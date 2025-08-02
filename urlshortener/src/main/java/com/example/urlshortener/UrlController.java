package com.example.urlshortener;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import java.net.URI;
import java.util.Optional;
import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@RestController
public class UrlController {

    private final UrlMappingRepository urlMappingRepository;

    public UrlController(UrlMappingRepository urlMappingRepository) {
        this.urlMappingRepository = urlMappingRepository;
    }

    @PostMapping("/shorten")
    public ResponseEntity<String> shortenUrl(@RequestBody String longUrl) {
        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setLongUrl(longUrl);
        urlMapping.setCreatedAt(LocalDateTime.now());

        String shortCode = RandomStringUtils.randomAlphanumeric(6);
        urlMapping.setShortCode(shortCode);

        urlMappingRepository.save(urlMapping);

        String shortUrl = "http://localhost:8080/" + shortCode;

        return ResponseEntity.ok(shortUrl);
    }

    // Add this new method to your UrlController.java file

@GetMapping("/{shortCode}")
public ResponseEntity<Void> redirectToLongUrl(@PathVariable String shortCode) {
    // 1. Find the mapping in the database
    Optional<UrlMapping> urlMappingOptional = urlMappingRepository.findById(shortCode);

    if (urlMappingOptional.isPresent()) {
        // 2. If found, get the long URL
        UrlMapping urlMapping = urlMappingOptional.get();
        String longUrl = urlMapping.getLongUrl();

        // 3. Create response headers to specify the redirect location
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(longUrl));

        // 4. Return an HTTP 302 "Found" (redirect) response
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    } else {
        // 5. If not found, return a "404 Not Found" error
        return ResponseEntity.notFound().build();
    }
}
}