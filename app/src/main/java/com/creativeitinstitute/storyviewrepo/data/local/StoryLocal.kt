package com.creativeitinstitute.storyviewrepo.data.local

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize


@Parcelize
data class StoryLocal(@DrawableRes val imgRes: Int, val storyDate: Long) : Parcelable