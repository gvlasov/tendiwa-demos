package org.tendiwa.client.gdx

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import org.tendiwa.backend.modules.roguelike.aspects.Health
import org.tendiwa.backend.modules.roguelike.aspects.Name
import org.tendiwa.backend.space.aspects.Position
import org.tendiwa.backend.modules.roguelike.aspects.Weight
import org.tendiwa.backend.modules.roguelike.player.PlayerVolition
import org.tendiwa.backend.modules.roguelike.things.Human
import org.tendiwa.backend.space.Reality
import org.tendiwa.backend.space.Space
import org.tendiwa.backend.space.Voxel
import org.tendiwa.backend.space.floors.FloorPlane
import org.tendiwa.backend.space.floors.FloorType
import org.tendiwa.backend.space.floors.floors
import org.tendiwa.backend.space.realThing.RealThingPlane
import org.tendiwa.backend.space.walls.WallPlane
import org.tendiwa.backend.space.walls.WallType
import org.tendiwa.backend.space.walls.walls
import org.tendiwa.plane.grid.constructors.GridRectangle
import org.tendiwa.plane.grid.dimensions.by
import org.tendiwa.plane.grid.masks.GridMask
import org.tendiwa.plane.grid.masks.boundedBy

fun main(args: Array<String>) {
    val config =
        LwjglApplicationConfiguration().apply {
            title = "Tendiwa Demo Client"
            width = 800
            height = 480
            resizable = false
        }
    val worldSize = 320 by 320
    val playerVolition = PlayerVolition()
    LwjglApplication(
        TendiwaGame(
            "atlas/example.atlas",
            Reality(
                space = Space(
                    GridRectangle(worldSize),
                    listOf(
                        FloorPlane(worldSize),
                        WallPlane(worldSize),
                        RealThingPlane(worldSize)
                    )
                )
                    .apply { // Setting up space
                        val grassFloor = FloorType("grass", false)
                        val stoneFloor = FloorType("stone", false)
                        val stoneWall = WallType("wall_gray_stone")
                        val voidWall = WallType.void
                        val mask =
                            GridMask {
                                x, y ->
                                Math.sin(x.toDouble() + y) > 0.5
                            }
                        mask
                            .boundedBy(GridRectangle(worldSize))
                            .hull
                            .forEachTile { x, y ->
                                val floorType =
                                    if (mask.contains(x, y)) {
                                        grassFloor
                                    } else {
                                        stoneFloor
                                    }
                                val wallType =
                                    if (mask.contains(x, y)) {
                                        stoneWall
                                    } else {
                                        voidWall
                                    }
                                floors
                                    .chunkWithTile(x, y)
                                    .setFloor(x, y, floorType)
                                walls
                                    .chunkWithTile(x, y)
                                    .setWall(x, y, wallType)
                            }
                    }
            )
                .apply { // Setting up reality
                    addRealThing(
                        Human(
                            Position(Voxel(20, 30, 0)),
                            Name("Suseika"),
                            Weight(550),
                            Health(100)
                        ).apply { addAspect(playerVolition) }
                    )
                }
        ),
        config
    )
}

