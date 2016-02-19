package org.tendiwa.plane.settlements.roadNetworks

import org.tendiwa.canvas.algorithms.geometry.graphs.drawGraph2D
import org.tendiwa.canvas.awt.AwtCanvas
import org.tendiwa.math.sliders.CircularSlider
import org.tendiwa.plane.geometry.crackedHoleygon.random.RandomCrackedHoleygon
import org.tendiwa.plane.geometry.holeygons.Holeygon
import org.tendiwa.plane.geometry.polygons.masked.PerimeterPiece
import org.tendiwa.plane.geometry.rectangles.Rectangle
import org.tendiwa.plane.settlements.roadNetworkShape.GapingOutlineRoadNetworkShape
import org.tendiwa.plane.settlements.roadNetworkShape.RoadWidth
import java.awt.Color

fun main(args: Array<String>) {
    val shape = GapingOutlineRoadNetworkShape(
        RandomCrackedHoleygon(
            Holeygon(Rectangle(10.0, 10.0, 380.0, 280.0))
        ),
        {
            listOf(
                PerimeterPiece(
                    CircularSlider(0.3),
                    90.0
                ),
                PerimeterPiece(
                    CircularSlider(0.8),
                    90.0
                ),
                PerimeterPiece(
                    CircularSlider(0.0),
                    90.0
                )
            )
        },
        RoadWidth(3.0)
    )
    AwtCanvas().apply {
        drawGraph2D(shape.roads, Color.black)
//        shape.gaps.forEach {
//            drawSegment(it, Color.green)
//        }
    }
}

