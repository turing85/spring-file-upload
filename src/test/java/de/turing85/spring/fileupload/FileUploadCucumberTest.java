package de.turing85.spring.fileupload;

import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@Suite
@SelectClasspathResource("de/turing85/spring/fileupload")
@CucumberContextConfiguration
@ContextConfiguration(classes = FileUploadApplication.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FileUploadCucumberTest {
}
