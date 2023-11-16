package com.creativeitinstitute.storyviewrepo.utils

import com.creativeitinstitute.storyviewrepo.R
import com.creativeitinstitute.storyviewrepo.data.local.StoryLocal
import com.creativeitinstitute.storyviewrepo.data.local.StoryUserLocal
import com.creativeitinstitute.storyviewrepo.data.remote.Story
import com.creativeitinstitute.storyviewrepo.data.remote.StoryUser
import kotlin.random.Random
import kotlin.random.nextInt

object StoryGeneratorLocal {

    fun generateStoriesRes(): ArrayList<StoryUserLocal> {
        val storyRes = ArrayList<Int>()

        storyRes.add(R.drawable.info_img1)
        storyRes.add(R.drawable.info_img2)
        storyRes.add(R.drawable.info_img3)
        storyRes.add(R.drawable.info_img4)
        storyRes.add(R.drawable.info_img5)
        storyRes.add(R.drawable.info_img6)


        val userProfileRes = ArrayList<Int>()
        userProfileRes.add(R.mipmap.ic_launcher_round)
        userProfileRes.add(R.mipmap.ic_launcher_round)
        userProfileRes.add(R.mipmap.ic_launcher_round)
        userProfileRes.add(R.mipmap.ic_launcher_round)
        userProfileRes.add(R.mipmap.ic_launcher_round)


        val storyUserListLocal = ArrayList<StoryUserLocal>()


        val stories1 = ArrayList<StoryLocal>()
        val stories2 = ArrayList<StoryLocal>()
        val stories3 = ArrayList<StoryLocal>()

        stories1.add(
            StoryLocal(
                storyRes[0],
                System.currentTimeMillis() - (1 * (24 - Random.nextInt(500)) * 60 * 60 * 1000)
            )
        )
        stories1.add(
            StoryLocal(
                storyRes[1],
                System.currentTimeMillis() - (1 * (24 - Random.nextInt(500)) * 60 * 60 * 1000)
            )
        )
        stories1.add(
            StoryLocal(
                storyRes[3],
                System.currentTimeMillis() - (1 * (24 - Random.nextInt(500)) * 60 * 60 * 1000)
            )
        )
        stories2.add(
            StoryLocal(
                storyRes[4],
                System.currentTimeMillis() - (1 * (24 - Random.nextInt(500)) * 60 * 60 * 1000)
            )
        )
        stories2.add(
            StoryLocal(
                storyRes[5],
                System.currentTimeMillis() - (1 * (24 - Random.nextInt(500)) * 60 * 60 * 1000)
            )
        )

        stories3.add(
            StoryLocal(
                storyRes[2],
                System.currentTimeMillis() - (1 * (24 - Random.nextInt(500)) * 60 * 60 * 1000)
            )
        )



        storyUserListLocal.add(
            StoryUserLocal(
                "Beda Admin 1",
                userProfileRes[0],
                stories1
            )
        )
        storyUserListLocal.add(
            StoryUserLocal(
                "Beda Admin 2",
                userProfileRes[1],
                stories2
            )
        )

        storyUserListLocal.add(
            StoryUserLocal(
                "Beda Admin 3",
                userProfileRes[2],
                stories3
            )
        )







        return storyUserListLocal
    }
}