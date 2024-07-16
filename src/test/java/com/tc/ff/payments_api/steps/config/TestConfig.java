package com.tc.ff.payments_api.steps.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.tc.ff.payments_api")
@EnableAutoConfiguration
public class TestConfig {}
