package com.friendsocial.Backend.file;

import jakarta.servlet.ServletContext;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import net.coobird.thumbnailator.Thumbnails;

import java.util.UUID;
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
      // Generate a unique filename
      String uniqueFileName = generateUniqueFileName(file.getOriginalFilename());

      byte[] bytes = file.getBytes();
      Path path = Paths.get(uploadDir + File.separator + uniqueFileName);
      Files.write(path, bytes);

      File resizedImage = resizeImage(path.toFile(), 350);

      // Save the file path or URL to your database
      // Or return filepath and save to DB from there
      Map<String, String> response = new HashMap<>();

      response.put("userPic", resizedImage.getName());
      return ResponseEntity.ok(response);
    } catch (IOException e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  private File resizeImage(File input, int targetSize) throws IOException {
    String originalFileName = input.getName();
    String outputFileName = "resized_" + originalFileName.replace(".jpeg", ".png");

    File output = new File(uploadDir + File.separator + "re_" + outputFileName); // Modify the output filename

    
    Thumbnails.Builder<File> thumbnailBuilder = Thumbnails.of(input);
    thumbnailBuilder.size(targetSize, targetSize);

    // Crop from the center to achieve a 1:1 aspect ratio for rectangular images
    thumbnailBuilder.keepAspectRatio(false).crop(Positions.CENTER);
    thumbnailBuilder.outputFormat("png")
            .toFile(output);

    return output;
  }

  // Generates a unique filename using combo of UUID and original file extension.
  private String generateUniqueFileName(String originalFileName) {
    String uniqueID = UUID.randomUUID().toString();
    String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
    return uniqueID + fileExtension;
  }

}
