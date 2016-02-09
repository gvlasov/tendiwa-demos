package org.tendiwa.math.noise

import org.tendiwa.canvas.algorithms.grid.drawTile
import org.tendiwa.canvas.awt.AwtCanvas
import org.tendiwa.plane.grid.dimensions.by
import org.tendiwa.plane.grid.tiles.Tile
import java.awt.Color

fun main(args: Array<String>) {
    val noise = PerlinNoise(octave = 6)
    AwtCanvas(800 by 800, 1).apply {
        viewport.tiles
            .forEach {
                val noiseValue = noise.at(it, 120)
                if (noiseValue > 150) {
                    drawTile(it, Color.yellow)
                } else if (noiseValue > 120)
                    drawTile(it, Color.green)
                else {
                    drawTile(it, Color.blue)
                }
            }
    }
}

fun Noise2D.at(tile: Tile, scale: Int): Int =
    at(tile.x.toDouble() / scale, tile.y.toDouble() / scale)
