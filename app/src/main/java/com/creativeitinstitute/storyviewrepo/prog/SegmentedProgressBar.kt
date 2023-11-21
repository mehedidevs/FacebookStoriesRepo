//package com.creativeitinstitute.storyviewrepo.prog
//
//import android.content.Context
//import android.graphics.Color
//import android.graphics.Interpolator
//import android.graphics.Paint
//import android.graphics.Path
//import android.util.AttributeSet
//import android.view.View
//import android.view.animation.LinearInterpolator
//import androidx.annotation.ColorInt
//import androidx.annotation.FloatRange
//import com.creativeitinstitute.storyviewrepo.R
//
//
//class SegmentedProgressBar @JvmOverloads constructor(
//    context: Context,
//    attrs: AttributeSet? = null,
//    defStyleAttr: Int = 0
//) : View(context, attrs, defStyleAttr) {
//
//    @get:ColorInt
//    var segmentColor: Int = Color.WHITE
//        set(value) {
//            if (field != value) {
//                field = value
//                invalidate()
//            }
//        }
//
//    @get:ColorInt
//    var progressColor: Int = Color.GREEN
//        set(value) {
//            if (field != value) {
//                field = value
//                invalidate()
//            }
//        }
//
//    var spacing: Float = 0f
//        set(value) {
//            if (field != value) {
//                field = value
//                invalidate()
//            }
//        }
//
//    // TODO : Voluntarily coerce value between those angle to avoid breaking quadrilateral shape
//    @FloatRange(from = 0.0, to = 60.0)
//    var angle: Float = 0f
//        set(value) {
//            if (field != value) {
//                field = value.coerceIn(0f, 60f)
//                invalidate()
//            }
//        }
//
//    @FloatRange(from = 0.0, to = 1.0)
//    var segmentAlpha: Float = 1f
//        set(value) {
//            if (field != value) {
//                field = value.coerceIn(0f, 1f)
//                invalidate()
//            }
//        }
//
//    @FloatRange(from = 0.0, to = 1.0)
//    var progressAlpha: Float = 1f
//        set(value) {
//            if (field != value) {
//                field = value.coerceIn(0f, 1f)
//                invalidate()
//            }
//        }
//
//    var segmentCount: Int = 1
//        set(value) {
//            val newValue =Math.max(1, value)
//            if (field != newValue) {
//                field = newValue
//                initSegmentPaths()
//                invalidate()
//            }
//        }
//
//    var progressDuration: Long = 300L
//
//    var progressInterpolator: Interpolator = LinearInterpolator()
//
//    var progress: Int = 0
//        private set
//
//    private var animatedProgressSegmentCoordinates: SegmentCoordinates? = null
//    private val progressPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
//    private val progressPath: Path = Path()
//    private val segmentPaints: MutableList<Paint> = mutableListOf()
//    private val segmentPaths: MutableList<Path> = mutableListOf()
//    private val segmentCoordinatesComputer: SegmentCoordinatesComputer = SegmentCoordinatesComputer()
//
//    init {
//        context.obtainStyledAttributes(attrs, R.styleable.SegmentedProgressBar, defStyleAttr, 0).run {
//            segmentCount = getInteger(R.styleable.SegmentedProgressBar_spb_count, segmentCount)
//            segmentAlpha = getFloat(R.styleable.SegmentedProgressBar_spb_segmentAlpha, segmentAlpha)
//            progressAlpha = getFloat(R.styleable.SegmentedProgressBar_spb_progressAlpha, progressAlpha)
//            segmentColor = getColor(R.styleable.SegmentedProgressBar_spb_segmentColor, segmentColor)
//            progressColor = getColor(R.styleable.SegmentedProgressBar_spb_progressColor, progressColor)
//            spacing = getDimension(R.styleable.SegmentedProgressBar_spb_spacing, spacing)
//            angle = getFloat(R.styleable.SegmentedProgressBar_spb_angle, angle)
//            progressDuration = getInteger(R.styleable.SegmentedProgressBar_spb_duration, progressDuration)
//            recycle()
//        }
//
//        initSegmentPaths()
//    }
//
//    fun setProgress(progress: Int, animated: Boolean = false) {
//        doOnLayout {
//            val newProgressCoordinates =
//                segmentCoordinatesComputer.progressCoordinates(progress, segmentCount, width.toFloat(), height.toFloat(), spacing, angle)
//
//            if (animated) {
//                val oldProgressCoordinates =
//                    segmentCoordinatesComputer.progressCoordinates(this.progress, segmentCount, width.toFloat(), height.toFloat(), spacing, angle)
//
//                ValueAnimator.ofFloat(0f, 1f)
//                    .apply {
//                        duration = progressDuration
//                        interpolator = progressInterpolator
//                        addUpdateListener {
//                            val animationProgress = it.animatedValue as Float
//                            val topRightXDiff = oldProgressCoordinates.topRightX.lerp(newProgressCoordinates.topRightX, animationProgress)
//                            val bottomRightXDiff = oldProgressCoordinates.bottomRightX.lerp(newProgressCoordinates.bottomRightX, animationProgress)
//                            animatedProgressSegmentCoordinates = SegmentCoordinates(0f, topRightXDiff, 0f, bottomRightXDiff)
//                            invalidate()
//                        }
//                        start()
//                    }
//            } else {
//                animatedProgressSegmentCoordinates = SegmentCoordinates(0f, newProgressCoordinates.topRightX, 0f, newProgressCoordinates.bottomRightX)
//                invalidate()
//            }
//
//            this.progress = progress.coerceIn(0, segmentCount)
//        }
//    }
//
//    private fun initSegmentPaths() {
//        segmentPaths.clear()
//        segmentPaints.clear()
//        (0 until segmentCount).forEach { _ ->
//            segmentPaths.add(Path())
//            segmentPaints.add(Paint(Paint.ANTI_ALIAS_FLAG))
//        }
//    }
//
//    private fun drawSegment(canvas: Canvas, path: Path, paint: Paint, coordinates: SegmentCoordinates, color: Int, alpha: Float) {
//        path.run {
//            reset()
//            moveTo(coordinates.topLeftX, 0f)
//            lineTo(coordinates.topRightX, 0f)
//            lineTo(coordinates.bottomRightX, height.toFloat())
//            lineTo(coordinates.bottomLeftX, height.toFloat())
//            close()
//        }
//
//        paint.color = color
//        paint.alpha = alpha.toAlphaPaint()
//
//        canvas.drawPath(path, paint)
//    }
//
//    override fun onDraw(canvas: Canvas) {
//        val w = width.toFloat()
//        val h = height.toFloat()
//
//        (0 until segmentCount).forEach { position ->
//            val path = segmentPaths[position]
//            val paint = segmentPaints[position]
//            val segmentCoordinates = segmentCoordinatesComputer.segmentCoordinates(position, segmentCount, w, h, spacing, angle)
//
//            drawSegment(canvas, path, paint, segmentCoordinates, segmentColor, segmentAlpha)
//        }
//
//        animatedProgressSegmentCoordinates?.let { drawSegment(canvas, progressPath, progressPaint, it, progressColor, progressAlpha) }
//    }
//}