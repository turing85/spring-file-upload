package de.turing85.spring.fileupload.steps;

import java.io.IOException;

import de.turing85.spring.fileupload.actor.FileUploadActor;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@SuppressWarnings("unused")
public class FileUploadSteps {
  private final FileUploadActor actor;

  @When("I upload a file with a size of {int} bytes")
  public void i_upload_a_file(int size) throws IOException {
    actor.uploadFile(size);
  }

  @Then("I get the expected response")
  public void i_get_the_expected_response() throws Exception {
    actor.validateUploadResponse();
  }

  @When("I upload no file")
  public void i_upload_no_file() throws IOException {
    actor.uploadNoFile();
  }

  @Then("I get a bad request response")
  public void i_get_a_bad_request_response() throws Exception {
    actor.validateBadRequestResponse();
  }
}
