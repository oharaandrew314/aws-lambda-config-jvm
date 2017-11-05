package io.andrewohara.lambda.config

class MultiConfig(
        vararg private val configs: Config
): Config {

    override fun getStr(propName: String, defaultValue: String?, decrypt: Boolean): String? {
        return configs
                .mapNotNull { it.getStr(propName) }
                .firstOrNull() ?: defaultValue

    }
}