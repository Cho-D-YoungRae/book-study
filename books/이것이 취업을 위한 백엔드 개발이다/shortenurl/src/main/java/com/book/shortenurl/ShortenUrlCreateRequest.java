package com.book.shortenurl;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public record ShortenUrlCreateRequest(
        @NotBlank @URL(regexp = "[(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)")
        String url
) {

    public ShortenUrlCreate toShortenUrlCreate() {
        return new ShortenUrlCreate(url);
    }
}
