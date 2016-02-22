package org.tendiwa.plane.terrain.networks

import org.tendiwa.canvas.algorithms.grid.drawGridMask
import org.tendiwa.canvas.algorithms.grid.drawTile
import org.tendiwa.canvas.awt.AwtCanvas
import org.tendiwa.plane.grid.constructors.GridRectangle
import org.tendiwa.plane.grid.dimensions.by
import org.tendiwa.plane.grid.masks.boundedBy
import org.tendiwa.plane.grid.masks.move
import java.awt.Color

fun main(args: Array<String>) {
    val worldBounds = GridRectangle(400 by 300)
    val places =
        (0..24)
            .map {
                GridRectangle(20 by 20)
                    .move(30 * (it / 5), 30 * (it % 5))
                    .move(20, 20)
            }
            .mapIndexed { i, mask ->
                BorderedGridPlace(
                    mask,
                    { index, tile -> index % (15 + i) == 0 }
                )
            }
    val network = InterplaceNetwork(places, worldBounds)
    AwtCanvas(viewport = worldBounds).apply {
        network
            .places()
            .map { it.mask.boundedBy(worldBounds) }
            .forEach { mask -> drawGridMask(mask, Color.gray) }
        network
            .paths()
            .forEach { path -> drawGridMask(path, Color.red) }
        network
            .places()
            .flatMap { it.exits }
            .forEach { drawTile(it, Color.green) }
    }
}
