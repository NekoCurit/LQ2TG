package net.nekocurit.lq2tg

import kotlinx.coroutines.runBlocking

object LQ2TGLoader {

    @JvmStatic
    fun main(vararg args: String) {
        runBlocking {
            LQ2TG().start()
        }
    }

}