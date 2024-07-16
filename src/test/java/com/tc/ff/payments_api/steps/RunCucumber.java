package com.tc.ff.payments_api.steps;

import static io.cucumber.core.options.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.core.options.Constants.PLUGIN_PROPERTY_NAME;

import org.junit.platform.suite.api.*;

@Suite
@IncludeEngines("cucumber")
@SelectPackages("com.tc.ff.payments_api")
@SelectClasspathResource("com.tc.ff.payments_api.new-payment.feature")
@SelectClasspathResource("com.tc.ff.payments_api")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.tc.ff.payments_api.steps")
public class RunCucumber {}
