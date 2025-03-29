package com.book.shortenurl;

import org.springframework.stereotype.Service;

@Service
public class ShortenUrlService {

    private static final String BASE56_CHARACTER_SET = "23456789abcdefghijkmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ";
    private static final int SHORTEN_URL_LENGTH = 8;
    private final RandomGenerator randomGenerator;
    private final ShortenUrlRepository shortenUrlRepository;

    public ShortenUrlService(RandomGenerator randomGenerator, ShortenUrlRepository shortenUrlRepository) {
        this.randomGenerator = randomGenerator;
        this.shortenUrlRepository = shortenUrlRepository;
    }

    public ShortenUrlCreateResult generateShortenUrl(ShortenUrlCreate shortenUrlCreate) {
        String key = generateKey();
        var shortenUrl = new ShortenUrl(shortenUrlCreate.originalUrl(), key);
        shortenUrlRepository.save(shortenUrl);
        return new ShortenUrlCreateResult(key);
    }

    private String generateKey() {
        StringBuilder sb = new StringBuilder();
        for (int count = 0; count < SHORTEN_URL_LENGTH; count++) {
            int index = randomGenerator.nextInt(0, BASE56_CHARACTER_SET.length());
            sb.append(BASE56_CHARACTER_SET.charAt(index));
        }
        return sb.toString();
    }

    public ShortenUrl get(String key) {
        return shortenUrlRepository.findByKey(key).orElseThrow();
    }

    public String getUrl(String key) {
        ShortenUrl shortenUrl = shortenUrlRepository.findByKey(key).orElseThrow();
        ShortenUrl incresedShortenUrl = shortenUrl.incrementRequestedCount();
        shortenUrlRepository.save(incresedShortenUrl);

        return shortenUrl.originalUrl();
    }
}
