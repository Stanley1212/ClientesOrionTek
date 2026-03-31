package com.example.clientesoriontek.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.clientesoriontek.data.local.entity.Client
import com.example.clientesoriontek.databinding.ItemClientBinding

class ClientAdapter(private val onItemClicked: (Client) -> Unit) :
    ListAdapter<Client, ClientAdapter.ClientViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientViewHolder {
        return ClientViewHolder(
            ItemClientBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ClientViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }

    class ClientViewHolder(private var binding: ItemClientBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(client: Client) {
            binding.textViewName.text = "${client.firstName} ${client.lastName}"
            binding.textViewEmail.text = client.email
            binding.textViewPhone.text = client.phone
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Client>() {
            override fun areItemsTheSame(oldItem: Client, newItem: Client): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Client, newItem: Client): Boolean {
                return oldItem == newItem
            }
        }
    }
}
