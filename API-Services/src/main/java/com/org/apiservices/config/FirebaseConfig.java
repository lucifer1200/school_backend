package com.org.apiservices.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

	@PostConstruct
	public void initialize() {
		try {
			FileInputStream serviceAccount =
					 new FileInputStream("path/to/your/firebase-service-account.json");
					//new FileInputStream("C:\\Users\\50048041\\Documents\\FireBasePath\\firebase-service-account.json");

			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}