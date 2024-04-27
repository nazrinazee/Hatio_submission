package com.example.todo.controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.todo.entity.GistRequest;

@RestController
public class GistController {

	@Value("${github.token}")
	private String githubToken; // Add your GitHub personal access token to application.properties or use other
								// methods to store sensitive data

	@Autowired
	private RestTemplate restTemplate;

	@PostMapping("/api/create-gist")
	@ResponseBody
	public ResponseEntity<?> createGist(@RequestBody GistRequest gistRequest) {
		String apiUrl = "https://api.github.com/gists";

		// Prepare HTTP headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "token " + githubToken); // Add GitHub token for authentication

		// Prepare HTTP entity with request body and headers
		HttpEntity<GistRequest> requestEntity = new HttpEntity<>(gistRequest, headers);

		// Make POST request to GitHub API to create the gist
		ResponseEntity<Map> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, Map.class);

		// Extract the URL of the created gist from the response
		String gistUrl = (String) response.getBody().get("html_url");

		// Fetch content of the created gist
		ResponseEntity<String> gistContentResponse = restTemplate.getForEntity(gistUrl, String.class);

		// Save the fetched content to a local Markdown file
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("exported_gist.md"))) {
			writer.write(gistContentResponse.getBody());
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Failed to save the exported gist file.");
		}

		return ResponseEntity.status(HttpStatus.CREATED).body("Exported gist file saved successfully.");
	}
}
