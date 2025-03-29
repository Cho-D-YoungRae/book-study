package com.book.shortenurl;

public record ShortenUrlCreateResponse(
        String key
) {

    public static ShortenUrlCreateResponse from(ShortenUrlCreateResult shortenUrlCreateResult) {
        return new ShortenUrlCreateResponse(shortenUrlCreateResult.key());
    }
}
