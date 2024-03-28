package com.example.userlist.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.userlist.data.models.DataItem
import com.example.userlist.databinding.ItemUserRowBinding

class UsersAdapter : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {
    private val listUsers: ArrayList<DataItem> = arrayListOf()
    var onUserClick: ((Int) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setList(listUser: ArrayList<DataItem>) {
        this.listUsers.clear()
        this.listUsers.addAll(listUser)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemUserRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listUsers[position])
    }

    override fun getItemCount() = listUsers.size

    inner class ViewHolder(private var binding: ItemUserRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: DataItem) {
            with(binding) {
                Glide.with(binding.root)
                    .load(user.avatar)
                    .into(binding.ivUser)

                tvUsername.text = StringBuilder("${user.firstName} ${user.lastName}")
                tvEmail.text = user.email

                cardUser.setOnClickListener {
                    onUserClick?.invoke(user.id!!)
                }
            }
        }
    }
}