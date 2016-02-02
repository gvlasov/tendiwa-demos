package org.tendiwa.canvas.awt.gif

import org.tendiwa.canvas.awt.AwtCanvas
import org.tendiwa.plane.grid.dimensions.by
import org.tendiwa.plane.grid.rectangles.GridRectangle
import java.awt.Color

fun main(args: Array<String>) {
    Gif(
        AwtCanvas(300 by 300, scale = 2),
        IntRange(0, 100)
            .map { index ->
                AnimationFrame {
                    draw(
                        GridRectangle(
                            index * 3,
                            index * 3,
                            40,
                            40
                        ),
                        Color.red
                    )
                }
            }
    )
        .showInBrowser(Fps.MAX)
}
