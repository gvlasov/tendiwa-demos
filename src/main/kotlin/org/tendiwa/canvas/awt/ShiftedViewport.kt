package org.tendiwa.canvas.awt

import org.tendiwa.plane.grid.rectangles.GridRectangle
import java.awt.Color

fun main(args: Array<String>) {
    AwtCanvas(
        GridRectangle(-5, -5, 200, 200),
        scale = 1
    ).apply {
        draw(GridRectangle(0, 0, 20, 20), Color.green)
    }
}
