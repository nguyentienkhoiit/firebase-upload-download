package demo.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;


@Configuration
public class FirebaseStorage {

    @Value("classpath:service-account.json")
    Resource serviceAccount;

    @Value("${firebase.bucketname}")
    String bucketName;


    @Bean
    Bucket storageClient() throws IOException {
        var options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream()))
                .setStorageBucket(bucketName)
                .build();
        FirebaseApp firebaseApp=FirebaseApp.initializeApp(options);

        return StorageClient.getInstance(firebaseApp).bucket();
    }



}
