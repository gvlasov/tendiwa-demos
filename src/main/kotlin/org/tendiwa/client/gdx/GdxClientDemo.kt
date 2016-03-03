package org.tendiwa.client.gdx

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration

fun main(args: Array<String>) {
    val config =
        LwjglApplicationConfiguration().apply {
            title = "Tendiwa Demo Client"
            width = 800
            height = 480
            resizable = false
        }
    LwjglApplication(
        TendiwaGame(
            "atlas/example.atlas"
        ),
        config
    )
}

