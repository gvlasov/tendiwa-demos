package org.jojman.expression

import org.tendiwa.canvas.awt.AwtCanvas
import org.tendiwa.plane.geometry.points.Point
import java.awt.Color

fun main(args: Array<String>) {
    AwtCanvas()
        .apply {
            drawText(
                "FUCK YOU",
                Point(100.0, 100.0),
                Color.RED
            )
        }
}
