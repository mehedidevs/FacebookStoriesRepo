package com.creativeitinstitute.storyviewrepo.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Story(val url: String, val storyDate: Long) : Parcelable {

    //fun isVideo() =  url.contains(".mp4")
    fun isVideo() =  url.contains(".mp4")
}