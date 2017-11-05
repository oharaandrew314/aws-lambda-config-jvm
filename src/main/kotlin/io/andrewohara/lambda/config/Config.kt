package io.andrewohara.lambda.config

interface Config {

    fun getStr(propName: String, defaultValue: String? = null, decrypt: Boolean=false): String?

    @Throws(NumberFormatException::class)
    fun getInt(propName: String, defaultValue: String? = null, decrypt: Boolean=false) = getStr(propName, defaultValue, decrypt)?.toInt()
}