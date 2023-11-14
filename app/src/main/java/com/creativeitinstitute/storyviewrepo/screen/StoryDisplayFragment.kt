package com.creativeitinstitute.storyviewrepo.screen


import android.content.Context
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.creativeitinstitute.storyviewrepo.customview.StoriesProgressView
import com.creativeitinstitute.storyviewrepo.data.Story
import com.creativeitinstitute.storyviewrepo.data.StoryUser
import com.creativeitinstitute.storyviewrepo.databinding.FragmentStoryDisplayBinding
import com.creativeitinstitute.storyviewrepo.utils.OnSwipeTouchListener
import com.creativeitinstitute.storyviewrepo.utils.hide
import com.creativeitinstitute.storyviewrepo.utils.show
import java.util.Calendar
import java.util.Locale

class StoryDisplayFragment : Fragment(),
    StoriesProgressView.StoriesListener {

    private val position: Int by
    lazy { arguments?.getInt(EXTRA_POSITION) ?: 0 }

    private val storyUser: StoryUser by
    lazy {
        (arguments?.getParcelable<StoryUser>(
            EXTRA_STORY_USER
        ) as StoryUser)
    }

    private val stories: ArrayList<Story> by
    lazy { storyUser.stories }


    private var pageViewOperator: PageViewOperator? = null
    private var counter = 0
    private var pressTime = 0L
    private var limit = 500L
    private var onResumeCalled = false
    private var onVideoPrepared = false


    lateinit var binding: FragmentStoryDisplayBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentStoryDisplayBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateStory()
        setUpUi()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.pageViewOperator = context as PageViewOperator
    }

    override fun onStart() {
        super.onStart()
        counter = restorePosition()
    }

    override fun onResume() {
        super.onResume()
        onResumeCalled = true


        if (counter == 0) {
            binding.storiesProgressView?.startStories()
        } else {
            // restart animation
            counter = MainActivity.progressState.get(arguments?.getInt(EXTRA_POSITION) ?: 0)
            binding. storiesProgressView?.startStories(counter)
        }
    }

    override fun onPause() {
        super.onPause()

        binding.  storiesProgressView?.abandon()
    }

    override fun onComplete() {

        pageViewOperator?.nextPageView()
    }

    override fun onPrev() {
        if (counter - 1 < 0) return
        --counter
        savePosition(counter)
        updateStory()
    }

    override fun onNext() {
        if (stories.size <= counter + 1) {
            return
        }
        ++counter
        savePosition(counter)
        updateStory()
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

    private fun updateStory() {

        if (stories[counter].isVideo()) {

            binding.  storyDisplayImage.hide()
            binding.   storyDisplayVideoProgress.show()

        } else {

            binding.    storyDisplayVideoProgress.hide()
            binding.    storyDisplayImage.show()
            Glide.with(this).load(stories[counter].url).into( binding. storyDisplayImage)
        }

        val cal: Calendar = Calendar.getInstance(Locale.ENGLISH).apply {
            timeInMillis = stories[counter].storyDate
        }
        binding.  storyDisplayTime.text = DateFormat.format("MM-dd-yyyy HH:mm:ss", cal).toString()
    }


    private fun setUpUi() {
        val touchListener = object : OnSwipeTouchListener(requireActivity()) {
            override fun onSwipeTop() {
                Toast.makeText(activity, "onSwipeTop", Toast.LENGTH_LONG).show()
            }

            override fun onSwipeBottom() {
                Toast.makeText(activity, "onSwipeBottom", Toast.LENGTH_LONG).show()
            }

            override fun onClick(view: View) {
                when (view) {
                   binding. next -> {
                        if (counter == stories.size - 1) {
                            pageViewOperator?.nextPageView()
                        } else {
                            binding.   storiesProgressView?.skip()
                        }
                    }
                   binding. previous -> {
                        if (counter == 0) {
                            pageViewOperator?.backPageView()
                        } else {
                            binding.    storiesProgressView?.reverse()
                        }
                    }
                }
            }

            override fun onLongClick() {
                hideStoryOverlay()
            }

            override fun onTouchView(view: View, event: MotionEvent): Boolean {
                super.onTouchView(view, event)
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        pressTime = System.currentTimeMillis()
                        pauseCurrentStory()
                        return false
                    }
                    MotionEvent.ACTION_UP -> {
                        showStoryOverlay()
                        resumeCurrentStory()
                        return limit < System.currentTimeMillis() - pressTime
                    }
                }
                return false
            }
        }
        binding.    previous.setOnTouchListener(touchListener)
        binding.   next.setOnTouchListener(touchListener)

        binding.  storiesProgressView?.setStoriesCountDebug(
            stories.size, position = arguments?.getInt(EXTRA_POSITION) ?: -1
        )
        binding.   storiesProgressView?.setAllStoryDuration(4000L)
        binding.   storiesProgressView?.setStoriesListener(this)

        Glide.with(this).load(storyUser.profilePicUrl).circleCrop().into( binding. storyDisplayProfilePicture)
        binding.     storyDisplayNick.text = storyUser.username
    }

    private fun showStoryOverlay() {
        if ( binding. storyOverlay == null ||  binding. storyOverlay.alpha != 0F) return

        binding. storyOverlay.animate()
            .setDuration(100)
            .alpha(1F)
            .start()
    }

    private fun hideStoryOverlay() {
        if ( binding. storyOverlay == null ||  binding. storyOverlay.alpha != 1F) return

        binding. storyOverlay.animate()
            .setDuration(200)
            .alpha(0F)
            .start()
    }

    private fun savePosition(pos: Int) {
        MainActivity.progressState.put(position, pos)
    }

    private fun restorePosition(): Int {
        return MainActivity.progressState.get(position)
    }

    fun pauseCurrentStory() {

        binding. storiesProgressView?.pause()
    }

    fun resumeCurrentStory() {
        if (onResumeCalled) {

            showStoryOverlay()
            binding.   storiesProgressView?.resume()
        }
    }

    companion object {
        private const val EXTRA_POSITION = "EXTRA_POSITION"
        private const val EXTRA_STORY_USER = "EXTRA_STORY_USER"
        fun newInstance(position: Int, story: StoryUser): StoryDisplayFragment {
            return StoryDisplayFragment().apply {
                arguments = Bundle().apply {
                    putInt(EXTRA_POSITION, position)
                    putParcelable(EXTRA_STORY_USER, story)
                }
            }
        }
    }
}