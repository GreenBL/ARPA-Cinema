package pwm.ar.arpacinema.util

import com.google.android.material.shape.CornerTreatment
import com.google.android.material.shape.EdgeTreatment
import com.google.android.material.shape.ShapePath


class CircleEdgeTreatment(val size: Float) : CornerTreatment() {
    @Deprecated("Deprecated in Java")
    override fun getCornerPath(angle: Float, interpolation: Float, shapePath: ShapePath) {
        val interpolatedRadius = size * interpolation
        shapePath.reset(0f, interpolatedRadius, ANGLE_LEFT, ANGLE_LEFT - angle)
        shapePath.addArc(
            -interpolatedRadius,
            -interpolatedRadius,
            interpolatedRadius,
            interpolatedRadius,
            ANGLE_BOTTOM,
            -angle
        )
    }

    companion object {
        const val ANGLE_LEFT = 180f
        const val ANGLE_BOTTOM = 90f
    }
}