package org.tendiwa.plane.geometry.streets

import org.jgrapht.UndirectedGraph
import org.jgrapht.alg.ChromaticNumber
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph
import org.tendiwa.canvas.algorithms.geometry.drawSegmentGroup
import org.tendiwa.canvas.api.Canvas
import org.tendiwa.math.sets.allPossiblePairs
import org.tendiwa.plane.geometry.paths.SegmentPath
import org.tendiwa.plane.geometry.points.Point
import org.tendiwa.plane.geometry.shapes.SegmentGroup
import java.awt.Color
import java.util.*

fun StreetColoring(streets: List<SegmentPath>): Map<Color, Set<SegmentPath>> =
    StreetIntersectionGraph(streets)
        .let { ChromaticNumber.findGreedyColoredGroups(it) }
        .entries
        .map { Pair(colors[it.key], it.value) }
        .toMap()

fun Canvas.drawSegmentGroupColoring(coloring: Map<Color, Set<SegmentGroup>>) {
    for ((color, groups) in coloring) {
        for (group in groups) {
            this.drawSegmentGroup(group, color)
        }
    }
}

private val colors = listOf(
    Color.red,
    Color.green,
    Color.blue,
    Color.black,
    Color.orange,
    Color.cyan,
    Color.yellow,
    Color.magenta,
    Color(210, 105, 30), // brown
    Color(148, 0, 211) // violet
)

private fun StreetIntersectionGraph(
    streets: List<SegmentPath>
): UndirectedGraph<SegmentPath, DefaultEdge> {
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

