package com.example.userlist.ui.auth.register

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.userlist.R
import com.example.userlist.databinding.FragmentRegisterBinding
import com.example.userlist.ui.auth.AuthViewModelFactory
import com.example.userlist.ui.auth.login.LoginFragment
import com.example.userlist.utils.Constants.showSnackbar

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding

    private val viewModel by viewModels<RegisterViewModel> {
        AuthViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        observeViewModel()
        setListeners()

        return binding.root
    }

    private fun observeViewModel() {
        viewModel.apply {
            isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }

            isCreated.observe(viewLifecycleOwner) {
                if (it) {
                    changeToLogin()
                }
            }

            message.observe(viewLifecycleOwner) {
                showSnackbar(requireActivity(), binding.root, it)
            }
        }
    }

    private fun setListeners() {
        binding.apply {
            btnRegister.setOnClickListener {
                if (isValid()) {
                    viewModel.registerUser(
                        username = edUsername.text.toString(),
                        email = edEmail.text.toString(),
                        githubUsername = edGithub.text.toString(),
                        nik = edNik.text.toString(),
                        password = edPassword.text.toString(),
                    )
                }
            }

            btnLogin.setOnClickListener { changeToLogin() }
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                changeToLogin()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun changeToLogin() {
        parentFragmentManager.beginTransaction().apply {
            replace(
                R.id.auth_container,
                LoginFragment(),
                LoginFragment::class.java.simpleName
            )
            commit()
        }
    }

    private fun isValid() = if (binding.edUsername.text.isNullOrEmpty()
    ) {
        showSnackbar(
            requireActivity(),
            binding.root,
            "Fill the Username field correctly!"
        )
        false
    } else if (binding.edEmail.text.isNullOrEmpty()) {
        showSnackbar(
            requireActivity(),
            binding.root,
            "Fill the Email field correctly!"
        )
        false
    } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.edEmail.text.toString())
            .matches() || binding.edEmail.text.isNullOrEmpty()
    ) {
        showSnackbar(
            requireActivity(),
            binding.root,
            "Fill the Email Pattern correctly!"
        )
        false
    } else if (binding.edGithub.text.isNullOrEmpty()) {
        showSnackbar(
            requireActivity(),
            binding.root,
            "Fill the Github Username field correctly!"
        )
        false
    } else if (binding.edNik.text.isNullOrEmpty()) {
        showSnackbar(
            requireActivity(),
            binding.root,
            "Fill the NIK field correctly!"
        )
        false
    } else if (binding.edPassword.text.isNullOrEmpty()) {
        showSnackbar(
            requireActivity(),
            binding.root,
            "Fill the Password field correctly!"
        )
        false
    } else {
        true
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressbar.isVisible = isLoading
        binding.btnRegister.isVisible = !isLoading
    }
}