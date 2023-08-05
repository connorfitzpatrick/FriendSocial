package com.friendsocial.Backend.file;

import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

  @CrossOrigin(origins = "http://localhost:4200")
  @GetMapping("/image/{fileName:.+}")
  public ResponseEntity<Resource> getProfilePicture(@PathVariable String fileName) throws IOException {
    System.out.println("GET PROFILE PICTURE IS ACCESSIBLE!");
    System.out.println(fileName);
    Path filePath = Paths.get("Backend/uploads").resolve(fileName);
    System.out.println(filePath);
    Resource resource = new UrlResource(filePath.toUri());
    System.out.println(resource.getURL());
    return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_JPEG) // Change the media type based on your image format
            .body(resource);
  }

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
      response.put("userPic", file.getOriginalFilename());
      return ResponseEntity.ok(response);
    } catch (IOException e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
