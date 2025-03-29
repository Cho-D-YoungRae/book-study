package com.book.shortenurl;

public record ShortenUrlResponse(
        String originalUrl,
        String key,
        long requestedCount
) {

    public static ShortenUrlResponse from(ShortenUrl shortenUrl) {
        return new ShortenUrlResponse(shortenUrl.originalUrl(), shortenUrl.key(), shortenUrl.requestedCount());
    }
}
