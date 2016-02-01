package org.tendiwa.plane.worldGeometry

import org.tendiwa.derasterization.polygons.derasterized
import org.tendiwa.math.noise.PerlinNoise
import org.tendiwa.math.ranges.SizeRange
import org.tendiwa.plane.geometry.algorithms.polygons.shrinking.shrink
import org.tendiwa.plane.geometry.graphs.Graph2D
import org.tendiwa.plane.geometry.graphs.cycles.minimumCycleBasis.minimumCycleBasis
import org.tendiwa.plane.geometry.graphs.cycles.toPolygon
import org.tendiwa.plane.geometry.graphs.fractured.fracture
import org.tendiwa.plane.geometry.orthoFracturedPolygon.orthoFractured
import org.tendiwa.plane.grid.algorithms.buffers.deflate
import org.tendiwa.plane.grid.algorithms.buffers.intrudedContour
import org.tendiwa.plane.grid.algorithms.distantSeeds.distantSeeds
import org.tendiwa.plane.grid.algorithms.floods.Flood
import org.tendiwa.plane.grid.algorithms.rectangles.maximalRectangle
import org.tendiwa.plane.grid.buffers.NoiseGridMask
import org.tendiwa.plane.grid.constructors.CenteredGridRectangle
import org.tendiwa.plane.grid.masks.BoundedGridMask
import org.tendiwa.plane.grid.masks.boundedBy
import org.tendiwa.plane.grid.masks.inverse
import org.tendiwa.plane.grid.rectangles.GridRectangle
import org.tendiwa.plane.rasterization.polygon.rasterize

data class WorldGeometry(val viewport: GridRectangle) {
    val terrain =
        NoiseGridMask(
            noise = PerlinNoise(7),
            scale = 128,
            hull = viewport,
            limit = 128
        )

    val water = terrain.inverse.boundedBy(viewport)

    val cityCentersMask =
        terrain
            .intrudedContour(depth = 20, thickness = 1)
            .boundedBy(viewport)

    val citiesRoads: List<Graph2D> =
        cityCentersMask
            .distantSeeds(200)
            .flatMap {
                Flood(
                    start = it,
                    tableHull = CenteredGridRectangle(it, 50),
                    passable = terrain.deflate(5)
                )
                    .reachableMask
                    .derasterized
            }
            .map {
                it.fracture(
                    crackSegmentLengths = SizeRange(20.0..34.0),
                    favourAxisAlignedSegments = true
                )
            }

    val citiesLots =
        citiesRoads
            .flatMap { it.minimumCycleBasis.minimalCycles }
            .flatMap { it.toPolygon().shrink(2.0) }
            .flatMap { it.orthoFractured(SizeRange(8.0..10.0)) }

    val houses: List<BoundedGridMask> =
        citiesLots
            .map { it.rasterize().maximalRectangle() }
            .filterNotNull()
}
