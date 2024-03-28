package com.example.userlist.ui.main.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.userlist.data.models.DataItem
import com.example.userlist.databinding.FragmentHomeBinding
import com.example.userlist.ui.adapters.UsersAdapter
import com.example.userlist.ui.detail.DetailActivity
import com.example.userlist.ui.detail.DetailActivity.Companion.EXTRA_ID
import com.example.userlist.ui.main.MainViewModelFactory
import com.example.userlist.utils.Constants.showSnackbar
import com.google.android.material.search.SearchView

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel> {
        MainViewModelFactory()
    }

    private val usersAdapter = UsersAdapter()
    private val searchUsersAdapter = UsersAdapter()

    private lateinit var onBackPressedCallback: OnBackPressedCallback

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        observeViewModel()
        setSearchView()
        setRecyclerView()

        return binding.root
    }

    private fun observeViewModel() {
        viewModel.apply {
            isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }

            allUsers.observe(viewLifecycleOwner) {
                usersAdapter.setList(it)
            }

            message.observe(viewLifecycleOwner) {
                showSnackbar(requireActivity(), binding.root, it)
            }
        }
    }

    private fun setSearchView() {
        binding.searchView.apply {
            setText(binding.searchBar.text)

            setupWithSearchBar(binding.searchBar)
            addTransitionListener { _, _, newState ->
                binding.searchView.isVisible =
                    newState == SearchView.TransitionState.SHOWN || newState == SearchView.TransitionState.SHOWING
                onBackPressedCallback = requireActivity().onBackPressedDispatcher.addCallback(
                    requireActivity(),
                    false
                ) {
                    binding.searchView.hide()
                }
                onBackPressedCallback.isEnabled =
                    newState == SearchView.TransitionState.SHOWN || newState == SearchView.TransitionState.SHOWING
            }

            editText.setOnEditorActionListener { _, _, _ ->
                binding.searchBar.setText(text)

                if (text.isEmpty()) {
                    binding.rvUser.apply {
                        adapter = null
                        adapter = usersAdapter
                    }
                } else {
                    filterUser(text.toString())
                    binding.rvUser.apply {
                        adapter = null
                        adapter = searchUsersAdapter
                    }
                }
                hide()
                false
            }

        }
    }

    private fun setRecyclerView() {
        usersAdapter.onUserClick = { id ->
            val iDetail = Intent(requireActivity(), DetailActivity::class.java)
            iDetail.putExtra(EXTRA_ID, id)
            startActivity(iDetail)
        }

        searchUsersAdapter.onUserClick = { id ->
            val iDetail = Intent(requireActivity(), DetailActivity::class.java)
            iDetail.putExtra(EXTRA_ID, id)
            startActivity(iDetail)
        }

        binding.rvUser.apply {
            adapter = usersAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun filterUser(query: String) {
        val listUser = viewModel.allUsers.value
        val searchedListUser = ArrayList<DataItem>()

        for (user in listUser!!) {
            if (user.firstName!!.lowercase()
                    .contains(query.lowercase()) || user.lastName!!.lowercase()
                    .contains(query.lowercase())
            ) {
                searchedListUser.add(user)
            }
        }

        searchUsersAdapter.setList(searchedListUser)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }
}