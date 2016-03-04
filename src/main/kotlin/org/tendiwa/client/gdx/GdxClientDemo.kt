package org.tendiwa.client.gdx

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import org.tendiwa.client.gdx.temporaryImpls.CameraInputAdapter
import org.tendiwa.client.gdx.temporaryImpls.ExampleVicinity

fun main(args: Array<String>) {
    val config =
        LwjglApplicationConfiguration().apply {
            title = "Tendiwa Demo Client"
            width = 800
            height = 480
            resizable = false
        }
    val vicinity = ExampleVicinity()
    LwjglApplication(
        TendiwaGame(
            atlasPath = "atlas/example.atlas",
            createInputProcessor = { camera -> CameraInputAdapter(camera, vicinity) },
            vicinity = vicinity
        ),
        config
    )
}

