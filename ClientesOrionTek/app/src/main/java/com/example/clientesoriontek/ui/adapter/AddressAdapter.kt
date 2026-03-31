package com.example.clientesoriontek.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.clientesoriontek.data.local.entity.Address
import com.example.clientesoriontek.databinding.ItemAddressBinding

class AddressAdapter : ListAdapter<Address, AddressAdapter.AddressViewHolder>(DiffCallback) {

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
        holder.bind(current)
    }

    class AddressViewHolder(private var binding: ItemAddressBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(address: Address) {
            binding.textViewStreet.text = address.street
            binding.textViewCity.text = "${address.city}, ${address.sector}"
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Address>() {
            override fun areItemsTheSame(oldItem: Address, newItem: Address): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Address, newItem: Address): Boolean {
                return oldItem.street == newItem.street && oldItem.city == newItem.city
            }
        }
    }
}
