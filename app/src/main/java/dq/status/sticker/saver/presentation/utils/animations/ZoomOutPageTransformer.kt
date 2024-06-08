package dq.status.sticker.saver.presentation.utils.animations

import android.view.View
import androidx.viewpager.widget.ViewPager

class ZoomOutPageTransformer : ViewPager.PageTransformer {
    override fun transformPage(page: View, position: Float) {
       /* page.apply {
            val scaleFactor = 0.85f
            val minScale = 0.75f

            val absPosition = abs(position)
            val scale = if (absPosition < 1) {
                minScale + (1 - minScale) * (1 - absPosition)
            } else {
                minScale
            }

            scaleX = scale
            scaleY = scale

            alpha = if (absPosition < 1) {
                1 - (1 - minScale) * absPosition
            } else {
                1 - minScale
            }

            translationY = height * (1 - scale) / 2f
        }*/

        page.apply {
            val alpha = when {
                position <= -1 -> 0f
                position < 0 -> 1 + position
                position < 1 -> 1 - position
                else -> 0f
            }
            this.alpha = alpha
        }
    }
}
