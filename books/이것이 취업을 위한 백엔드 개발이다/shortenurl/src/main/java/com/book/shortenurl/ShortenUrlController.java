package com.book.shortenurl;

import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class ShortenUrlController {

    private final ShortenUrlService shortenUrlService;

    public ShortenUrlController(ShortenUrlService shortenUrlService) {
        this.shortenUrlService = shortenUrlService;
    }

    @PostMapping("/shorten-url")
    public ShortenUrlCreateResponse create(@Valid @RequestBody ShortenUrlCreateRequest request) {
        return ShortenUrlCreateResponse.from(shortenUrlService.generateShortenUrl(request.toShortenUrlCreate()));
    }

    @GetMapping("/{shortenUrlKey}")
    public ResponseEntity<Void> redirect(@PathVariable String shortenUrlKey) {
        String originalUrl = shortenUrlService.getUrl(shortenUrlKey);
        URI redirectUri = URI.create(originalUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(redirectUri);
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .headers(headers)
                .build();
    }

    @GetMapping("/shorten-url/{shortenUrlKey}")
    public ShortenUrlResponse get(@PathVariable String shortenUrlKey) {
        ShortenUrl shortenUrl = shortenUrlService.get(shortenUrlKey);
        return ShortenUrlResponse.from(shortenUrl);
    }
}
