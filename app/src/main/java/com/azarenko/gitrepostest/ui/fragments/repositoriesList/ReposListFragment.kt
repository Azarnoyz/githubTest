package com.azarenko.gitrepostest.ui.fragments.repositoriesList

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.azarenko.gitrepostest.R
import com.azarenko.gitrepostest.data.RepositoriesData
import com.azarenko.gitrepostest.databinding.ReposListFramentFragmentBinding
import com.azarenko.gitrepostest.utils.DebouncingQueryTextListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReposListFragment : Fragment() {

    private val reposListViewModel: ReposListViewModel by viewModels()
    private lateinit var adapter: ReposListAdapter

    private var _binding: ReposListFramentFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ReposListFramentFragmentBinding.inflate(layoutInflater, container, false)

        initAdapter()
        initSearch()
        getRepositories(getString(R.string.android))

        return binding.root
    }

    private fun initSearch() {
        binding.searchView.setOnQueryTextListener(
            DebouncingQueryTextListener(
                this.lifecycle
            ) { newText ->
                newText?.let {
                    if (it.isEmpty()) {
                        getRepositories(getString(R.string.android))
                    } else {
                        getRepositories(it)
                    }
                }
            }
        )
    }

    private fun getRepositories(searchValue: String) {
        lifecycleScope.launch {
            reposListViewModel.getRepositories(searchValue).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun initAdapter() = with(binding) {
        adapter = ReposListAdapter { item ->
            openUrlInBrowser(item.url)
            addRepositoryToDb(item)
        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun addRepositoryToDb(repository: RepositoriesData) {
        lifecycleScope.launch {
            reposListViewModel.addRepository(repository)
        }
    }

    private fun openUrlInBrowser(it: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
        requireActivity().startActivity(intent)
    }
}