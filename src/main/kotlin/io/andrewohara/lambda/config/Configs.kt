package io.andrewohara.lambda.config

object Configs {
    fun envConfig() = EnvConfig()
    fun ssmConfig() = SsmConfig()
    fun defaultConfig() = MultiConfig(EnvConfig(), SsmConfig())
    fun customConfig(vararg configs: Config) = MultiConfig(*configs)
}