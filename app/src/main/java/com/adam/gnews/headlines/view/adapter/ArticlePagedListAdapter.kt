package com.adam.gnews.headlines.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.adam.gnews.R
import com.adam.gnews.databinding.ItemHeadLineBinding
import com.adam.gnews.headlines.uimodels.ArticleUiModel
import com.adam.gnews.utils.extensions.setRemoteImage
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

class ArticlePagedListAdapter :
    PagedListAdapter<ArticleUiModel, ArticlePagedListAdapter.Holder>(ArticleUiModel.ARTICLE_DIFF_UTIL) {

    val clickInteraction: Subject<ArticleUiModel> by lazy {
        PublishSubject.create<ArticleUiModel>()
    }

    fun setUpAdapter(recyclerView: RecyclerView) {
        recyclerView.adapter = this
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                DividerItemDecoration.VERTICAL
            ).apply {
                ContextCompat.getDrawable(
                    recyclerView.context,
                    R.drawable.simple_divider_space
                )?.let(::setDrawable)
            }
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)

        return ItemHeadLineBinding.inflate(inflater, parent, false)
            .run(::Holder)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        getItem(position)?.let { holder.bind(dataSet = it) }
    }

    inner class Holder(
        private val binding: ItemHeadLineBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(dataSet: ArticleUiModel) {

            binding.ivHedLine.setRemoteImage(
                imageUrl = dataSet.urlToImage
            )
            binding.tvHeadLineAuthor.text = dataSet.author
            binding.tvHeadLineSource.text = dataSet.sourceName
            binding.tvHeadlineTitle.text = dataSet.title
            binding.tvHeadlineSubTitle.text = dataSet.description
            binding.tvHeadLineDate.text = dataSet.publishedAt

            binding.root.setOnClickListener { clickInteraction.onNext(dataSet) }
        }
    }
}