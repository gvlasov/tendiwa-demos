package org.tendiwa.geometry.algorithms.polygons.randomPointsOnPerimeter

import org.tendiwa.canvas.algorithms.geometry.draw
import org.tendiwa.canvas.awt.AwtCanvas
import org.tendiwa.geometry.circles.Circle
import org.tendiwa.geometry.points.Point
import org.tendiwa.geometry.polygons.perimeter
import org.tendiwa.geometry.trails.Polygon
import org.tendiwa.grid.dimensions.by
import org.tendiwa.plane.directions.OrdinalDirection.*
import java.awt.Color

fun main(args: Array<String>) {
    AwtCanvas(size = 100 by 100, scale = 8)
        .apply {
            val gap = 2.0
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
            val regularPoints = polygon.pointsOnPerimeter(
                RegularPoint1DCirculars(
                    (polygon.perimeter / gap).toInt()
                )
            )
                .map { Circle(it, 0.4) }
            val randomPoints = polygon.randomPointsOnPerimeter(
                gap = gap,
                gapVariance = 9.0
            )
                .map { Circle(it, 0.4) }
            draw(polygon, Color.black)
            regularPoints.forEach { draw(it, Color.blue) }
            randomPoints.forEach { draw(it, Color.red) }
        }
}
