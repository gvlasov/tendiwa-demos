package org.tendiwa.frontend.gdx2d

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import org.tendiwa.backend.GridParallelepiped
import org.tendiwa.backend.by
import org.tendiwa.backend.existence.StimulusMedium
import org.tendiwa.backend.existence.aspect
import org.tendiwa.backend.modules.roguelike.aspects.*
import org.tendiwa.backend.modules.roguelike.things.Human
import org.tendiwa.backend.modules.roguelike.things.WarAxe
import org.tendiwa.backend.space.Reality
import org.tendiwa.backend.space.Space
import org.tendiwa.backend.space.Voxel
import org.tendiwa.backend.space.aspects.Name
import org.tendiwa.backend.space.aspects.Position
import org.tendiwa.backend.space.chunks.chunkWithVoxel
import org.tendiwa.backend.space.floors.FloorType
import org.tendiwa.backend.space.walls.WallType
import org.tendiwa.frontend.gdx2d.plugin.roguelike.RoguelikePlugin
import org.tendiwa.frontend.gdx2d.resources.images.ClasspathTextureBundle
import org.tendiwa.frontend.gdx2d.resources.images.TextureAtlasCache
import org.tendiwa.frontend.generic.PlayerVolition
import org.tendiwa.frontend.generic.TimeBubbleOwnership
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
            GridParallelepiped(Voxel(0, 0, 0), worldSize by 3)
        )
            .apply { // Setting up space
                val grassFloor = FloorType("grass", false)
                val stoneFloor = FloorType("stone", false)
                val stoneWall = WallType("wall_gray_stone")
                val voidWall = WallType.void
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
                        (0 until hull.dimension.depth).forEach {
                            depth ->
                            floors
                                .chunkWithVoxel(x, y, depth)
                                .setFloor(x, y, floorType)
                        }
                        walls
                            .chunkWithVoxel(x, y, 0)
                            .setWall(x, y, wallType)
                    }
            }
    )
        .let { reality -> // Setting up reality
            // Set up player character
            val playerVolition = PlayerVolition(reality)
            val playerCharacter =
                Human(
                    Position(Voxel(6, 7, 0)),
                    Name("carrotGolem"),
                    Weight(550),
                    Health(100)
                ).apply {
                    addAspect(playerVolition)
                    addAspect(PlayerVision())
                }
            playerCharacter.removeAspect(HumanoidIntelligence::class.java)
            reality.addRealThing(playerCharacter)
            val item = WarAxe()
            item.addAspect(Position(Voxel(9, 9, 0)))
            reality.addRealThing(item)

            // Set up the other nearby character
            val wanderbear = Human(
                Position(Voxel(10, 10, 0)),
                Name("bear"),
                Weight(550),
                Health(100)
            ).apply {
                addAspect(WandererAI())
                removeAspect(HumanoidIntelligence::class.java)
            }
            reality.addRealThing(wanderbear)

            val inventoryAxe1 = WarAxe()
            reality.addRealThing(inventoryAxe1)
            playerCharacter.aspect<Inventory>().store(reality, inventoryAxe1)
            val inventoryAxe2 = WarAxe()
            reality.addRealThing(inventoryAxe2)
            playerCharacter.aspect<Inventory>().store(reality, inventoryAxe2)
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
            // Run the simulation
            val bubbleOwnership = TimeBubbleOwnership()
            reality.addAspect(playerCharacter, bubbleOwnership)
            Thread(
                bubbleOwnership.runnable(),
                "TimeStream scheduling"
            ).start()
        }
}

