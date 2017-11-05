package io.andrewohara.lambda.config

class EnvConfig: Config {

    override fun getStr(propName: String, defaultValue: String?, decrypt: Boolean): String? {
        return System.getenv(propName) ?: defaultValue
    }
}