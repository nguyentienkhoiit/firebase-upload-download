package demo.controller;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import demo.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FileController {


    private final FileService fileService;
    private final ResourceLoader resourceLoader;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile multipartFile) {
        return ResponseEntity.ok(fileService.upload(multipartFile));
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<?> download(@PathVariable("filename") String fileName) {
        Resource resource = fileService.download(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"").body(resource);
    }

    @GetMapping("/get-resource-link")
    public String getResourceLink(@RequestParam String fileName) {
        try {
            // Load Firebase Admin SDK credentials from the classpath
            Resource serviceAccountResource = new ClassPathResource("service-account.json");
            InputStream serviceAccount = serviceAccountResource.getInputStream();
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);

            // Initialize the Firebase Storage client
            Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

            // Get a reference to the resource (file) you want to get the link for
            Blob blob = storage.get("emss-400017.appspot.com", fileName);

            // Generate a signed URL with an expiration time
            String downloadUrl = blob.signUrl(3600, TimeUnit.DAYS).toString();

            return downloadUrl;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: Unable to retrieve resource link";
        }
    }

}
