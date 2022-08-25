package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.util.SocketUtils;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import io.findify.s3mock.S3Mock;

@Configuration
class AwsS3TestConfig {

    private final int port = SocketUtils.findAvailableTcpPort();

    @Bean
    S3Mock s3MockServer() {
        return new S3Mock.Builder().withPort(port).withInMemoryBackend().build();
    }

    @Primary
    @Bean
    AmazonS3 amazonS3Test() {
        AwsClientBuilder.EndpointConfiguration endpoint =
                new AwsClientBuilder.EndpointConfiguration("http://127.0.0.1:" + port,
                                                           Regions.AP_NORTHEAST_2.name());
        return AmazonS3ClientBuilder.standard()
                                    .withPathStyleAccessEnabled(true)
                                    .withEndpointConfiguration(endpoint)
                                    .withCredentials(new AWSStaticCredentialsProvider(new AnonymousAWSCredentials()))
                                    .build();
    }
}
