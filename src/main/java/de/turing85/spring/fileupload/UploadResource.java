package de.turing85.spring.fileupload;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(UploadResource.PATH)
@Slf4j
public class UploadResource {
  public static final String PATH = "upload";

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
      produces = MediaType.TEXT_PLAIN_VALUE)
  ResponseEntity<String> upload(@RequestParam MultipartFile file) {
    long fileSize = file.getSize();
    log.info("Received file \"{}\" with byte-size {}", file.getOriginalFilename(), fileSize);
    return ResponseEntity.ok("%d".formatted(fileSize));
  }
}
