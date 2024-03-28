package com.example.userlist.ui.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.userlist.data.models.DataItem
import com.example.userlist.databinding.ActivityDetailBinding
import com.example.userlist.utils.Constants.showSnackbar

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    private val id by lazy { intent.getIntExtra(EXTRA_ID, -1) }
    private val viewModel by viewModels<DetailViewModel> {
        DetailViewModelFactory(
            id
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener { finish() }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.apply {
            isLoading.observe(this@DetailActivity) {
                showLoading(it)
            }

            userDetail.observe(this@DetailActivity) {
                setContent(it)
            }

            message.observe(this@DetailActivity) {
                showSnackbar(this@DetailActivity, binding.root, it)
            }
        }
    }

    private fun setContent(accountDetail: DataItem?) {
        if (accountDetail != null) {
            binding.apply {
                Glide.with(this@DetailActivity)
                    .load(accountDetail.avatar)
                    .circleCrop()
                    .into(binding.ivUser)

                tvFullname.text =
                    StringBuilder("${accountDetail.firstName} ${accountDetail.lastName}")
                tvEmail.text = accountDetail.email
            }
        }
    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressbar.isVisible = isLoading
    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}