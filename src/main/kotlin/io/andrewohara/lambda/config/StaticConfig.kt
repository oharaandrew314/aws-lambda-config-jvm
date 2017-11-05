package io.andrewohara.lambda.config

class StaticConfig(
        private val props: Map<String, String>
): Config {

    override fun getStr(propName: String, defaultValue: String?, decrypt: Boolean): String? {
        return props[propName] ?: defaultValue
    }
}