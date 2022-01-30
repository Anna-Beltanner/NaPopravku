package com.example.napopravku.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.napopravku.R
import com.example.napopravku.data.model.RepositoriesModel
import com.example.napopravku.databinding.ActivityMainBinding
import com.example.napopravku.databinding.ItemViewBinding
import java.text.FieldPosition

//данный класс отвечает за логику работы RV
//корутина уходит из UI потока и делает запрос в сеть, возвращает результат в UI поток


class RepositoriesAdapters (val onLastPosition: (Int) -> Unit) :
    PagingDataAdapter<RepositoriesModel, RepositoriesAdapters.MyViewHolder>(DIFF_CALLBACK) {

    var items = listOf<RepositoriesModel>()
    lateinit var binding: ItemViewBinding

    //список данных, полученных из запроса

    fun setUpdatedData(items: List<RepositoriesModel>) {
        this.items = items
        notifyDataSetChanged()
    }

    class MyViewHolder(var itemViewBinding: ItemViewBinding) :
        RecyclerView.ViewHolder(itemViewBinding.root) {

        //val tvTitle = view.findViewById<ImageView>(R.id.iv)


        fun bind(data: RepositoriesModel) {

            itemViewBinding.fullName.setText(data.full_name)
           itemViewBinding.ownerLogin.setText(data.owner.login)


            val url = data.owner.avatar_url

            Glide.with(itemView.context)
                .load(url)
                .into(itemViewBinding.ownerAvatar)


        }


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RepositoriesAdapters.MyViewHolder {
        binding = ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepositoriesAdapters.MyViewHolder, position: Int) {
        val repository = items.get(position)
        holder.bind(repository)
        if (position == items.size - 1) {
            onLastPosition (repository.id)
        }
        //если позиция = последнему элементу айтемов, то сделать запрос след.
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