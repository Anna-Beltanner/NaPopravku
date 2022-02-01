package com.example.napopravku.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.napopravku.data.model.RepositoriesModel
import com.example.napopravku.databinding.ItemViewBinding




class RepositoriesAdapters(
    val onItemClickListener: (RepositoriesModel) -> Unit,
    val onLastPosition: (Int) -> Unit
) :
    PagingDataAdapter<RepositoriesModel, RepositoriesAdapters.MyViewHolder>(DIFF_CALLBACK) {

    var items = listOf<RepositoriesModel>()
    lateinit var binding: ItemViewBinding

    //список данных, полученных из запроса

    fun setUpdatedData(items: List<RepositoriesModel>) {
        this.items = items
        notifyDataSetChanged()
    }

    class MyViewHolder(var itemViewBinding: ItemViewBinding, val onItemClickListener: (RepositoriesModel) -> Unit) :
        RecyclerView.ViewHolder(itemViewBinding.root) {

        fun bind(data: RepositoriesModel) {
            itemView.setOnClickListener {
                onItemClickListener.invoke(data)
            }

            itemViewBinding.fullName.text = data.full_name
            itemViewBinding.ownerLogin.text = data.owner.login

            val url = data.owner.avatar_url
            Glide.with(itemView.context)
                .load(url)
                .into(itemViewBinding.ownerAvatar)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        binding = ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding, onItemClickListener)
    }

    override fun onBindViewHolder(holder: RepositoriesAdapters.MyViewHolder, position: Int) {
        val repository = items.get(position)
        holder.bind(repository)
        if (position == items.size - 1) {
            onLastPosition (repository.id)
        }
        //если позиция = последнему элементу item, то сделать следующий запрос
    }

    override fun getItemCount(): Int {
        return items.size
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RepositoriesModel>() {
            override fun areItemsTheSame(
                oldItem: RepositoriesModel,
                newItem: RepositoriesModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: RepositoriesModel,
                newItem: RepositoriesModel
            ): Boolean {
                return oldItem == newItem
            }

        }
    }


}