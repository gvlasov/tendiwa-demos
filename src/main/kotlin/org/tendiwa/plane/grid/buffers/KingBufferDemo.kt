package org.tendiwa.plane.grid.buffers

import org.tendiwa.canvas.algorithms.grid.drawGridMask
import org.tendiwa.canvas.awt.AwtCanvas
import org.tendiwa.plane.grid.algorithms.buffers.kingBuffer
import org.tendiwa.plane.grid.constructors.GridRectangle
import org.tendiwa.plane.grid.dimensions.by
import org.tendiwa.plane.grid.masks.boundedBy
import org.tendiwa.plane.grid.masks.difference
import org.tendiwa.plane.grid.masks.move
import org.tendiwa.plane.grid.tiles.Tile
import java.awt.Color

fun main(args: Array<String>) {
    val viewport = GridRectangle(120 by 120)
    val mask = GridRectangle(100 by 100)
        .difference(GridRectangle(Tile(34, 63), 100 by 20))
        .difference(GridRectangle(Tile(84, 20), 4 by 100))
        .difference(GridRectangle(Tile(10, 10), 20 by 20))
        .move(10, 10)
    val buffer = mask
        .kingBuffer(thickness = 8)
        .boundedBy(viewport)
    AwtCanvas(viewport).apply {
        drawGridMask(mask, Color.green)
        drawGridMask(buffer, Color.red)
    }

}

