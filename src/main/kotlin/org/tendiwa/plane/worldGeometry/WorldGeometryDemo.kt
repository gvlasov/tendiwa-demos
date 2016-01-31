package org.tendiwa.plane.worldGeometry

import org.tendiwa.canvas.awt.AwtCanvas
import org.tendiwa.plane.grid.constructors.GridRectangle
import org.tendiwa.plane.grid.dimensions.by
import org.tendiwa.plane.grid.tiles.Tile

fun main(args: Array<String>) {
    val viewport = GridRectangle(Tile(20, 20), 400 by 400)
    val geometry = WorldGeometry(viewport)
    AwtCanvas(viewport, scale = 3)
        .apply {
            draw(geometry)
        }
}
