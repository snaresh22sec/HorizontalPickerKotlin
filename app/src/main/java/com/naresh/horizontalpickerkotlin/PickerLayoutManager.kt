package com.naresh.horizontalpickerkotlin

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler



class PickerLayoutManager(context: Context?, orientation: Int, reverseLayout: Boolean) :
    LinearLayoutManager(context, orientation, reverseLayout) {
    var scaleDownBy = 0.66f
    var scaleDownDistance = 0.9f
    var isChangeAlpha = true
    private var onScrollStopListener: OnScrollStopListener? = null


    override fun onLayoutChildren(recycler: Recycler, state: RecyclerView.State) {
        super.onLayoutChildren(recycler, state)
        scaleDownView()
    }

    override fun scrollHorizontallyBy(dx: Int, recycler: Recycler, state: RecyclerView.State): Int {
        val orientation = orientation
        return if (orientation == HORIZONTAL) {
            val scrolled = super.scrollHorizontallyBy(dx, recycler, state)
            scaleDownView()
            scrolled
        } else 0
    }

    private fun scaleDownView() {
        val mid = width / 2.0f
        val unitScaleDownDist = scaleDownDistance * mid
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val childMid = (getDecoratedLeft(child!!) + getDecoratedRight(child)) / 2.0f
            val scale = 1.0f + -1 * scaleDownBy * Math.min(
                unitScaleDownDist,
                Math.abs(mid - childMid)
            ) / unitScaleDownDist
            child.scaleX = scale
            child.scaleY = scale
            if (isChangeAlpha) {
                child.alpha = scale
            }
        }
    }

    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)
        if (state == 0) {
            if (onScrollStopListener != null) {
                var selected = 0
                var lastHeight = 0f
                for (i in 0 until childCount) {
                    if (lastHeight < getChildAt(i)!!.scaleY) {
                        lastHeight = getChildAt(i)!!.scaleY
                        selected = i
                    }
                }
                onScrollStopListener!!.selectedView(getChildAt(selected))
            }
        }
    }

    fun setOnScrollStopListener(onScrollStopListener: OnScrollStopListener?) {
        this.onScrollStopListener = onScrollStopListener
    }

    interface OnScrollStopListener {
        fun selectedView(view: View?)
    }
}