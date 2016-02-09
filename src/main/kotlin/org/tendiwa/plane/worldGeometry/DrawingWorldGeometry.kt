package org.tendiwa.plane.worldGeometry

import org.tendiwa.canvas.algorithms.geometry.drawPolygon
import org.tendiwa.canvas.algorithms.geometry.graphs.drawGraph2D
import org.tendiwa.canvas.algorithms.grid.drawGridMask
import org.tendiwa.canvas.api.Canvas
import java.awt.Color

fun Canvas.draw(geometry: WorldGeometry) {
    geometry.apply {
        drawGridMask(terrain, Color.green)
        drawGridMask(water, Color.blue)
        //        draw(cityCentersMask, Color.red)
        citiesRoads.forEach {
            drawGraph2D(it, Color.black)
        }
        citiesLots.forEach {
            drawPolygon(it, Color.red)
        }
        houses.forEach {
            drawGridMask(it, Color.black)
        }
    }
}
