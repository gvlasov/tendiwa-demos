package org.tendiwa.plane.worldGeometry

import org.tendiwa.canvas.algorithms.geometry.draw
import org.tendiwa.canvas.algorithms.geometry.graphs.draw
import org.tendiwa.canvas.algorithms.grid.draw
import org.tendiwa.canvas.awt.AwtCanvas
import org.tendiwa.derasterization.polygons.derasterized
import org.tendiwa.math.noise.PerlinNoise
import org.tendiwa.math.ranges.SizeRange
import org.tendiwa.plane.geometry.algorithms.polygons.shrinking.shrink
import org.tendiwa.plane.geometry.graphs.Graph2D
import org.tendiwa.plane.geometry.graphs.cycles.minimumCycleBasis.minimumCycleBasis
import org.tendiwa.plane.geometry.graphs.cycles.toPolygon
import org.tendiwa.plane.geometry.graphs.fractured.fracture
import org.tendiwa.plane.geometry.orthoFracturedPolygon.orthoFractured
import org.tendiwa.plane.grid.algorithms.buffers.inwardBuffer
import org.tendiwa.plane.grid.algorithms.buffers.kingBufferBorder
import org.tendiwa.plane.grid.algorithms.distantSeeds.distantSeeds
import org.tendiwa.plane.grid.algorithms.floods.Flood
import org.tendiwa.plane.grid.algorithms.rectangles.maximalRectangle
import org.tendiwa.plane.grid.buffers.NoiseGridMask
import org.tendiwa.plane.grid.constructors.CenteredGridRectangle
import org.tendiwa.plane.grid.constructors.GridRectangle
import org.tendiwa.plane.grid.dimensions.by
import org.tendiwa.plane.grid.masks.boundedBy
import org.tendiwa.plane.grid.masks.difference
import org.tendiwa.plane.grid.masks.inverse
import org.tendiwa.plane.grid.rectangles.GridRectangle
import org.tendiwa.plane.grid.tiles.Tile
import org.tendiwa.plane.rasterization.polygon.rasterized
import java.awt.Color

fun main(args: Array<String>) {
    val viewport = GridRectangle(Tile(20, 20), 400 by 400)
    val terrain = NoiseGridMask(
        noise = PerlinNoise(7),
        scale = 128,
        hull = viewport,
        limit = 128
    )
    val water = terrain.inverse.boundedBy(viewport)
    val cityCentersMask = water
        .kingBufferBorder(depth = 20, thickness = 1)
        .boundedBy(viewport)
    val citiesRoads: List<Graph2D> =
        cityCentersMask
            .distantSeeds(200)
            .flatMap {
                Flood(
                    start = it,
                    tableHull = CenteredGridRectangle(it, 50),
                    passable = terrain.difference(terrain.inwardBuffer(5))
                )
                    .reachableMask
                    .derasterized
            }
            .map { it.fracture() }
    val citiesLots =
        citiesRoads
            .flatMap { it.minimumCycleBasis.minimalCycles }
            .flatMap { it.toPolygon().shrink(2.0) }
            .flatMap { it.orthoFractured(SizeRange(2.0..5.0)) }
    val houses: List<GridRectangle> =
        citiesLots
            .map { it.rasterized.maximalRectangle() }
            .filterNotNull()

    AwtCanvas(viewport, scale = 3).apply {
        draw(terrain, Color.green)
        draw(water, Color.blue)
        //        draw(cityCentersMask, Color.red)
        citiesRoads.forEach {
            draw(it, Color.black)
        }
        citiesLots.forEach {
            draw(it, Color.red)
        }
        houses.forEach {
            draw(it, Color.black)
        }
    }
}
