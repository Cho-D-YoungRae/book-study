package com.example.thisisredispractice.ch7.visitcount;

import com.example.thisisredispractice.JedisHelper;
import redis.clients.jedis.Jedis;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class VisitCountOfDay {

    private static final String KEY_EVENT_CLICK_DAILY_TOTAL = "event:click:daily:total:";

    private static final String KEY_EVENT_CLICK_DAILY = "event:click:daily:";

    private final Jedis jedis;

    public VisitCountOfDay(JedisHelper jedisHelper) {
        this.jedis = jedisHelper.getConnection();
    }

    /**
     * 이벤트 아이디에 해당하는 날자의 방문횟수와 날자별 전체 방문횟수를 증가시킨다.
     * @param eventId 이벤트 아이디
     * @return 이벤트 페이지 방문횟수
     */
    public Long addVisit(String eventId) {
        jedis.incr(KEY_EVENT_CLICK_DAILY_TOTAL + getToday());
        return jedis.incr(KEY_EVENT_CLICK_DAILY + getToday() + ":" + eventId);
    }

    /**
     * 요청된 날자에 해당하는 전체 이벤트 페이지 방문횟수를 조회한다.
     * @return 전체 이벤트 페이지 방문횟수
     */
    public Long getVisitTotalCount(String date) {
        return Long.parseLong(jedis.get(KEY_EVENT_CLICK_DAILY_TOTAL + date));
    }

    /**
     * 이벤트 아이디에 해당하는 요청된 날자들의 방문횟수를 조회한다.
     * @param eventId 요청된 이벤트아이디
     * @param dates 요청날자 목록
     * @return 날짜 목록에 대한 방문횟수 목록
     */
    public List<Long> getVisitCount(String eventId, String[] dates) {
        return Arrays.stream(dates)
                .map(date -> Optional.ofNullable(
                        jedis.get(KEY_EVENT_CLICK_DAILY + date + ":" + eventId)).orElse("0"))
                .map(Long::valueOf)
                .toList();
    }

    private String getToday() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(new Date());
    }
}
