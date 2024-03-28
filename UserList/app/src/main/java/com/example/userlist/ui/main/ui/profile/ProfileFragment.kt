package com.example.userlist.ui.main.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.userlist.databinding.FragmentProfileBinding
import com.example.userlist.ui.auth.AuthActivity
import com.example.userlist.ui.main.MainViewModelFactory

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<ProfileViewModel> {
        MainViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        observeViewModel()
        setListeners()

        return binding.root
    }

    private fun observeViewModel() {
        viewModel.apply {
            getCurrentUsername().observe(viewLifecycleOwner) {
                binding.apply {
                    ivUser.text = it[0].toString()
                    tvUsername.text = it
                }
            }

            getCurrentNik().observe(viewLifecycleOwner) {
                binding.tvNik.text = it
            }

            getCurrentEmail().observe(viewLifecycleOwner) {
                binding.tvEmail.text = it
            }

            getCurrentGithubUsername().observe(viewLifecycleOwner) {
                binding.tvGithubUsername.text = it
            }
        }
    }

    private fun setListeners() {
        binding.btnLogout.setOnClickListener {
            viewModel.logoutUser()
            requireActivity().finishAffinity()
            startActivity(Intent(requireActivity(), AuthActivity::class.java))
        }
    }
}