package de.turing85.spring.fileupload.steps;

import de.turing85.spring.fileupload.config.CucumberTestConfiguration;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = CucumberTestConfiguration.class)
@SuppressWarnings("unused")
class GlueConfig {
}
