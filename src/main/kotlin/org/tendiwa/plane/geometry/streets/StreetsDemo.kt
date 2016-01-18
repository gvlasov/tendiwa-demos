package org.tendiwa.plane.geometry.streets

import org.jgrapht.UndirectedGraph
import org.jgrapht.alg.ChromaticNumber
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph
import org.tendiwa.canvas.algorithms.geometry.draw
import org.tendiwa.canvas.awt.AwtCanvas
import org.tendiwa.math.sets.allPossiblePairs
import org.tendiwa.plane.directions.CardinalDirection.*
import org.tendiwa.plane.directions.OrdinalDirection.*
import org.tendiwa.plane.geometry.graphs.fractured.fracture
import org.tendiwa.plane.geometry.graphs.toSegmentGroup
import org.tendiwa.plane.geometry.holeygons.Holeygon
import org.tendiwa.plane.geometry.paths.SegmentPath
import org.tendiwa.plane.geometry.points.Point
import org.tendiwa.plane.geometry.trails.Polygon
import org.tendiwa.plane.grid.dimensions.by
import java.awt.Color
import java.util.*

fun main(args: Array<String>) {
    val polygon = Polygon(
        Point(10.0, 10.0),
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
    val colors = listOf(
        Color.red,
        Color.green,
        Color.blue,
        Color.black,
        Color.orange,
        Color.cyan,
        Color.yellow
    )

    AwtCanvas(size = 256 by 295, scale = 3)
        .apply {
            val streets = holeygon
                .fracture(
                    roadsFromPoint = 4,
                    favourAxisAlignedSegments = true
                )
                .toSegmentGroup()
                .streets()
            val streetColoringGroups = streetIntersectionGraph(streets)
                .let { ChromaticNumber.findGreedyColoredGroups(it) }
            for ((colorIndex, streetGroup) in streetColoringGroups) {
                streetGroup.forEach {
                    draw(it, colors[colorIndex])
                }
            }
        }
}

fun streetIntersectionGraph(streets: List<SegmentPath>): UndirectedGraph<SegmentPath, DefaultEdge> {
    val graph = SimpleGraph<SegmentPath, DefaultEdge>(
        { a, b -> DefaultEdge() }
    );
    streets.forEach { graph.addVertex(it) }
    val map: MutableMap<Point, MutableCollection<SegmentPath>> = HashMap()
    streets.forEach { street ->
        street.points.forEach { point ->
            map
                .getOrPut(point, { HashSet<SegmentPath>() })
                .add(street)
        }
    }
    for ((point, intersectedStreets) in map) {
        intersectedStreets
            .allPossiblePairs()
            .forEach {
                graph.addEdge(it.first, it.second)
            }
    }
    return graph
}


