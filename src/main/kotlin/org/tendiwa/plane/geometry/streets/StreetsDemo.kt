package org.tendiwa.plane.geometry.streets

import org.tendiwa.canvas.algorithms.geometry.draw
import org.tendiwa.canvas.awt.AwtCanvas
import org.tendiwa.plane.directions.CardinalDirection.E
import org.tendiwa.plane.directions.CardinalDirection.N
import org.tendiwa.plane.directions.OrdinalDirection.*
import org.tendiwa.plane.geometry.points.Point
import org.tendiwa.plane.geometry.trails.Polygon
import org.tendiwa.plane.grid.dimensions.by
import java.awt.Color

fun main(args: Array<String>) {
    val polygon = Polygon(
        Point(10.0, 10.0),
        {
            move(50.0, E)
            move(30.0, NE)
            move(30.0, E)
            move(40.0, NW)
            move(20.0, N)
        }
    )
    AwtCanvas(size = 400 by 400, scale = 1)
        .apply {
            draw(polygon, Color.red)
        }
}

