package com.mycv.android.animated_header

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import kotlinx.android.synthetic.main.animated_header.view.*
import kotlin.math.abs
import kotlin.math.max



class AnimatedHeader @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppBarLayout(context, attrs) {

    private var expandedImageSize = 0F
    private var collapsedImageSize = 0F

    private var toolbarView: Toolbar
    private var title: TextView
    private var subtitle: TextView
    private var toolbarTitle: TextView

    private var calculatedWidth: Int = 0

    init {
        inflate(context, R.layout.animated_header, this)

        val arr = context.obtainStyledAttributes(attrs, R.styleable.AnimatedHeader)
        expandedImageSize = arr.getDimension(R.styleable.AnimatedHeader_expanded_size, 0F)
        collapsedImageSize = arr.getDimension(R.styleable.AnimatedHeader_collapsed_size, 0F)
        arr.recycle()

        toolbarView = toolbar
        title = titleView
        subtitle = subtitleView
        toolbarTitle = toolbarTitleView

        post {
            calculatedWidth = width
        }

        image.layoutParams.apply {
            width = expandedImageSize.toInt()
            height = expandedImageSize.toInt()
        }

        setupScrollListener()
    }

    fun setUp(title: String? = null,
              subTitle: String? = null,
              toolbarTitle: String? = null,
              imageDrawable: Drawable? = null) {
        setText(this.title, title)
        setText(this.subtitle, subTitle)
        setText(this.toolbarTitle, toolbarTitle)

        imageDrawable?.let {
            image.setImageDrawable(it)
        }
    }

    private fun setText(view: TextView, text: String? = null) {
        text?.let {
            view.text = it
        }
    }

    private fun setupScrollListener() {
        val leftMargin = (toolbar.layoutParams as MarginLayoutParams).leftMargin

        (toolbarTitle.layoutParams as MarginLayoutParams).leftMargin = leftMargin * 2 + collapsedImageSize.toInt()

        addOnOffsetChangedListener(OnOffsetChangedListener { _, dy ->
            val offset = abs(dy / totalScrollRange.toFloat())


            title.alpha = (1 - offset)
            subtitle.alpha = (1 - offset)
            toolbarTitle.alpha = if (offset > TEXT_ALPHA_THRESHOLD) (offset - TEXT_ALPHA_THRESHOLD) * SCALE_ALPHA_FACTOR * offset else 0F

            image.apply {
                val avatarSize = (expandedImageSize - (expandedImageSize - collapsedImageSize) * offset).toInt()

                left = max(leftMargin, (((calculatedWidth - avatarSize) / 2) * (1 - offset)).toInt())
                right = left + avatarSize
                top = (toolbarView.height - collapsedImageSize.toInt()) / 2 - dy
                bottom = top + avatarSize
            }

        })
    }

    companion object {
        const val TEXT_ALPHA_THRESHOLD = 0.8F
        const val SCALE_ALPHA_FACTOR = 1.0F / (1 - TEXT_ALPHA_THRESHOLD)
    }
}
