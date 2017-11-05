package io.andrewohara.lambda.config

import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.contrib.java.lang.system.EnvironmentVariables

class TestMultiConfig {
    private val testObj = Configs.customConfig(
            EnvConfig(),
            StaticConfig(mapOf(Props.STRING_PROP to "presentInStatic", Props.INT_PROP to "9001"))
            )

    private val env = EnvironmentVariables()
    @Rule fun env() = env

    @Test
    fun envStrProp() {
       env.set(Props.STRING_PROP, "presentInEnv")
       Assert.assertThat(
               testObj.getStr(Props.STRING_PROP),
               CoreMatchers.equalTo("presentInEnv")
       )
   }

    @Test
    fun staticStrPropWithDefault() {
        Assert.assertThat(
                testObj.getStr(Props.STRING_PROP, "default"),
                CoreMatchers.equalTo("presentInStatic")
        )
    }

    @Test
    fun defaultStrProp() {
        Assert.assertThat(
                testObj.getStr("missing", "default"),
                CoreMatchers.equalTo("default")
        )
    }

    @Test
    fun missing() {
        Assert.assertThat(
                testObj.getStr("missing"),
                CoreMatchers.nullValue()
        )
    }

    @Test
    fun staticIntProp() {
        Assert.assertThat(
                testObj.getInt(Props.INT_PROP),
                CoreMatchers.equalTo(9001)
        )
    }
}