package com.adam.gnews.headlines.uimodels

import android.os.Parcel
import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.adam.gnews.data.appdb.entites.ArticleDBO
import com.adam.gnews.data.webservice.remotemodels.ArticleDTO
import com.adam.gnews.headlines.utils.ArticleDateFormatHelper

data class ArticleUiModel(
    val id: String,
    val sourceName: String,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String
) : Parcelable {

    constructor(
        remoteModel: ArticleDTO,
        dateFormatter: ArticleDateFormatHelper
    ) : this(
        id = remoteModel.source.id.orEmpty(),
        sourceName = remoteModel.source.name,
        urlToImage = remoteModel.urlToImage.orEmpty(),
        url = remoteModel.url,
        title = remoteModel.title,
        publishedAt = dateFormatter.formatDate(date = remoteModel.publishedAt),
        description = remoteModel.description.orEmpty(),
        content = remoteModel.content.orEmpty(),
        author = remoteModel.author.orEmpty()
    )

    constructor(
        dbModel: ArticleDBO,
        dateFormatter: ArticleDateFormatHelper
    ) : this(
        id = dbModel.source.id,
        sourceName = dbModel.source.name,
        urlToImage = dbModel.urlToImage,
        url = dbModel.url,
        title = dbModel.title,
        publishedAt = dateFormatter.formatDate(date = dbModel.publishedAt),
        description = dbModel.description,
        content = dbModel.content,
        author = dbModel.author
    )

    private constructor(parcel: Parcel) :
            this(
                id = parcel.readString() ?: throw RuntimeException(),
                sourceName = parcel.readString() ?: throw RuntimeException(),
                author = parcel.readString() ?: throw RuntimeException(),
                title = parcel.readString() ?: throw RuntimeException(),
                description = parcel.readString() ?: throw RuntimeException(),
                url = parcel.readString() ?: throw RuntimeException(),
                urlToImage = parcel.readString() ?: throw RuntimeException(),
                publishedAt = parcel.readString() ?: throw RuntimeException(),
                content = parcel.readString() ?: throw RuntimeException()
            )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(this.id)
        dest?.writeString(this.sourceName)
        dest?.writeString(this.author)
        dest?.writeString(this.title)
        dest?.writeString(this.description)
        dest?.writeString(this.url)
        dest?.writeString(this.url)
        dest?.writeSerializable(this.publishedAt)
        dest?.writeString(this.content)
    }

    override fun describeContents(): Int = 0

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<ArticleUiModel> {
            override fun createFromParcel(parcel: Parcel) = ArticleUiModel(parcel)
            override fun newArray(size: Int) = arrayOfNulls<ArticleUiModel>(size)
        }

        val ARTICLE_DIFF_UTIL = object : DiffUtil.ItemCallback<ArticleUiModel>() {
            override fun areItemsTheSame(
                oldItem: ArticleUiModel,
                newItem: ArticleUiModel
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: ArticleUiModel,
                newItem: ArticleUiModel
            ): Boolean =
                newItem.id == oldItem.id
        }
    }
}