package com.example.recyclerviewtest.staggerd

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.recyclerviewtest.DeviceUtils
import com.example.recyclerviewtest.R
import com.example.recyclerviewtest.SpannedGridLayoutManager


class StaggeredTestAdapter(var context: Activity) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mSmallImageSize: Int = 0

    var mMediumImageSize: Int = 0

    var mBigImageSize: Int = 0

    var mBorderSize: Int = 0

    init {
        initImageSize()
        Log.d("jhlee", "mSmallImageSize : $mSmallImageSize")
        Log.d("jhlee", "mMediumImageSize : $mMediumImageSize")
        Log.d("jhlee", "mBigImageSize : $mBigImageSize")
        Log.d("jhlee", "mBorderSize : $mBorderSize")
    }


    companion object {
        const val TAG_IMAGES_VIEW_SMALL = 2

        const val TAG_IMAGES_VIEW_MEDIUM = 3

        const val TAG_IMAGES_VIEW_BIG = 4
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = View.inflate(parent.context, R.layout.recycler_item, null)

        itemView.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                val lp: ViewGroup.LayoutParams = itemView.layoutParams
                if (lp is StaggeredGridLayoutManager.LayoutParams) {
                    when (viewType) {
                        TAG_IMAGES_VIEW_MEDIUM -> {
                            lp.height = mMediumImageSize
                        }
                        TAG_IMAGES_VIEW_SMALL -> {
                            lp.height = mSmallImageSize
                        }
                        TAG_IMAGES_VIEW_BIG -> {
                            lp.height = mBigImageSize
                        }
                    }
                    lp.width = SpannedGridLayoutManager.LayoutParams.MATCH_PARENT
                    itemView.layoutParams = lp
                }
                itemView.viewTreeObserver.removeOnPreDrawListener(this)
                return true
            }

        })
        return when (viewType) {
            TAG_IMAGES_VIEW_MEDIUM -> {
                return MediumImageViewHolder(
                    parent.context,
                    parent,
                    itemView,
                    mBorderSize,
                    mMediumImageSize
                )
            }
            TAG_IMAGES_VIEW_SMALL -> {
                return SmallImageViewHolder(
                    parent.context,
                    parent,
                    itemView,
                    mBorderSize,
                    mSmallImageSize
                )
            }
            TAG_IMAGES_VIEW_BIG -> {
                return BigImageViewHolder(
                    parent.context,
                    parent,
                    itemView,
                    mBorderSize,
                    mBigImageSize,
                    mSmallImageSize

                )
            }
            else -> SmallImageViewHolder(
                parent.context,
                parent,
                itemView,
                mBorderSize,
                mSmallImageSize
            )
        }
    }

    override fun getItemCount(): Int {
        return 100
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("jhlee", "onBind")
        holder as AbsImageViewHolder
        holder.onBind()
    }


    private fun initImageSize() {
        var width = DeviceUtils.getDeviceSize(context).x
        width -= (context.resources.getDimensionPixelSize(R.dimen.tag_main_layout_side_padding) * 2)
        val tempImageSize = width / 3
        var itemMarginOffset =
            context.resources.getDimensionPixelSize(R.dimen.tag_main_image_margin)
        itemMarginOffset = when (itemMarginOffset % 3) {
            0 -> itemMarginOffset
            1 -> 2 + itemMarginOffset
            2 -> 1 + itemMarginOffset
            else -> itemMarginOffset
        }
        itemMarginOffset /= 2

        mSmallImageSize = tempImageSize - itemMarginOffset
        mBorderSize = itemMarginOffset
        mBigImageSize = (mSmallImageSize * 2) + itemMarginOffset

        mMediumImageSize =
            (width - itemMarginOffset) / 2
    }

    override fun getItemViewType(position: Int): Int {
        return when (position % 3) {
            0 -> TAG_IMAGES_VIEW_MEDIUM
            1 -> TAG_IMAGES_VIEW_BIG
            2 -> TAG_IMAGES_VIEW_SMALL

            else -> TAG_IMAGES_VIEW_SMALL
        }
    }
}