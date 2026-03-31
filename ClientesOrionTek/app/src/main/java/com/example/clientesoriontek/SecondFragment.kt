package com.example.clientesoriontek

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clientesoriontek.data.local.entity.Address
import com.example.clientesoriontek.data.local.entity.Client
import com.example.clientesoriontek.databinding.DialogAddAddressBinding
import com.example.clientesoriontek.databinding.FragmentSecondBinding
import com.example.clientesoriontek.ui.adapter.AddressAdapter
import com.example.clientesoriontek.ui.viewmodel.ClientViewModel
import com.example.clientesoriontek.ui.viewmodel.ClientViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    private val args: SecondFragmentArgs by navArgs()
    private val viewModel: ClientViewModel by viewModels {
        ClientViewModelFactory((requireActivity().application as MainApplication).repository)
    }

    private lateinit var addressAdapter: AddressAdapter
    private val tempAddressList = mutableListOf<Address>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        if (args.clientId != -1L) {
            loadClientData(args.clientId)
        }

        binding.buttonAddAddress.setOnClickListener {
            showAddAddressDialog()
        }

        binding.buttonSave.setOnClickListener {
            saveClient()
        }
    }

    private fun setupRecyclerView() {
        addressAdapter = AddressAdapter(
            onEditClicked = { address -> showEditAddressDialog(address) },
            onDeleteClicked = { address -> showDeleteConfirmationDialog(address) }
        )
        binding.recyclerViewAddresses.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = addressAdapter
        }
    }

    private fun loadClientData(clientId: Long) {
        viewModel.getClientById(clientId).observe(viewLifecycleOwner) { client ->
            client?.let {
                binding.editTextName.setText(it.firstName)
                binding.editTextLastName.setText(it.lastName)
                binding.editTextPhone.setText(it.phone)
                binding.editTextEmail.setText(it.email)
            }
        }

        viewModel.getAddressesForClient(clientId).observe(viewLifecycleOwner) { addresses ->
            if (args.clientId != -1L) {
                tempAddressList.clear()
                tempAddressList.addAll(addresses)
                addressAdapter.submitList(tempAddressList.toList())
            }
        }
    }

    private fun showAddAddressDialog() {
        val dialogBinding = DialogAddAddressBinding.inflate(layoutInflater)
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.add_address))
            .setView(dialogBinding.root)
            .setPositiveButton(getString(R.string.add)) { _, _ ->
                val street = dialogBinding.editTextStreet.text.toString()
                val city = dialogBinding.editTextCity.text.toString()
                val sector = dialogBinding.editTextSector.text.toString()
                val houseNumber = dialogBinding.editTextHouseNumber.text.toString()

                if (street.isNotBlank() && city.isNotBlank()) {
                    val newAddress = Address(
                        clientId = if (args.clientId != -1L) args.clientId else 0,
                        street = street,
                        city = city,
                        sector = sector,
                        houseNumber = houseNumber
                    )
                    
                    if (args.clientId != -1L) {
                        viewModel.insertAddress(newAddress)
                    } else {
                        tempAddressList.add(newAddress)
                        addressAdapter.submitList(tempAddressList.toList())
                    }
                } else {
                    Toast.makeText(requireContext(), getString(R.string.address_required), Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun showEditAddressDialog(address: Address) {
        val dialogBinding = DialogAddAddressBinding.inflate(layoutInflater)
        dialogBinding.editTextStreet.setText(address.street)
        dialogBinding.editTextCity.setText(address.city)
        dialogBinding.editTextSector.setText(address.sector)
        dialogBinding.editTextHouseNumber.setText(address.houseNumber)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.edit_address))
            .setView(dialogBinding.root)
            .setPositiveButton(getString(R.string.save)) { _, _ ->
                val street = dialogBinding.editTextStreet.text.toString()
                val city = dialogBinding.editTextCity.text.toString()
                val sector = dialogBinding.editTextSector.text.toString()
                val houseNumber = dialogBinding.editTextHouseNumber.text.toString()

                if (street.isNotBlank() && city.isNotBlank()) {
                    val updatedAddress = address.copy(
                        street = street,
                        city = city,
                        sector = sector,
                        houseNumber = houseNumber
                    )
                    
                    if (args.clientId != -1L) {
                        viewModel.updateAddress(updatedAddress)
                    } else {
                        val index = tempAddressList.indexOf(address)
                        if (index != -1) {
                            tempAddressList[index] = updatedAddress
                            addressAdapter.submitList(tempAddressList.toList())
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), getString(R.string.address_required), Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun showDeleteConfirmationDialog(address: Address) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.delete_address))
            .setMessage(getString(R.string.confirm_delete_address))
            .setPositiveButton(getString(R.string.delete)) { _, _ ->
                if (args.clientId != -1L) {
                    viewModel.deleteAddress(address)
                } else {
                    tempAddressList.remove(address)
                    addressAdapter.submitList(tempAddressList.toList())
                }
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun saveClient() {
        val firstName = binding.editTextName.text.toString()
        val lastName = binding.editTextLastName.text.toString()
        val phone = binding.editTextPhone.text.toString()
        val email = binding.editTextEmail.text.toString()

        if (firstName.isBlank() || lastName.isBlank()) {
            Toast.makeText(requireContext(), getString(R.string.client_required), Toast.LENGTH_SHORT).show()
            return
        }

        val client = Client(
            id = if (args.clientId != -1L) args.clientId else 0,
            firstName = firstName,
            lastName = lastName,
            phone = phone,
            email = email
        )

        if (args.clientId == -1L) {
            viewModel.insertClient(client) { newClientId ->
                tempAddressList.forEach { address ->
                    viewModel.insertAddress(address.copy(clientId = newClientId))
                }
                requireActivity().runOnUiThread {
                    findNavController().navigateUp()
                }
            }
        } else {
            viewModel.updateClient(client)
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
