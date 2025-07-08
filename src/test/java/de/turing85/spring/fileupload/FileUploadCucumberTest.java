package de.turing85.spring.fileupload;

import de.turing85.spring.fileupload.config.CucumberTestConfiguration;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@Suite
@SelectPackages("de/turing85/spring/fileupload")
@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = CucumberTestConfiguration.class)
public class FileUploadCucumberTest {
}
