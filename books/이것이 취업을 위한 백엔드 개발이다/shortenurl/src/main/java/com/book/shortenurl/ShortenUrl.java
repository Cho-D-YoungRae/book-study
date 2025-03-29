package com.book.shortenurl;

public record ShortenUrl(
        String originalUrl,
        String key,
        long requestedCount
) {

    public ShortenUrl(String originalUrl, String shortenUrlKey) {
        this(originalUrl, shortenUrlKey, 0);
    }

    public ShortenUrl incrementRequestedCount() {
        return new ShortenUrl(originalUrl, key, requestedCount + 1);
    }
}
