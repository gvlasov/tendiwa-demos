package org.tendiwa.plane.geometry.algorithms.buffers

import org.tendiwa.canvas.algorithms.grid.drawGridMask
import org.tendiwa.canvas.awt.AwtCanvas
import org.tendiwa.plane.grid.algorithms.buffers.deflate
import org.tendiwa.plane.grid.dimensions.by
import org.tendiwa.plane.grid.masks.boundedBy
import org.tendiwa.plane.grid.rectangles.GridRectangle
import org.tendiwa.plane.grid.rectangles.grow
import java.awt.Color

fun main(args: Array<String>) {
    AwtCanvas(200 by 200).apply {
        val mask = GridRectangle(20, 20, 5, 5)
        drawGridMask(
            mask
                .deflate(-10)
                .boundedBy(mask.hull.grow(10)),
            Color.red
        )
    }
}
