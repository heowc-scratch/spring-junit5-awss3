package com.example;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.amazonaws.services.s3.AmazonS3;

import io.findify.s3mock.S3Mock;

public class AwsS3Extension implements BeforeEachCallback, AfterEachCallback {

    private S3Mock s3Mock;

    @Override
    public void beforeEach(ExtensionContext context) {
        ApplicationContext applicationContext = SpringExtension.getApplicationContext(context);

        s3Mock = applicationContext.getBean(S3Mock.class);
        AmazonS3 s3Client = applicationContext.getBean(AmazonS3.class);

        s3Mock.start();
        s3Client.createBucket("test");
    }

    @Override
    public void afterEach(ExtensionContext context) {
        s3Mock.stop();
    }
}
