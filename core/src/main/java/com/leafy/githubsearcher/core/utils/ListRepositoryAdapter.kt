package com.leafy.githubsearcher.core.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.leafy.githubsearcher.core.databinding.ItemListRepositoryBinding
import com.leafy.githubsearcher.core.domain.model.Repository

class ListRepositoryAdapter : RecyclerView.Adapter<ListRepositoryAdapter.UserViewHolder>() {
    var data = ArrayList<Repository>()

    fun setData(list: List<Repository>?) {
        if (list == null) return
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListRepositoryBinding.bind(itemView)
        fun bind(repository: Repository) {
            with(binding) {
                name.text = repository.name
                link.text = repository.url.replace("https://", "")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemListRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}