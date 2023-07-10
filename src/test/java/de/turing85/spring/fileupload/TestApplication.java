package de.turing85.spring.fileupload;

import java.net.URI;

import de.turing85.spring.fileupload.actor.FileUploadActor;
import io.cucumber.spring.ScenarioScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
class TestApplication {
  @Bean
  @ScenarioScope
  @Profile("test")
  static FileUploadActor testActor(
      @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
      @LocalServerPort int port) {
    return new FileUploadActor(URI.create("http://localhost:%d".formatted(port)));
  }

  @Bean
  @ScenarioScope
  @Profile("e2e")
  static FileUploadActor e2eActor(@Value("${sut.url}") URI sutUrl) {
    return new FileUploadActor(sutUrl);
  }
}
