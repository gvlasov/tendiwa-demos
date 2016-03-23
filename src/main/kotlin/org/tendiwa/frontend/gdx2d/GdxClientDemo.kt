package org.tendiwa.frontend.gdx2d

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import org.tendiwa.backend.existence.StimulusMedium
import org.tendiwa.backend.modules.roguelike.aspects.Health
import org.tendiwa.backend.modules.roguelike.aspects.PlayerVision
import org.tendiwa.backend.modules.roguelike.aspects.Weight
import org.tendiwa.backend.modules.roguelike.aspects.inventory
import org.tendiwa.backend.modules.roguelike.things.Human
import org.tendiwa.backend.modules.roguelike.things.WarAxe
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
import org.tendiwa.backend.time.TimeStream
import org.tendiwa.frontend.gdx2d.plugin.roguelike.RoguelikePlugin
import org.tendiwa.frontend.gdx2d.resources.images.ClasspathTextureBundle
import org.tendiwa.frontend.gdx2d.resources.images.TextureAtlasCache
import org.tendiwa.frontend.generic.PlayerVolition
import org.tendiwa.plane.grid.constructors.GridRectangle
import org.tendiwa.plane.grid.dimensions.by
import org.tendiwa.plane.grid.masks.GridMask
import org.tendiwa.plane.grid.masks.boundedBy
import java.nio.file.Paths

fun main(args: Array<String>) {
    val config =
        LwjglApplicationConfiguration().apply {
            title = "Tendiwa Demo Client"
            width = 800
            height = 480
            resizable = false
            foregroundFPS = 0
            backgroundFPS = 0
            vSyncEnabled = false
        }
    val worldSize = 320 by 320
    val medium = StimulusMedium()
    Reality(
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
                val voidWall = WallType.Companion.void
                val mask =
                    GridMask {
                        x, y ->
                        Math.sin(x.toDouble() + y) > 0.5 && Math.cos(x.toDouble () + y) > 0.5
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
        .let { reality -> // Setting up reality
            val playerVolition = PlayerVolition(reality)
            val playerCharacter =
                Human(
                    Position(Voxel(6, 7, 0)),
                    Name("bear"),
                    Weight(550),
                    Health(100)
                ).apply {
                    addAspect(playerVolition)
                    addAspect(PlayerVision())
                }
            reality.addRealThing(playerCharacter)
            reality.space.realThings.addRealThing(playerCharacter)
            val item = WarAxe()
            item.addAspect(Position(Voxel(9, 9, 0)))
            reality.addRealThing(item)
            reality.space.realThings.addRealThing(item)

            val inventoryAxe1 = WarAxe()
            reality.addRealThing(inventoryAxe1)
            playerCharacter.inventory.store(reality, inventoryAxe1)
            val inventoryAxe2 = WarAxe()
            reality.addRealThing(inventoryAxe2)
            playerCharacter.inventory.store(reality, inventoryAxe2)
            // Setting up the frontend
            LwjglApplication(
                TendiwaGame(
                    {
                        TextureAtlasCache(
                            Paths.get("target/textureCache"),
                            ClasspathTextureBundle(
                                listOf(
                                    "org/tendiwa/frontend/gdx/plugins/roguelike"
                                )
                            )
                        )
                            .obtainAtlas()
                    },
                    reality,
                    playerVolition,
                    medium,
                    listOf(RoguelikePlugin())
                ),
                config
            )
            val timeStream = TimeStream(reality, emptyList())
            playerVolition.addActorTo(timeStream)
            // Run the simulation
            Thread({
                while (true) {
                    timeStream.play()
                }
            }).start()
        }
}

