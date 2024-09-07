package com.tc.ff.payments_api.common.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SqsConfig {
    @Value("${aws.queues.Endpoint}")
    private String endpoint;

    @Value("${aws.queues.Region}")
    private String region;

    @Bean
    public AmazonSQS amazonSQSClient() {
        EnvironmentVariableCredentialsProvider awsEnvCredentialsProvider = new EnvironmentVariableCredentialsProvider();
        var builder = AmazonSQSClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsEnvCredentialsProvider.getCredentials()));
        //                .withRegion(Regions.SA_EAST_1);

        if (this.endpoint != null && !this.endpoint.isEmpty()) {
            builder.withEndpointConfiguration(
                    new AmazonSQSClientBuilder.EndpointConfiguration(this.endpoint, this.region));
        }
        return builder.build();
    }
}
