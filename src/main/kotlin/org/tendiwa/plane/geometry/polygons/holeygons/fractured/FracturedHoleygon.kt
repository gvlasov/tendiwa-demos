package org.tendiwa.plane.geometry.polygons.holeygons.fractured

import org.tendiwa.canvas.algorithms.geometry.graphs.draw
import org.tendiwa.canvas.awt.AwtCanvas
import org.tendiwa.math.ranges.SizeRange
import org.tendiwa.plane.geometry.graphs.fractured.SnapRadius
import org.tendiwa.plane.geometry.graphs.fractured.fracture
import org.tendiwa.plane.geometry.holeygons.Holeygon
import org.tendiwa.plane.geometry.rectangles.Rectangle
import org.tendiwa.plane.grid.dimensions.by
import java.awt.Color

fun main(args: Array<String>) {
    AwtCanvas(scale = 8, size = 120 by 120)
        .apply {
            val fracturedHoleygon =
                Holeygon(
                    Rectangle(0.0, 0.0, 100.0, 100.0),
                    listOf(
                        Rectangle(40.0, 40.0, 10.0, 10.0)
                    )
                )
                    .fracture(
                        roadsFromPoint = 5,
                        crackSegmentLengths = SizeRange(10.0..15.0),
                        snapRadius = SnapRadius(8.0),
                        crackDeviationAngle = Math.toRadians(20.0),
                        favourAxisAlignedSegments = false
                    )
            draw(fracturedHoleygon, Color.red)
        }
}
