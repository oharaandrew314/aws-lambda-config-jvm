package io.andrewohara.lambda.config

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest
import com.amazonaws.services.simplesystemsmanagement.model.ParameterNotFoundException

class SsmConfig(
        private val client: AWSSimpleSystemsManagement = AWSSimpleSystemsManagementClientBuilder.defaultClient()
): Config {

    override fun getStr(propName: String, defaultValue: String?, decrypt: Boolean): String? {
        try {
            val result = client.getParameter(GetParameterRequest().withName(propName).withWithDecryption(decrypt))
            return result.parameter.value ?: defaultValue
        } catch (ex: ParameterNotFoundException) {
            return defaultValue
        }
    }
}