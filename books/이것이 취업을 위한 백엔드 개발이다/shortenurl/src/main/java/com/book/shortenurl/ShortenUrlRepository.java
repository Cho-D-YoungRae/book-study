package com.book.shortenurl;

import java.util.Optional;

public interface ShortenUrlRepository {

    void save(ShortenUrl shortenUrl);

    Optional<ShortenUrl> findByKey(String key);
}
