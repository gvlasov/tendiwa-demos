package org.tendiwa.plane.geometry.polygons.masked

import org.tendiwa.canvas.algorithms.geometry.drawSegmentGroup
import org.tendiwa.canvas.awt.AwtCanvas
import org.tendiwa.canvas.awt.gif.AnimationFrame
import org.tendiwa.canvas.awt.gif.Fps
import org.tendiwa.canvas.awt.gif.Gif
import org.tendiwa.canvas.awt.gif.showInBrowser
import org.tendiwa.math.sliders.CircularSlider
import org.tendiwa.math.sliders.move
import org.tendiwa.plane.geometry.points.Point
import org.tendiwa.plane.geometry.polygons.Polygon
import org.tendiwa.plane.grid.dimensions.by
import java.awt.Color

fun main(args: Array<String>) {
    val polygon = Polygon(
        Point(10.0, 10.0),
        {
            moveX(20.0)
            move(20.0, 5.0)
            move(20.0, 10.0)
            move(-5.0, 5.0)
            move(-20.0, -10.0)
            moveY(10.0)
            move(20.0, 10.0)
            move(-10.0, 18.0)
            move(-10.0, -5.0)
        }
    )
    Gif(
        AwtCanvas(size = 100 by 100, scale = 4),
        IntRange(0, 99)
            .map { index ->
                AnimationFrame {
                    val scorched = polygon.mask(
                        IntRange(0, 9)
                            .map { scorchIndex ->
                                PerimeterPiece(
                                    CircularSlider(1.0 / 100 * index)
                                        .move(0.1 * scorchIndex),
                                    10.5 * (index + 1) / 100.0
                                )
                            }
                    )
                    scorched.unmasked.forEach {
                        drawSegmentGroup(it, Color.red)
                    }
                }
            }
    )
        .showInBrowser(Fps.MAX)
}
