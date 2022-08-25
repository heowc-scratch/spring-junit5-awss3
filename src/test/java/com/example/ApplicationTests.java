package com.example;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.util.ResourceUtils;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.util.IOUtils;

@SpringBootTest
@ExtendWith(AwsS3Extension.class)
@TestConstructor(autowireMode = AutowireMode.ALL)
class ApplicationTests {

    private final AmazonS3 amazonS3;

    ApplicationTests(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    @Test
    void contextLoads() throws IOException {
        amazonS3.putObject("test", "dummy", ResourceUtils.getFile("classpath:test.txt"));

        String content = IOUtils.toString(amazonS3.getObject("test", "dummy").getObjectContent());
        assertThat(content).isEqualTo("hello\n");
    }
}
