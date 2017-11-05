package io.andrewohara.lambda.config

import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.contrib.java.lang.system.EnvironmentVariables

class TestEnvConfig {

    private val testObj = Configs.envConfig()

    @Rule fun env() = EnvironmentVariables()

    @Test
    fun missingStrProp() {
        Assert.assertThat(
                testObj.getStr(Props.STRING_PROP),
                CoreMatchers.nullValue()
        )
    }

    @Test
    fun missingStrPropWithDefault() {
        Assert.assertThat(
                testObj.getStr(Props.STRING_PROP, "default"),
                CoreMatchers.equalTo("default")
        )
    }

    @Test
    fun presentStrProp() {
        env().set(Props.STRING_PROP, "present")
        Assert.assertThat(
                testObj.getStr(Props.STRING_PROP),
                CoreMatchers.equalTo("present")
        )
    }

    @Test
    fun presentStrPropWithDefault() {
        env().set(Props.STRING_PROP, "present")
        Assert.assertThat(
                testObj.getStr(Props.STRING_PROP, "default"),
                CoreMatchers.equalTo("present")
        )
    }

    @Test
    fun missingIntProp() {
        Assert.assertThat(
                testObj.getStr(Props.INT_PROP),
                CoreMatchers.nullValue()
        )
    }

    @Test
    fun presentIntProp() {
        env().set(Props.INT_PROP, "1337")
        Assert.assertThat(
                testObj.getInt(Props.INT_PROP),
                CoreMatchers.equalTo(1337)
        )
    }

    @Test(expected=NumberFormatException::class)
    fun invalidIntProp() {
        env().set(Props.STRING_PROP, "present")
        testObj.getInt(Props.STRING_PROP)
    }
}