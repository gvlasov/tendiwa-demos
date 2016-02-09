package org.tendiwa.plane.rasterization

import org.tendiwa.canvas.algorithms.geometry.drawPolygon
import org.tendiwa.canvas.algorithms.grid.drawGridMask
import org.tendiwa.canvas.awt.AwtCanvas
import org.tendiwa.plane.geometry.points.Point
import org.tendiwa.plane.geometry.polygons.Polygon
import org.tendiwa.plane.grid.algorithms.rectangles.maximalRectangle
import org.tendiwa.plane.grid.dimensions.by
import org.tendiwa.plane.rasterization.polygon.rasterize
import org.tendiwa.plane.rasterization.segmentGroups.gridHull
import java.awt.Color

fun main(args: Array<String>) {
    AwtCanvas(100 by 100, scale = 5).apply {
        Polygon (
            Point(0.0, 0.0),
            Point(44.0, 34.0),
            Point(12.0, 88.0)
        )
            .rasterize()
            .apply { drawGridMask(this, Color.red) }
        val smallPolygon = Polygon(
            Point(5.99995030776296, 7.82850703463737),
            Point(5.9999356881499, 10.08837662380284),
            Point(7.5504466525217, 9.37897770925053)
        )
        val rasterizedSmallPolygon = smallPolygon
            .rasterize()

        drawGridRectangle(smallPolygon.gridHull, Color.pink)
//        draw(rasterizedSmallPolygon, Color.green)
        drawPolygon(smallPolygon, Color.blue)
        val maximalRectangle = rasterizedSmallPolygon
            .maximalRectangle()!!
        drawGridRectangle(maximalRectangle, Color.green)
    }
}
