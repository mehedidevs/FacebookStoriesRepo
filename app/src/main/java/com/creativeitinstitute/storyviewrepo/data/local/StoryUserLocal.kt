package com.creativeitinstitute.storyviewrepo.data.local

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize


@Parcelize
data class StoryUserLocal(val username: String, @DrawableRes val profilePicRes: Int, val stories: ArrayList<StoryLocal>) : Parcelable