package com.azarenko.gitrepostest.ui.fragments.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.azarenko.gitrepostest.databinding.HistoryFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private val reposListViewModel: HistoryViewModel by viewModels()

    private var _binding: HistoryFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: HistoryListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HistoryFragmentBinding.inflate(layoutInflater, container, false)

        initAdapter()
        initObserver()
        reposListViewModel.getReposListsFromDb()

        return binding.root
    }

    private fun initAdapter() = with(binding) {
        adapter = HistoryListAdapter()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun initObserver() {
        reposListViewModel.reposListDataFromDB.observe(requireActivity()) {
            adapter.submitList(it)
        }
    }

}