package com.creativeitinstitute.storyviewrepo.data.remote

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Story(val url: String, val storyDate: Long) : Parcelable