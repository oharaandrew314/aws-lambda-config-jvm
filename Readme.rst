serverless-config
=================

.. image:: https://travis-ci.org/oharaandrew314/aws-lambda-config-jvm.svg?branch=master
    :target: https://travis-ci.org/oharaandrew314/aws-lambda-config-jvm

A simple configuration client for AWS serverless JVM systems, written in Kotlin.

Supports the **System Environment**, and **AWS SSM Parameter Store**.  It is extendable to support your own custom configs, too.

Installation
------------

Add **jcenter** to your repositories

.. code-block:: groovy

    compile 'io.andrewohara:aws-lambda-config-jvm:0.1.0'


Quickstart
----------

.. code-block:: kotlin

    /* Example.kt */
    import io.andrewohara.lambda.config.Configs
    val config = Configs.defaultConfig()

    val stringProp: String? = config.getStr("string_prop")
    val intProp: Int? = config.getInt("missing_int_prop", defaultValue=123)
    val secretProp: String? = config.getStr("secret_prop", decrypt=True)
    
.. code-block:: java

    /* Example.java */
    import io.andrewohara.lambda.config.Configs
    final Config config = Configs.defaultConfig();
    
    final String stringProp = config.getStr("string_prop");
    final int intProp = config.getInt("missing_int_prop", defaultValue=123);
    final String secretProp = config.getStr("secret_prop", decrypt=True);
    
    

The default config will first search for the parameter in the *environment*.  If it is not there, then it will search *SSM*.

Encrypted secrets are supported with via *SSM*.  If you want to decrypt the parameter in transit, then be sure to add **decrypt=true**.

If a parameter is not found, it will return null.
