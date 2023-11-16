@file:Suppress("DEPRECATION")

package com.creativeitinstitute.storyviewrepo.customview

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.creativeitinstitute.storyviewrepo.data.local.StoryUserLocal
import com.creativeitinstitute.storyviewrepo.data.remote.StoryUser
import com.creativeitinstitute.storyviewrepo.screen.StoryDisplayFragment

class StoryPagerAdapter constructor(fragmentManager: FragmentManager, private val storyList: ArrayList<StoryUser>)
    : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment = StoryDisplayFragment.newInstance(position, storyList[position])

    override fun getCount(): Int {
        return storyList.size
    }

    fun findFragmentByPosition(viewPager: ViewPager, position: Int): Fragment? {
        try {
            val f = instantiateItem(viewPager, position)
            return f as? Fragment
        } finally {
            finishUpdate(viewPager)
        }
    }
}