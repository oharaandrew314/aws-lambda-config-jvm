aws-lambda-config-jvm
=====================

.. image:: https://travis-ci.org/oharaandrew314/aws-lambda-config-jvm.svg?branch=master
    :target: https://travis-ci.org/oharaandrew314/aws-lambda-config-jvm

.. image:: https://api.bintray.com/packages/oharaandrew314/maven-public/aws-lambda-config-jvm/images/download.svg
    :target: https://bintray.com/oharaandrew314/maven-public/aws-lambda-config-jvm/_latestVersion
    
.. image:: https://codecov.io/gh/oharaandrew314/aws-lambda-config-jvm/branch/master/graph/badge.svg
  :target: https://codecov.io/gh/oharaandrew314/aws-lambda-config-jvm

A simple configuration client for AWS serverless JVM systems, written in Kotlin.

Supports the **System Environment**, and **AWS SSM Parameter Store**.  It is extendable to support your own custom configs, too.

There is also a `python version <https://github.com/oharaandrew314/serverless-config>`_.

Installation
------------

Add the dependency to your *build.gradle*:

.. code-block:: groovy

    repositories {
      ...
      maven {
        url 'https://dl.bintray.com/oharaandrew314/maven-public'
      }
    }
    
    dependencies {
      ...
      compile 'io.andrewohara:aws-lambda-config-jvm:0.1.2'
    }


Quickstart
----------

.. code-block:: kotlin

    /* Example.kt */
    import io.andrewohara.lambda.config.Configs
    val config = Configs.defaultConfig()

    val stringProp: String? = config.getStr("string_prop")
    val intProp: Int? = config.getInt("missing_int_prop", defaultValue=123)
    val secretProp: String? = config.getStr("secret_prop", decrypt=true)
    
.. code-block:: java

    /* Example.java */
    import io.andrewohara.lambda.config.Configs
    final Config config = Configs.defaultConfig();
    
    final String stringProp = config.getStr("string_prop");
    final int intProp = config.getInt("missing_int_prop", defaultValue=123);
    final String secretProp = config.getStr("secret_prop", decrypt=true);
    
    

The default config will first search for the parameter in the *environment*.  If it is not there, then it will search *SSM*.

Encrypted secrets are supported via *SSM*.  If you want to decrypt the parameter in transit, then be sure to add **decrypt=true**.

If a parameter is not found, it will return null.
