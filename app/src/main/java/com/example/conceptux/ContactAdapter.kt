package com.example.conceptux

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter(var listeners: ClickItemContactListener) : RecyclerView.Adapter<ContactAdapter.ContactAdapterViewHolder>() {

    /* lista de Contact */
    private val list: MutableList<Contact> = mutableListOf()

    fun updatelist(list: List<Contact>){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    /*inner class*/
    class ContactAdapterViewHolder(itemview: View, var list: List<Contact>, var listeners: ClickItemContactListener) : RecyclerView.ViewHolder(itemview){
        private val tvName: TextView = itemView.findViewById(R.id.tv_name)
        private val tvPhones: TextView = itemView.findViewById(R.id.tv_phone)
        private val tvPhotograph: ImageView = itemView.findViewById(R.id.iv_photograph)

        init {
            itemView.setOnClickListener{
                listeners.clickItemContact(list[adapterPosition])
            }
        }

        fun bind(contact: Contact){
            tvName.text = contact.name
            tvPhones.text = contact.phone
        }
    }
    /* implementados automaticamente */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return ContactAdapterViewHolder(view, list, listeners)
    }

    override fun onBindViewHolder(holder: ContactAdapterViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}