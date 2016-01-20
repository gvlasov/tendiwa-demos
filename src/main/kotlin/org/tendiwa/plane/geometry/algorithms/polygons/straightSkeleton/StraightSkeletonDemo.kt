package org.tendiwa.plane.geometry.algorithms.polygons.straightSkeleton

import org.tendiwa.canvas.algorithms.geometry.draw
import org.tendiwa.canvas.awt.AwtCanvas
import org.tendiwa.plane.directions.CardinalDirection.*
import org.tendiwa.plane.directions.OrdinalDirection.*
import org.tendiwa.plane.geometry.algorithms.polygons.shrinking.shrink
import org.tendiwa.plane.geometry.points.Point
import org.tendiwa.plane.geometry.polygons.Polygon
import java.awt.Color

fun main(args: Array<String>) {
    AwtCanvas().apply {
        val polygon =
            //            Rectangle(
            //                ZeroPoint(),
            //                100.0 by 100.0
            //            )
            Polygon(
                Point(100.0, 100.0),
                {
                    move(30.0, NE)
                    move(35.0, E)
                    move(13.0, S)
                    move(20.0, NE)
                    move(70.0, S)
                    move(170.0, W)
                }
            )
        draw(polygon, Color.black)
        polygon.shrink(8.0).forEach {
            draw(it, Color.red)
        }
    }
}

