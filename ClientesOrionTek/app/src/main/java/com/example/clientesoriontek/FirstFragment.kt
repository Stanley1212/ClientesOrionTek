package com.example.clientesoriontek

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.clientesoriontek.databinding.FragmentFirstBinding
import com.example.clientesoriontek.ui.adapter.ClientAdapter
import com.example.clientesoriontek.ui.viewmodel.ClientViewModel
import com.example.clientesoriontek.ui.viewmodel.ClientViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ClientViewModel by viewModels {
        ClientViewModelFactory((requireActivity().application as MainApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ClientAdapter { client ->
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(client.id)
            findNavController().navigate(action)
        }
        binding.recyclerViewClients.adapter = adapter

        viewModel.allClients.observe(viewLifecycleOwner) { clients ->
            adapter.submitList(clients)
            binding.textViewEmpty.visibility = if (clients.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        activity?.findViewById<FloatingActionButton>(R.id.fab)?.setOnClickListener {
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(-1L)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
