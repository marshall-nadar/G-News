package com.adam.gnews.utils.extensions

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.adam.gnews.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator

fun ImageView.setImage(
    fitToView: Boolean = true,
    centerType: CenterType = CenterType.CROP,
    @DrawableRes placeholderDrawable: Int = R.drawable.anim_progress,
    @DrawableRes errorDrawable: Int = R.drawable.ic_baseline_broken_image_24,
    onSuccessBlock: () -> Unit = {},
    onErrorBlock: () -> Unit = {},
    isContentEmpty: Boolean,
    loadResource: (picasso: Picasso) -> RequestCreator
): ImageView {

    val picasso = Picasso.with(context)
    val builder = if (isContentEmpty) {
        picasso.load(placeholderDrawable)

    } else {
        loadResource(picasso)
            .error(ContextCompat.getDrawable(context, errorDrawable))
            .placeholder(ContextCompat.getDrawable(context, placeholderDrawable))
    }

    if (fitToView) builder.fit()

    when (centerType) {
        CenterType.CROP -> builder.centerCrop()
        CenterType.INSIDE -> builder.centerInside()
    }

    builder.into(this, object : Callback {
        override fun onSuccess() {
            onSuccessBlock()
        }

        override fun onError() {
            onErrorBlock()
        }
    })
    return this@setImage
}

fun ImageView.setRemoteImage(
    imageUrl: String? = "",
    fitToView: Boolean = true,
    centerType: CenterType = CenterType.CROP,
    @DrawableRes placeholderDrawable: Int = R.drawable.anim_progress,
    @DrawableRes errorDrawable: Int = R.drawable.ic_baseline_broken_image_24,
    onSuccessBlock: () -> Unit = {},
    onErrorBlock: () -> Unit = {}
): ImageView = setImage(
    fitToView = fitToView,
    centerType = centerType,
    placeholderDrawable = placeholderDrawable,
    errorDrawable = errorDrawable,
    onSuccessBlock = onSuccessBlock,
    onErrorBlock = onErrorBlock,
    isContentEmpty = imageUrl.isNullOrBlank()
) { it.load(imageUrl) }


enum class CenterType {

    /**
     * Crop the image by zoom in the ImageView
     * The sides (vertical / horizontal) will be cropped with respect to the ImageView dimensions.
     */
    CROP,

    /**
     * Center the image in the ImageView.
     * A possibility of empty space when the aspect ratio of Image with ImageView don't match.
     */
    INSIDE
}