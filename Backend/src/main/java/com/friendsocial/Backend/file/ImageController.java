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

import java.nio.file.StandardCopyOption;
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
    System.out.println(fileName);

    Path filePath = Paths.get("Backend/uploads").resolve(fileName);
    Resource resource = new UrlResource(filePath.toUri());

    System.out.println(resource.getURL());
    return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_JPEG) // Change the media type based on your image format
            .body(resource);
  }

  @PostMapping("/upload")
  public ResponseEntity<Map<String, String>>  handleFileUpload(
          @RequestParam("file") MultipartFile file,
          @RequestParam("isProfilePic") boolean isProfilePic
  ) {
    if (file.isEmpty()) {
      throw new RuntimeException("No file detected");
    }
    try {
      // Create the directory if it doesn't exist
//      File directory = new File(uploadDir);
//      if (!directory.exists()) {
//        directory.mkdirs();
//      }
      // Generate a unique filename
      // Generate a unique filename
      String uniqueFileName = generateUniqueFileName(file.getOriginalFilename());

      // Decide the target size based on isProfilePic
      int targetSize = isProfilePic ? 320 : 1080;

      // Convert the MultipartFile into a byte array
      byte[] bytes = file.getBytes();

      // Generate a temporary File object to be resized
      Path tempPath = Files.createTempFile("temp_", ".tmp");
      Files.write(tempPath, bytes);

      // Resize the image
      File resizedImage = resizeImage(tempPath.toFile(), uniqueFileName, targetSize);
      System.out.println("Resized image path: " + resizedImage.getAbsolutePath());
      File debugFile = new File(resizedImage.getAbsolutePath());
      if(debugFile.exists() && debugFile.canRead()) {
        System.out.println("File exists and can be read.");
      } else {
        System.out.println("File either does not exist or cannot be read.");
      }


      // Move the resized image to the final directory with the unique filename
      Path finalPath = Paths.get(uploadDir + File.separator + uniqueFileName);
      System.out.println("Final image path: " + finalPath.toString());

      if (Files.exists(resizedImage.toPath())) {
        Files.move(resizedImage.toPath(), finalPath, StandardCopyOption.REPLACE_EXISTING);
      } else {
        System.out.println("Resized image does not exist.");
      }

      // Delete the temporary file
      Files.deleteIfExists(tempPath);

      // Save the file path or URL to your database
      // Or return filepath and save to DB from there
      Map<String, String> response = new HashMap<>();
      response.put("userPic", finalPath.getFileName().toString());
      return ResponseEntity.ok(response);
    } catch (IOException e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  private File resizeImage(File input, String uniqueFileName, int targetSize) throws IOException {
    try {
      File output = new File(uploadDir + File.separator + uniqueFileName); // Use the uniqueFileName

      Thumbnails.Builder<File> thumbnailBuilder = Thumbnails.of(input);
      thumbnailBuilder.size(targetSize, targetSize);

      // Crop from the center to achieve a 1:1 aspect ratio for rectangular images
      thumbnailBuilder.crop(Positions.CENTER);
      thumbnailBuilder.outputFormat("png")
              .toFile(output);

      return output;
    } catch (Exception e) {
      e.printStackTrace();
      throw new IOException("Error resizing image: " + e.getMessage());
    }
  }


  // Generates a unique filename using combo of UUID and original file extension.
  private String generateUniqueFileName(String originalFileName) {
    String uniqueID = UUID.randomUUID().toString();
    String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
    return uniqueID + fileExtension;
  }

}
