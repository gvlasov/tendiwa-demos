package org.tendiwa.plane.grid.pathfinding

import org.tendiwa.canvas.algorithms.grid.drawGridMask
import org.tendiwa.canvas.awt.AwtCanvas
import org.tendiwa.plane.grid.algorithms.pathfinding.astar.path
import org.tendiwa.plane.grid.constructors.GridRectangle
import org.tendiwa.plane.grid.dimensions.by
import org.tendiwa.plane.grid.masks.difference
import org.tendiwa.plane.grid.tiles.Tile
import java.awt.Color

fun main(args: Array<String>) {
    val viewport = GridRectangle(120 by 120)
    val passableMask = GridRectangle(100 by 100)
        .difference(GridRectangle(Tile(10, 30), 80 by 20))
    val path = passableMask.path(
        start = Tile(40, 5),
        end = Tile(50, 90)
    )!!
    AwtCanvas(viewport).apply {
        drawGridMask(passableMask, Color.green)
        drawGridMask(path, Color.red)
    }
}

