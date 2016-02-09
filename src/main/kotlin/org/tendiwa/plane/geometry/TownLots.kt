package org.tendiwa.plane.geometry

import org.tendiwa.canvas.algorithms.geometry.drawPolygon
import org.tendiwa.canvas.awt.AwtCanvas
import org.tendiwa.math.ranges.SizeRange
import org.tendiwa.plane.geometry.algorithms.polygons.shrinking.shrink
import org.tendiwa.plane.geometry.crackedHoleygon.random.RandomCrackedHoleygon
import org.tendiwa.plane.geometry.graphs.cycles.minimumCycleBasis.minimumCycleBasis
import org.tendiwa.plane.geometry.graphs.cycles.toPolygon
import org.tendiwa.plane.geometry.graphs.toSegmentGroup
import org.tendiwa.plane.geometry.holeygons.Holeygon
import org.tendiwa.plane.geometry.points.Point
import org.tendiwa.plane.geometry.polygons.Polygon
import org.tendiwa.plane.geometry.streets.StreetColoring
import org.tendiwa.plane.geometry.streets.drawSegmentGroupColoring
import org.tendiwa.plane.grid.dimensions.by
import org.tendiwa.plane.settlements.quarters.ortho.random.orthoFractured
import org.tendiwa.plane.settlements.streets.streets
import java.awt.Color

fun main(args: Array<String>) {
    val polygon = Polygon(
        Point(0.0, 0.0),
        {
            move(50.0, 8.0)
            move(20.0, -4.0)
            move(10.0, 36.0)
            move(-15.0, 35.0)
            move(3.0, 15.0)
            move(-30.0, 8.0)
            move(-10.0, -20.0)
            move(10.0, -20.0)
            move(-30.0, 2.0)
            move(1.0, 10.0)
            move(-8.0, 1.0)
            move(0.0, -22.0)
            move(13.0, 1.0)
        }
    )
    val hole1 = Polygon(
        Point(40.0, 40.0),
        {
            move(19.0, 0.0)
            move(0.0, 10.0)
            move(8.0, 13.0)
            move(-17.0, -3.0)
        }
    )
    val holeygon = Holeygon(polygon, listOf(hole1))
    val graph = RandomCrackedHoleygon(holeygon).contours
    val cells = graph
        .minimumCycleBasis
        .minimalCycles
        .map { it.toPolygon() }
        .flatMap { it.shrink(2.0) }
    val lots = cells
        .flatMap { it.orthoFractured(SizeRange(5.0..10.0)) }
    val streetColoring = graph
        .toSegmentGroup()
        .streets()
        .let { StreetColoring(it) }
    AwtCanvas(size = 100 by 100, scale = 4)
        .apply {
            drawSegmentGroupColoring(streetColoring)
            lots.forEach {
                drawPolygon(it, Color.black)
            }
        }
}
