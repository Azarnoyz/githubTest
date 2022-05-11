package com.azarenko.gitrepostest.ui.fragments.repositoriesList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.azarenko.gitrepostest.data.RepositoriesData
import com.azarenko.gitrepostest.databinding.ItemRepositoriesListBinding

class ReposListAdapter(
    private val listener: (RepositoriesData) -> Unit
) : PagingDataAdapter<RepositoriesData, ReposListAdapter.ViewHolder>(ReposDiffCallback()) {


    private class ReposDiffCallback : DiffUtil.ItemCallback<RepositoriesData>() {
        override fun areItemsTheSame(
            oldItem: RepositoriesData,
            newItem: RepositoriesData
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: RepositoriesData,
            newItem: RepositoriesData
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        item.let {
            if (it != null) {
                holder.bind(it)
            }
        }
        holder.itemView.setOnClickListener {
            if (item != null) {
                listener(item)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder =
        ViewHolder(
            ItemRepositoriesListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    inner class ViewHolder(private val binding: ItemRepositoriesListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(repositoriesData: RepositoriesData) = with(binding) {

            tvName.text = repositoriesData.name
            tvLanguage.text = repositoriesData.language
            tvStarsCount.text = repositoriesData.stars.toString()
        }
    }
}