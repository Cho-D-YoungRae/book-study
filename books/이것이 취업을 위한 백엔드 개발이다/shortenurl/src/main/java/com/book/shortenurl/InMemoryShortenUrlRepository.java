package com.book.shortenurl;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryShortenUrlRepository implements ShortenUrlRepository {

    private final Map<String, ShortenUrl> data = new ConcurrentHashMap<>();

    @Override
    public void save(ShortenUrl shortenUrl) {
        data.put(shortenUrl.key(), shortenUrl);
    }

    @Override
    public Optional<ShortenUrl> findByKey(String key) {
        return Optional.ofNullable(data.get(key));
    }
}
