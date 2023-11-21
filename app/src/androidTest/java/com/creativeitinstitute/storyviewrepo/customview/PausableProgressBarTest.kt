package com.creativeitinstitute.storyviewrepo.customview


import android.content.Context
import android.view.View
import androidx.test.platform.app.InstrumentationRegistry

import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class PausableProgressBarTest {

    private lateinit var pausableProgressBar: PausableProgressBar

    @Before
    fun setUp() {
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
        pausableProgressBar = PausableProgressBar(context)
    }

    @Test
    fun testSetDuration() {
        val duration = 2000L
        pausableProgressBar.setDuration(duration)
        val animation = pausableProgressBar.animation
        assertEquals(duration, animation.duration)
    }

    @Test
    fun testSetCallback() {
        val callback = mock(PausableProgressBar.Callback::class.java)
        pausableProgressBar.setCallback(callback)
        val storedCallback = pausableProgressBar.getCallbackForTesting()
        assertEquals(callback, storedCallback)
    }
    @Test
    fun testSetMax() {
        pausableProgressBar.setMax()

        val maxProgressView = pausableProgressBar.getMaxProgressViewForTesting()

        assertEquals(View.VISIBLE, maxProgressView?.visibility)
        // Adjust this according to your actual implementation
        // assertEquals(R.color.progress_max_active, ShadowView.viewBgColor)
    }

    // ... (other tests)

    @Test
    fun testFinishProgress() {
        val callback = mock(PausableProgressBar.Callback::class.java)
        pausableProgressBar.setCallback(callback)

        pausableProgressBar.finishProgress(true)

        // Verify that onFinishProgress() is called
        verify(callback).onFinishProgress()
    }

    // ... (other tests)
}




