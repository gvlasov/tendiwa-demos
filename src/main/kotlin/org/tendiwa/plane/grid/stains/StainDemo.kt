package org.tendiwa.plane.grid.stains

import org.tendiwa.canvas.algorithms.grid.drawGridMask
import org.tendiwa.canvas.awt.AwtCanvas
import org.tendiwa.math.noise.PerlinNoise
import org.tendiwa.math.noise.scale
import org.tendiwa.plane.grid.algorithms.stains.Stain
import org.tendiwa.plane.grid.dimensions.by
import org.tendiwa.plane.grid.tiles.Tile
import java.awt.Color

fun main(args: Array<String>) {
    val size = 150
    val stain = Stain(
        start = Tile(size, size),
        noise = PerlinNoise(octave = 7).scale(1.0 / 40.0),
        size = size
    )
    AwtCanvas(size * 2 by size * 2, scale = 1)
        .apply {
            drawGridMask(stain, Color.red)
        }
}
