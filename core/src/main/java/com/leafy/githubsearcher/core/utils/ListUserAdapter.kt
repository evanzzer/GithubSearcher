package com.leafy.githubsearcher.core.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.leafy.githubsearcher.core.R
import com.leafy.githubsearcher.core.databinding.ItemListUserBinding
import com.leafy.githubsearcher.core.domain.model.User

class ListUserAdapter : RecyclerView.Adapter<ListUserAdapter.UserViewHolder>() {
    var onItemClick: ((User) -> Unit)? = null

    var data = ArrayList<User>()

    fun setData(list: List<User>?) {
        if (list == null) return
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListUserBinding.bind(itemView)
        fun bind(user: User) {
            with(binding) {
                username.text = user.username
                Glide.with(itemView.context)
                    .load(user.avatarUrl)
                    .apply(RequestOptions.overrideOf(128, 128))
                    .placeholder(R.drawable.ic_baseline_image_24)
                    .error(R.drawable.ic_baseline_person_24)
                    .into(avatar)
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(data[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}