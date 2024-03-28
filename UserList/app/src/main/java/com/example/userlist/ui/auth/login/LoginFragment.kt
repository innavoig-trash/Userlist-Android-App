package com.example.userlist.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.userlist.R
import com.example.userlist.databinding.FragmentLoginBinding
import com.example.userlist.ui.auth.AuthViewModelFactory
import com.example.userlist.ui.auth.register.RegisterFragment
import com.example.userlist.ui.main.BottomMainActivity
import com.example.userlist.utils.Constants.showSnackbar

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    private val viewModel by viewModels<LoginViewModel> {
        AuthViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        observeViewModel()
        setListeners()

        return binding.root
    }

    private fun observeViewModel() {
        viewModel.apply {
            isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }

            isLoggedIn.observe(viewLifecycleOwner) {
                if (it) {
                    requireActivity().finish()
                    startActivity(Intent(requireActivity(), BottomMainActivity::class.java))
                }
            }

            message.observe(viewLifecycleOwner) {
                showSnackbar(requireActivity(), binding.root, it)
            }
        }
    }

    private fun setListeners() {
        binding.apply {
            btnLogin.setOnClickListener {
                if (isValid()) {
                    viewModel.loginUser(
                        email = edEmail.text.toString(),
                        password = edPassword.text.toString()
                    )
                }
            }

            btnRegister.setOnClickListener { changeToRegister() }
        }
    }

    private fun isValid() = if (binding.edEmail.text.isNullOrEmpty()) {
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
    } else if (binding.edPassword.text.isNullOrEmpty()) {
        showSnackbar(requireActivity(), binding.root, "Fill the Password field correctly!")
        false
    } else {
        true
    }


    private fun changeToRegister() {
        parentFragmentManager.beginTransaction().apply {
            replace(
                R.id.auth_container,
                RegisterFragment(),
                RegisterFragment::class.java.simpleName
            )
            commit()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressbar.isVisible = isLoading
        binding.btnLogin.isVisible = !isLoading
    }
}