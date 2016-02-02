package org.tendiwa.plane.grid.stains

import org.tendiwa.canvas.algorithms.grid.drawGridMask
import org.tendiwa.canvas.awt.AwtCanvas
import org.tendiwa.canvas.awt.gif.AnimationFrame
import org.tendiwa.canvas.awt.gif.Fps
import org.tendiwa.canvas.awt.gif.Gif
import org.tendiwa.canvas.awt.gif.showInBrowser
import org.tendiwa.math.noise.PerlinNoise
import org.tendiwa.math.noise.move
import org.tendiwa.math.noise.scale
import org.tendiwa.plane.grid.algorithms.buffers.intrudedContour
import org.tendiwa.plane.grid.algorithms.stains.Stain
import org.tendiwa.plane.grid.constructors.GridRectangle
import org.tendiwa.plane.grid.dimensions.by
import org.tendiwa.plane.grid.masks.boundedBy
import org.tendiwa.plane.grid.masks.inverse
import org.tendiwa.plane.grid.tiles.Tile
import java.awt.Color

fun main(args: Array<String>) {
    val size = 150
    val frames = 80
    val viewport = GridRectangle(size * 2 by size * 2)
    Gif(
        canvas = AwtCanvas(viewport, scale = 1),
        frames = (0..frames)
            .map { index ->
                AnimationFrame {
                    val stain = Stain(
                        start = Tile(size, size),
                        noise = PerlinNoise(octave = 7)
                            .scale(1.0 / 64 + 1 / 200 * index)
                            .move(
                                40 * Math.sin(Math.PI * 2 / frames * index),
                                40 * Math.cos(Math.PI * 2 / frames * index)
                            ),
                        size = size
                    )
                    val contour = stain.inverse
                        .intrudedContour(4, 1)
                        .boundedBy(viewport)
                    drawGridMask(stain, Color.red)
                    drawGridMask(contour, Color.blue)
                }
            }
    )
        .showInBrowser(Fps.MAX)
}
