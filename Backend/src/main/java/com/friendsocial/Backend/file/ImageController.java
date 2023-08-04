package com.friendsocial.Backend.file;

import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ImageController {
  @Value("${file.upload-dir}")
  private String uploadDir;

  @Autowired
  ServletContext context;

  @PostMapping("/upload")
  public ResponseEntity<Map<String, String>>  handleFileUpload(@RequestParam("file") MultipartFile file) {

    if (file.isEmpty()) {
      throw new RuntimeException("Please load a file");
    }

    try {
      // Create the directory if it doesn't exist
      File directory = new File(uploadDir);
      if (!directory.exists()) {
        directory.mkdirs();
      }

      byte[] bytes = file.getBytes();
      Path path = Paths.get(uploadDir + File.separator + file.getOriginalFilename());
      Files.write(path, bytes);

      // Save the file path or URL to your database
      // Might want to send to post or profile service?
      // Or return filepath and save to DB from there
      Map<String, String> response = new HashMap<>();
      response.put("userPic", path.toString());
      return ResponseEntity.ok(response);
    } catch (IOException e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
