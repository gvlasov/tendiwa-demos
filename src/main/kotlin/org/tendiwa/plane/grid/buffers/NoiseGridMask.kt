package org.tendiwa.plane.grid.buffers

import org.tendiwa.math.noise.PerlinNoise
import org.tendiwa.plane.grid.masks.ArrayGridMask
import org.tendiwa.plane.grid.masks.BoundedGridMask
import org.tendiwa.plane.grid.masks.GridMask
import org.tendiwa.plane.grid.masks.boundedBy
import org.tendiwa.plane.grid.rectangles.GridRectangle

data class NoiseGridMask(
    val noise: PerlinNoise,
    val scale: Int,
    override val hull: GridRectangle,
    val limit: Int
) : BoundedGridMask {
    val cachedMask: ArrayGridMask =
        GridMask(
            { x, y -> heightAt(x, y) > limit }
        )
            .boundedBy(hull)
            .let { ArrayGridMask(it) }

    private fun heightAt(x: Int, y: Int) =
        noise.at(x.toDouble() / scale, y.toDouble() / scale)

    override fun contains(x: Int, y: Int): Boolean =
        cachedMask.contains(x, y)

}
