package org.tendiwa.plane.geometry.algorithms.polygons

import org.tendiwa.canvas.algorithms.geometry.draw
import org.tendiwa.canvas.awt.AwtCanvas
import org.tendiwa.plane.directions.OrdinalDirection.*
import org.tendiwa.plane.geometry.algorithms.polygons.cut.random.cutRandomly
import org.tendiwa.plane.geometry.circles.Circle
import org.tendiwa.plane.geometry.points.Point
import org.tendiwa.plane.geometry.trails.Polygon
import org.tendiwa.plane.grid.dimensions.by
import java.awt.Color

fun main(args: Array<String>) {
    AwtCanvas(size = 100 by 100, scale = 8)
        .apply {
            val polygon = Polygon(
                Point(10.0, 10.0),
                {
                    move(10.0, -2.0)
                    move(3.0, SE)
                    move(1.0, NE)
                    moveX(3.0)
                    moveY(5.0)
                    move(4.0, SW)
                    move(8.0, NW)
                }
            )
            polygon
                .cutRandomly(gap = 4.0, gapVariance = 10.0)
                .cuts
                .map { Circle(it, 0.4) }
                .forEach { draw(it, Color.blue) }
            draw(polygon, Color.black)
        }
}
