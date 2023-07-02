package de.turing85.spring.fileupload.actor;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Random;

import com.google.common.truth.Truth;
import de.turing85.spring.fileupload.UploadResource;
import io.cucumber.spring.ScenarioScope;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@ScenarioScope
public class FileUploadActor {
  private static final Random RANDOM = new Random();
  private static final int EXPECTED_SIZE_NOT_SET = -1;
  private static final int SIZE_IS_SET_BUT_IRRELEVANT = -2;

  private final URI sutUrl;

  private int expectedSize = EXPECTED_SIZE_NOT_SET;
  private CloseableHttpClient client;
  private CloseableHttpResponse response;

  public FileUploadActor(@Value("${sut.url}") URI sutUrl) {
    this.sutUrl = sutUrl;
  }

  public void uploadFile(int size) throws IOException {
    validateStateIsClean();
    expectedSize = size;
    HttpPost post = constructPostRequest();
    // @formatter:off
    post.setEntity(MultipartEntityBuilder.create()
        .addBinaryBody(
            "file",
            createRandomBytes(size),
            ContentType.APPLICATION_OCTET_STREAM,
            "foo.bar")
        .build());
    // @formatter:on
    client = HttpClients.createDefault();
    response = client.execute(post);
  }

  private void validateStateIsClean() {
    if (expectedSize != EXPECTED_SIZE_NOT_SET) {
      throw new IllegalStateException("expectedSize must not be set");
    }
    if (Objects.nonNull(client)) {
      throw new IllegalStateException("client must not be set");
    }
    if (Objects.nonNull(response)) {
      throw new IllegalStateException("response must not be set");
    }
  }

  private HttpPost constructPostRequest() {
    return new HttpPost(sutUrl.resolve(UploadResource.PATH));
  }

  private static byte[] createRandomBytes(int size) {
    byte[] randomBytes = new byte[size];
    RANDOM.nextBytes(randomBytes);
    return randomBytes;
  }

  public void validateUploadResponse() throws Exception {
    validateStateIsSet();
    try {
      Truth.assertThat(response.getStatusLine().getStatusCode()).isEqualTo(HttpStatus.OK.value());
      String bodyContentAsString =
          new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);
      Truth.assertThat(Integer.parseInt(bodyContentAsString)).isEqualTo(expectedSize);
    } finally {
      resetState();
    }
  }

  private void validateStateIsSet() {
    if (expectedSize == EXPECTED_SIZE_NOT_SET) {
      throw new IllegalStateException("expectedSize must be set");
    }
    if (Objects.isNull(client)) {
      throw new IllegalStateException("client must not be null");
    }
    if (Objects.isNull(response)) {
      throw new IllegalStateException("response must not be null");
    }
  }

  private void resetState() throws IOException {
    response.close();
    response = null;
    client.close();
    client = null;
    expectedSize = EXPECTED_SIZE_NOT_SET;
  }

  public void uploadNoFile() throws IOException {
    validateStateIsClean();
    expectedSize = SIZE_IS_SET_BUT_IRRELEVANT;
    HttpPost post = constructPostRequest();
    post.setEntity(MultipartEntityBuilder.create().build());
    client = HttpClients.createDefault();
    response = client.execute(post);
  }

  public void validateBadRequestResponse() throws IOException {
    validateStateIsSet();
    try {
      Truth.assertThat(response.getStatusLine().getStatusCode())
          .isEqualTo(HttpStatus.BAD_REQUEST.value());
    } finally {
      resetState();
    }
  }
}
