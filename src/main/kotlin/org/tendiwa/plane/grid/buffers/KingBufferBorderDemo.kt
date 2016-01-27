package org.tendiwa.plane.grid.buffers

import org.tendiwa.canvas.algorithms.grid.draw
import org.tendiwa.canvas.awt.AwtCanvas
import org.tendiwa.math.noise.PerlinNoise
import org.tendiwa.plane.grid.algorithms.buffers.kingBufferBorder
import org.tendiwa.plane.grid.constructors.GridRectangle
import org.tendiwa.plane.grid.dimensions.by
import org.tendiwa.plane.grid.masks.boundedBy
import org.tendiwa.plane.grid.masks.inverse
import org.tendiwa.plane.grid.tiles.Tile
import java.awt.Color

fun main(args: Array<String>) {
    val viewport = GridRectangle(Tile(0, 0), 800 by 800)
    val mask = NoiseGridMask(
        PerlinNoise(7),
        scale = 128,
        hull = viewport,
        limit = 128
    )
    val bufferBorder = mask.inverse
        .kingBufferBorder(
            depth = 5,
            thickness = 3
        )
        .boundedBy(viewport)
    AwtCanvas(viewport, 1).apply {
        draw(mask, Color.green)
        draw(mask.inverse.boundedBy(viewport), Color.blue)
        draw(bufferBorder, Color.red)
    }
}
