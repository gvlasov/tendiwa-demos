package org.tendiwa.plane.derasterization

import org.tendiwa.canvas.algorithms.geometry.drawPolygon
import org.tendiwa.canvas.algorithms.grid.drawGridMask
import org.tendiwa.canvas.awt.AwtCanvas
import org.tendiwa.derasterization.polygons.derasterized
import org.tendiwa.plane.grid.constructors.GridRectangle
import org.tendiwa.plane.grid.dimensions.by
import org.tendiwa.plane.grid.masks.boundedBy
import org.tendiwa.plane.grid.masks.move
import org.tendiwa.plane.grid.masks.union
import org.tendiwa.plane.grid.rectangles.hulls.GridRectangularHull
import java.awt.Color

fun main(args: Array<String>) {
    val component1 = GridRectangle(10 by 10)
    val component2 = component1.move(20, 20)
    AwtCanvas(scale = 6, size = 30 by 30).apply {
        component1
            .union(component2)
            .boundedBy(GridRectangularHull(component1, component2))
            .apply { drawGridMask(this, Color.blue) }
            .derasterized
            .first()
            .enclosing
            .let { drawPolygon(it, Color.red) }
    }
}

