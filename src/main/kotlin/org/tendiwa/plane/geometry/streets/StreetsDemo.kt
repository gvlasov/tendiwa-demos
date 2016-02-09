package org.tendiwa.plane.geometry.streets

import org.tendiwa.canvas.algorithms.geometry.draw
import org.tendiwa.canvas.awt.AwtCanvas
import org.tendiwa.plane.directions.CardinalDirection.*
import org.tendiwa.plane.directions.OrdinalDirection.*
import org.tendiwa.plane.geometry.graphs.toSegmentGroup
import org.tendiwa.plane.geometry.holeygons.Holeygon
import org.tendiwa.plane.geometry.paths.SegmentPath
import org.tendiwa.plane.geometry.points.Point
import org.tendiwa.plane.geometry.polygons.Polygon
import org.tendiwa.plane.grid.dimensions.by
import org.tendiwa.plane.settlements.roadNetwork.random.RandomRoadNetworkGeometry
import org.tendiwa.plane.settlements.streets.streets

fun main(args: Array<String>) {
    AwtCanvas(size = 256 by 295, scale = 3)
        .apply {
            for ((color, streetGroup) in StreetColoring(streetNetwork())) {
                streetGroup.forEach {
                    draw(it, color)
                }
            }
        }
}

private fun streetNetwork(): List<SegmentPath> =
    RandomRoadNetworkGeometry(
        border = holeygon(),
        roadsFromPoint = 4,
        favourAxisAlignedSegments = true
    )
        .roads
        .toSegmentGroup()
        .streets()

private fun holeygon(): Holeygon {
    val polygon = Polygon(Point(10.0, 10.0),
        {
            move(100.0, E)
            move(60.0, NE)
            move(80.0, E)
            move(80.0, NW)
            move(40.0, N)
            move(40.0, NE)
            move(60.0, NW)
            move(50.0, W)
            move(50.0, SW)
        }
    )
    val hole1 = Polygon(
        Point(95.0, 220.0),
        {
            move(20.0, E)
            move(30.0, S)
            move(50.0, SW)
        }
    )
    val hole2 = Polygon(
        Point(108.0, 77.0),
        {
            move(40.0, N)
            move(35.0, SE)
        }
    )
    val holeygon = Holeygon(
        enclosing = polygon,
        holes = listOf(hole1, hole2)
    )
    return holeygon
}


