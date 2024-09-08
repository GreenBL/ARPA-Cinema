package pwm.ar.arpacinema.util

import android.graphics.Color
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable

object PlaceholderDrawable {

    fun getPlaceholderDrawable(): ShimmerDrawable {
        val shimmer = Shimmer.AlphaHighlightBuilder()
            .setDuration(1800) // how long the shimmering animation takes to do one full sweep
            .setBaseAlpha(0.7f) //the alpha of the underlying children
            .setHighlightAlpha(0.6f) // the shimmer alpha amount
            .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
            .setAutoStart(true)
            .build()

        val shimmerDrawable = ShimmerDrawable()
        shimmerDrawable.setShimmer(shimmer)

        return shimmerDrawable

    }
}