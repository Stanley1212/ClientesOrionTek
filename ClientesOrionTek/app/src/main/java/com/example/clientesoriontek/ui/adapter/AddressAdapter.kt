package com.example.clientesoriontek.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.clientesoriontek.data.local.entity.Address
import com.example.clientesoriontek.databinding.ItemAddressBinding

class AddressAdapter(
    private val onEditClicked: (Address) -> Unit,
    private val onDeleteClicked: (Address) -> Unit
) : ListAdapter<Address, AddressAdapter.AddressViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        return AddressViewHolder(
            ItemAddressBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        val current = getItem(position)
        holder.binding.buttonEditAddress.setOnClickListener {
            onEditClicked(current)
        }
        holder.binding.buttonDeleteAddress.setOnClickListener {
            onDeleteClicked(current)
        }
        holder.bind(current)
    }

    class AddressViewHolder(val binding: ItemAddressBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(address: Address) {
            binding.textViewStreet.text = address.street
            binding.textViewCity.text = "${address.city}, ${address.sector}"
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Address>() {
            override fun areItemsTheSame(oldItem: Address, newItem: Address): Boolean {
                return oldItem.addressId == newItem.addressId
            }

            override fun areContentsTheSame(oldItem: Address, newItem: Address): Boolean {
                return oldItem == newItem
            }
        }
    }
}
