package org.tendiwa.plane.geometry.polygons.holeygons.fractured

import org.tendiwa.canvas.algorithms.geometry.graphs.draw
import org.tendiwa.canvas.awt.AwtCanvas
import org.tendiwa.plane.geometry.graphs.fractured.fracture
import org.tendiwa.plane.geometry.holeygons.Holeygon
import org.tendiwa.plane.geometry.rectangles.Rectangle
import java.awt.Color

fun main(args: Array<String>) {
    AwtCanvas()
        .apply {
            val fracturedHoleygon =
                Holeygon(
                    Rectangle(0.0, 0.0, 100.0, 100.0),
                    listOf(
                        Rectangle(40.0, 40.0, 10.0, 10.0)
                    )
                )
                    .fracture()
            draw(
                fracturedHoleygon,
                Color.red
            )
        }
}
