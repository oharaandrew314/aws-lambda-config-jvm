package io.andrewohara.lambda.config

import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementAsyncClientBuilder
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class TestSsmConfig {

    //TODO test decryption.  Need to figure out how to mock that easily

    private val testObj = SsmConfig(AWSSimpleSystemsManagementAsyncClientBuilder
            .standard()
            .withEndpointConfiguration(AwsClientBuilder
                    .EndpointConfiguration("http://localhost:8080", "us-east-1")
            ).build())

    private val wireMock = WireMockRule()

    @Rule fun wireMock() = wireMock

    private fun setMockParam(propName: String, propValue: String? = null, encrypt: Boolean = false) {
        val value = when(propValue) {
            null -> "null"
            else -> if (encrypt) "\"encrypted:$propValue\"" else "\"$propValue\""
        }

        val body = """
                  {
                    "Parameter": {
                      "Name": "$propName",
                      "Type": "${if (encrypt) "SecureString" else "String"}",
                      "Value": $value,
                      "Version": 2
                    }
                  }
                  """

        wireMock.stubFor(WireMock.post(WireMock.urlEqualTo("/"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(body)
                )
        )
    }

    @Test
    fun missingStrProp() {
        setMockParam(Props.STRING_PROP)

        Assert.assertThat(
                testObj.getStr(Props.STRING_PROP, decrypt=true),
                CoreMatchers.nullValue()
        )
    }

    @Test
    fun missingStrPropWithDefault() {
        setMockParam(Props.STRING_PROP)
        Assert.assertThat(
                testObj.getStr(Props.STRING_PROP, "default"),
                CoreMatchers.equalTo("default")
        )
    }

    @Test
    fun presentStrProp() {
        setMockParam(Props.STRING_PROP, "present")
        Assert.assertThat(
                testObj.getStr(Props.STRING_PROP),
                CoreMatchers.equalTo("present")
        )
    }

    @Test
    fun presentStrPropWithDefault() {
        setMockParam(Props.STRING_PROP, "present")
        Assert.assertThat(
                testObj.getStr(Props.STRING_PROP, "default"),
                CoreMatchers.equalTo("present")
        )
    }

    @Test
    fun missingIntProp() {
        setMockParam(Props.INT_PROP)
        Assert.assertThat(
                testObj.getStr(Props.INT_PROP),
                CoreMatchers.nullValue()
        )
    }

    @Test
    fun presentIntProp() {
        setMockParam(Props.INT_PROP, "1337")
        Assert.assertThat(
                testObj.getInt(Props.INT_PROP),
                CoreMatchers.equalTo(1337)
        )
    }

    @Test(expected=NumberFormatException::class)
    fun invalidIntProp() {
        setMockParam(Props.STRING_PROP, "present")
        testObj.getInt(Props.STRING_PROP)
    }

    @Test
    fun getStrEncrypted() {
        setMockParam(Props.ENCRYPTED_PROP, "secret", encrypt=true)
        Assert.assertThat(
                testObj.getStr(Props.ENCRYPTED_PROP),
                CoreMatchers.not(CoreMatchers.equalTo("secret"))
        )
    }
}