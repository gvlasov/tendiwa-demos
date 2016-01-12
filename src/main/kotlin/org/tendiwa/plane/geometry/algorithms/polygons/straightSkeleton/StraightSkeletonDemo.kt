package org.tendiwa.plane.geometry.algorithms.polygons.straightSkeleton

import org.tendiwa.canvas.algorithms.geometry.draw
import org.tendiwa.canvas.awt.AwtCanvas
import org.tendiwa.plane.directions.CardinalDirection.*
import org.tendiwa.plane.directions.OrdinalDirection.*
import org.tendiwa.plane.geometry.algorithms.polygons.shrinking.shrink
import org.tendiwa.plane.geometry.trails.Trail
import org.tendiwa.plane.geometry.trails.polygon
import java.awt.Color

fun main(args: Array<String>) {
    AwtCanvas().apply {
        val polygon =
            //            Rectangle(
            //                ZeroPoint(),
            //                100.0 by 100.0
            //            )
            Trail(100.0, 100.0).apply {
                move(30.0, NE)
                move(35.0, E)
                move(13.0, S)
                move(20.0, NE)
                move(70.0, S)
                move(170.0, W)
            }
                .polygon
        draw(polygon, Color.black)
        polygon.shrink(8.0).forEach {
            draw(it, Color.red)
        }
    }
}

