//package com.creativeitinstitute.storyviewrepo.prog
//
//class SegmentCoordinatesComputer {
//
//    fun segmentCoordinates(position: Int, segmentCount: Int, width: Float, spacing: Float): SegmentCoordinates {
//
//        return  SegmentCoordinates()
//    }
//
//    fun progressCoordinates(progress: Int, segmentCount: Int, width: Float, spacing: Float): SegmentCoordinates {
//        val segmentWidth = (width - spacing * (segmentCount - 1)) / segmentCount
//        val isLast = progress == segmentCount
//
//        val topRight = segmentWidth * progress + spacing * Math.max(0, progress - 1)
//        val bottomRight = segmentWidth * progress + spacing * Math.max(0, progress - 1)
//
//        return SegmentCoordinates(0f, topRight, 0f, bottomRight)
//    }
//}