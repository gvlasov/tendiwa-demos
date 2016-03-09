package org.tendiwa.client.gdx

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import org.tendiwa.backend.modules.roguelike.aspects.Health
import org.tendiwa.backend.modules.roguelike.aspects.Weight
import org.tendiwa.backend.modules.roguelike.things.Human
import org.tendiwa.backend.space.Reality
import org.tendiwa.backend.space.Space
import org.tendiwa.backend.space.Voxel
import org.tendiwa.backend.space.aspects.Name
import org.tendiwa.backend.space.aspects.Position
import org.tendiwa.backend.space.floors.FloorPlane
import org.tendiwa.backend.space.floors.FloorType
import org.tendiwa.backend.space.floors.floors
import org.tendiwa.backend.space.realThing.RealThingPlane
import org.tendiwa.backend.space.realThing.realThings
import org.tendiwa.backend.space.walls.WallPlane
import org.tendiwa.backend.space.walls.WallType
import org.tendiwa.backend.space.walls.walls
import org.tendiwa.frontend.gdx.plugin.roguelike.RoguelikePlugin
import org.tendiwa.frontend.generic.PlayerVolition
import org.tendiwa.plane.grid.constructors.GridRectangle
import org.tendiwa.plane.grid.dimensions.by
import org.tendiwa.plane.grid.masks.GridMask
import org.tendiwa.plane.grid.masks.boundedBy
import org.tendiwa.stimuli.StimulusMedium
import org.tendiwa.time.TimeStream

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
    val medium = StimulusMedium()
    val reality = Reality(
        medium = medium,
        space = Space(
            GridRectangle(worldSize),
            listOf(
                FloorPlane(worldSize),
                WallPlane(worldSize),
                RealThingPlane(worldSize, medium)
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
            val playerCharacter =
                Human(
                    Position(Voxel(7, 7, 0)),
                    Name("bear"),
                    Weight(550),
                    Health(100)
                ).apply { addAspect(playerVolition) }
            addRealThing(playerCharacter)
            space.realThings.addRealThing(playerCharacter)
        }
    LwjglApplication(
        TendiwaGame(
            "atlas/example.atlas",
            reality,
            playerVolition,
            medium,
            listOf(RoguelikePlugin())
        ),
        config
    )
    val timeStream = TimeStream(reality, emptyList())
    playerVolition.addActorTo(timeStream)
    Thread({
        while (true) {
            timeStream.play()
        }
    }).start()
}

