package com.example.thisisredispractice.ch7.visitcount;

import com.example.thisisredispractice.JedisHelper;
import redis.clients.jedis.Jedis;

import java.util.Arrays;
import java.util.List;

public class VisitCount {

    private static final String KEY_EVENT_CLICK_TOTAL = "event:click:total";

    private static final String KEY_EVENT_CLICK = "event:click:";

    private final Jedis jedis;

    public VisitCount(JedisHelper jedisHelper) {
        this.jedis = jedisHelper.getConnection();
    }

    /**
     * 요청된 이벤트 페이지의 방문횟수와 전체 이벤트 페이지의 방문횟수를 증가시킨다.
     * @param eventId 이벤트 아이디
     * @return 요청된 이벤트 페이지의 총 방문횟수
     */
    public Long addVisit(String eventId) {
        jedis.incr(KEY_EVENT_CLICK_TOTAL);
        return jedis.incr(KEY_EVENT_CLICK + eventId);
    }

    /**
     * 전체 이벤트 페이지 방문횟수를 조회한다.
     * @return 전체 이벤트 페이지 방문횟수
     */
    public Long getVisitTotalCount() {
        return Long.parseLong(jedis.get(KEY_EVENT_CLICK_TOTAL));
    }

    /**
     * 요청된 이벤트 아이디들에 대한 방문횟수를 조회한다.
     * @param eventIds 이벤트 아이디 목록
     * @return 이벤트 아이디 목록에 대한 방문횟수
     */
    public List<Long> getVisitCountByDate(String... eventIds) {
        return Arrays.stream(eventIds)
                .map(eventId -> jedis.get(KEY_EVENT_CLICK + eventId))
                .map(Long::parseLong)
                .toList();
    }
}
