package com.adam.gnews.headlines.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.adam.gnews.R
import com.adam.gnews.databinding.ActivityHeadLinesBinding
import com.adam.gnews.headlines.di.HeadLinesDependencyHolder
import com.adam.gnews.headlines.view.adapter.ArticlePagedListAdapter
import com.adam.gnews.headlines.viewmodel.HeadLinesVMF
import com.adam.gnews.headlines.viewmodel.HeadLinesViewModel
import com.adam.gnews.utils.processingstates.State
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class HeadLinesActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: HeadLinesVMF

    private val viewModel: HeadLinesViewModel by viewModels { viewModelFactory }

    private val component by lazy { HeadLinesDependencyHolder.initHeadLinesComponent() }

    private val rvAdapter: ArticlePagedListAdapter by lazy {
        ArticlePagedListAdapter()
    }

    private val binding: ActivityHeadLinesBinding by lazy {
        ActivityHeadLinesBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(activity = this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setUpRecyclerView()

        setUpLiveDataListeners()
    }

    private fun setUpRecyclerView() {
        rvAdapter.setUpAdapter(binding.rvHeadLines)
    }

    private fun setUpLiveDataListeners() {
        viewModel.livePagedList.observe(this) {
            rvAdapter.submitList(it)
            binding.srfHeadLines.isRefreshing = false
            binding.srfHeadLines.isEnabled = false
        }

        viewModel.loadingStatusSubject.observe(this) { state ->
            when (state) {
                is State.Loading -> {
                    binding.srfHeadLines.isRefreshing = state.loading
                }
                is State.Error -> {
                    binding.srfHeadLines.isRefreshing = false
                    binding.srfHeadLines.isEnabled = false
                    Snackbar.make(
                        binding.root,
                        R.string.err_occurred,
                        Snackbar.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }

    companion object {
        private const val TAG = "HeadLinesActivity"
    }
}