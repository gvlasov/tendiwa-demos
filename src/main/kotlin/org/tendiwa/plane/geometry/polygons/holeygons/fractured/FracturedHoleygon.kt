package org.tendiwa.plane.geometry.polygons.holeygons.fractured

import org.tendiwa.canvas.algorithms.geometry.graphs.draw
import org.tendiwa.canvas.awt.AwtCanvas
import org.tendiwa.math.angles.AngularMeasure
import org.tendiwa.math.ranges.SizeRange
import org.tendiwa.plane.directions.CardinalDirection.*
import org.tendiwa.plane.directions.OrdinalDirection.*
import org.tendiwa.plane.geometry.graphs.fractured.SnapRadius
import org.tendiwa.plane.geometry.graphs.fractured.fracture
import org.tendiwa.plane.geometry.holeygons.Holeygon
import org.tendiwa.plane.geometry.points.Point
import org.tendiwa.plane.geometry.rectangles.Rectangle
import org.tendiwa.plane.geometry.trails.Polygon
import org.tendiwa.plane.grid.dimensions.by
import java.awt.Color

fun main(args: Array<String>) {
    AwtCanvas(scale = 8, size = 120 by 120)
        .apply {
            val fracturedHoleygon =
                Holeygon(
                    Polygon(Point(15.0, 5.0), {
                        move(30.0, 10.0)
                        move(10.0, NE)
                        move(10.0, SE)
                        move(20.0, SW)
                        move(20.0, E)
                        move(20.0, S)
                        move(30.0, W)
                        move(10.0, SE)
                        move(40.0, W)
                        move(10.0, NE)
                        move(10.0, N)
                        move(10.0, NE)
                        move(20.0, N)
                        move(10.0, W)
                    }),
                    listOf(
                        Rectangle(35.0, 25.0, 5.0, 5.0)
                    )
                )
                    .fracture(
                        roadsFromPoint = 4,
                        crackSegmentLengths = SizeRange(10.0..15.0),
                        snapRadius = SnapRadius(8.0),
                        crackDeviation = AngularMeasure(Math.toRadians(20.0)),
                        favourAxisAlignedSegments = false
                    )
            draw(fracturedHoleygon, Color.red)
        }
}
