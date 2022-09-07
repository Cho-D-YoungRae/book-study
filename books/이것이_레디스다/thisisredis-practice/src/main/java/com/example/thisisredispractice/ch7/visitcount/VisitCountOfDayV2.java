package com.example.thisisredispractice.ch7.visitcount;

import com.example.thisisredispractice.JedisHelper;
import redis.clients.jedis.Jedis;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class VisitCountOfDayV2 {

    private static final String KEY_EVENT_CLICK_DAILY_TOTAL = "event:daily:click:total:hash";

    private static final String KEY_EVENT_CLICK_DAILY = "event:daily:click:hash:";

    private final Jedis jedis;

    public VisitCountOfDayV2(JedisHelper jedisHelper) {
        this.jedis = jedisHelper.getConnection();
    }

    /**
     * 이벤트 아이디에 해당하는 날자별 방문횟수와 날자별 전체 방문횟수를 증가시킨다.
     * @param eventId 이벤트 아이디
     * @return 이벤트 페이지 방문횟수
     */
    public Long addVisit(String eventId) {
        jedis.hincrBy(KEY_EVENT_CLICK_DAILY_TOTAL, getToday(), 1);
        return jedis.hincrBy(KEY_EVENT_CLICK_DAILY + eventId, getToday(), 1);
    }

    /**
     * 이벤트 페이지에 대한 모든 날자별 방문횟수를 조회한다.
     * @return 전체 이벤트 페이지 방문횟수
     */
    public SortedMap<String, Long> getVisitCountByDailyTotal() {
        return new TreeMap<>(jedis.hgetAll(KEY_EVENT_CLICK_DAILY_TOTAL)
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> Long.valueOf(entry.getValue()))));
    }

    /**
     * 이벤트 아이디에 해당하는 모든 날자의 방문횟수를 조회한다.
     * @param eventId 요청된 이벤트아이디
     * @return 날자로 정렬된 방문횟수 목록
     */
    public SortedMap<String, Long> getVisitCountByDaily(String eventId) {
        return new TreeMap<>(jedis.hgetAll(KEY_EVENT_CLICK_DAILY + eventId)
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> Long.valueOf(entry.getValue()))));
    }

    private String getToday() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(new Date());
    }
}
