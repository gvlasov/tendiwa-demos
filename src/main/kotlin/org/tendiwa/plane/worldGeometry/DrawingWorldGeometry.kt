package org.tendiwa.plane.worldGeometry

import org.tendiwa.canvas.algorithms.geometry.draw
import org.tendiwa.canvas.algorithms.geometry.graphs.draw
import org.tendiwa.canvas.algorithms.grid.drawGridMask
import org.tendiwa.canvas.api.Canvas
import java.awt.Color

fun Canvas.draw(geometry: WorldGeometry) {
    geometry.apply {
        drawGridMask(terrain, Color.green)
        drawGridMask(water, Color.blue)
        //        draw(cityCentersMask, Color.red)
        citiesRoads.forEach {
            draw(it, Color.black)
        }
        citiesLots.forEach {
            draw(it, Color.red)
        }
        houses.forEach {
            drawGridMask(it, Color.black)
        }
    }
}
