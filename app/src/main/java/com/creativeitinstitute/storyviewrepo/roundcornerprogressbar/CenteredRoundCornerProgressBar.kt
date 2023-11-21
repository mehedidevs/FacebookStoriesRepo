package com.creativeitinstitute.storyviewrepo.roundcornerprogressbar

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.annotation.Keep
import androidx.annotation.RequiresApi

@Keep
open class CenteredRoundCornerProgressBar : RoundCornerProgressBar {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    )

    override fun drawProgress(
        layoutProgress: LinearLayout,
        progressDrawable: GradientDrawable?,
        progressDrawablel: Drawable?,
        max: Float,
        progress: Float,
        totalWidth: Float,
        radius: Int,
        padding: Int,
        isReverse: Boolean,
    ) {
        super.drawProgress(
            layoutProgress,
            null,
            progressDrawablel,
            max,
            progress,
            totalWidth,
            radius,
            padding,
            isReverse
        )
        val params = layoutProgress.layoutParams as MarginLayoutParams
        val ratio = max / progress
        val progressWidth = (totalWidth - (padding * 2)) / ratio
        val deltaWidth = totalWidth - progressWidth
        params.setMargins(
            (deltaWidth / 2).toInt(),
            params.topMargin,
            (deltaWidth / 2).toInt(),
            params.bottomMargin,
        )
        layoutProgress.layoutParams = params
    }
}
