package io.andrewohara.lambda.config

import org.junit.Test

class TestConfigs {

    @Test
    fun customConfig() {
        Configs.customConfig()
    }

    @Test
    fun defaultConfig() {
        Configs.defaultConfig()
    }

    @Test
    fun ssmCOnfig() {
        Configs.ssmConfig()
    }

    @Test
    fun envConfig() {
        Configs.envConfig()
    }
}