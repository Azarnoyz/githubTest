package com.azarenko.gitrepostest.ui.fragments.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.azarenko.gitrepostest.data.RepositoriesData
import com.azarenko.gitrepostest.databinding.ItemRepositoriesListBinding

class HistoryListAdapter : RecyclerView.Adapter<HistoryListAdapter.ViewHolder>() {


    private val diffCallback = object : DiffUtil.ItemCallback<RepositoriesData>() {
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

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<RepositoriesData>) = differ.submitList(list)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)
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

    override fun getItemCount(): Int = differ.currentList.size

    inner class ViewHolder(private val binding: ItemRepositoriesListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(repositoriesData: RepositoriesData) = with(binding) {

            tvName.text = repositoriesData.name
            tvLanguage.text = repositoriesData.language
            tvStarsCount.text = repositoriesData.stars.toString()
        }
    }


}