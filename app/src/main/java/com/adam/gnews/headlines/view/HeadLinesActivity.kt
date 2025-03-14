package com.adam.gnews.headlines.view

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.observe
import com.adam.gnews.R
import com.adam.gnews.databinding.ActivityHeadLinesBinding
import com.adam.gnews.headlines.di.HeadLinesDependencyHolder
import com.adam.gnews.headlines.view.adapter.ArticlePagedListAdapter
import com.adam.gnews.headlines.viewmodel.HeadLinesVMF
import com.adam.gnews.headlines.viewmodel.HeadLinesViewModel
import com.adam.gnews.utils.processingstates.State
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class HeadLinesActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: HeadLinesVMF

    private val viewModel: HeadLinesViewModel by viewModels { viewModelFactory }

    private val component by lazy { HeadLinesDependencyHolder.initHeadLinesComponent() }

    private lateinit var rvAdapterInteractionSubSubscription: Disposable

    private val rvAdapter: ArticlePagedListAdapter by lazy {
        ArticlePagedListAdapter()
    }

    private val binding: ActivityHeadLinesBinding by lazy {
        ActivityHeadLinesBinding.inflate(
            layoutInflater
        )
    }

    private val behaviour: BottomSheetBehavior<FrameLayout> by lazy {
        BottomSheetBehavior.from(binding.flWebViewContainer)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(activity = this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnRetry.setOnClickListener { viewModel.invalidateDataSource() }

        setUpRecyclerView()

        setUpWebView()

        setUpLiveDataListeners()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        saveWebViewCurrentPage(outState)
    }

    private fun saveWebViewCurrentPage(outState: Bundle) {
        if (behaviour.state == BottomSheetBehavior.STATE_EXPANDED) {
            outState.putString(KEY_CURRENT_PAGE, binding.wwHeadLine.url)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        restoreWebViewUrl(savedInstanceState)
    }

    private fun restoreWebViewUrl(savedInstanceState: Bundle) {
        if (behaviour.state == BottomSheetBehavior.STATE_EXPANDED) {
            savedInstanceState.getString(KEY_CURRENT_PAGE)
                ?.let(binding.wwHeadLine::loadUrl)
        }
    }

    private fun setUpWebView() {
        binding.wwHeadLine.webViewClient = WebViewClient()
        behaviour.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    binding.wwHeadLine.loadUrl("about:blank")
                }
            }
        })
    }

    private fun setUpRecyclerView() {
        rvAdapter.setUpAdapter(binding.rvHeadLines)
        rvAdapterInteractionSubSubscription = rvAdapter.clickInteraction.subscribe { uiModel ->
            binding.wwHeadLine.loadUrl(uiModel.url)
            behaviour.state = BottomSheetBehavior.STATE_EXPANDED
        }
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
                    with(rvAdapter.itemCount == 0) {
                        binding.errorGroup.isVisible = this
                        binding.rvHeadLines.isGone = this
                    }

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

    override fun onDestroy() {
        rvAdapterInteractionSubSubscription.dispose()
        super.onDestroy()
    }

    override fun onBackPressed() {
        if (behaviour.state == BottomSheetBehavior.STATE_EXPANDED) {
            behaviour.state = BottomSheetBehavior.STATE_HIDDEN
        } else super.onBackPressed()
    }

    companion object {
        private const val TAG = "HeadLinesActivity"

        private const val KEY_CURRENT_PAGE = "key-current-key"
    }
}