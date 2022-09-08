package com.example.thisisredispractice.ch7.recentview;

import com.example.thisisredispractice.JedisHelper;
import redis.clients.jedis.Jedis;

import java.util.Set;

public class RecentViewListV2 {

    public static final int LIST_MAX_SIZE = 30;

    private static final String KEY_VIEW_LIST = "recent:view:zset";

    private final Jedis jedis;

    private final String key;

    public RecentViewListV2(JedisHelper helper, String userNo) {
        this.jedis = helper.getConnection();
        this.key = KEY_VIEW_LIST + userNo;
    }

    /**
     * 최근 조회 상품 목록에 상품을 추가한다.
     * @param productNo 상품 번호
     * @return 저장된 상품 목록의 개수
     */
    public Long add(String productNo) {
        Long result = jedis.zadd(key, System.nanoTime(), productNo);
        jedis.zremrangeByRank(key, -(LIST_MAX_SIZE + 1), -(LIST_MAX_SIZE + 1));
        return result;
    }

    /**
     * 주어진 사용자의 저장된 최근 조회 상품목록을 조회한다.
     * @return 조회된 상품목록
     */
    public Set<String> getRecentViewList() {
        return jedis.zrevrange(key, 0, -1);
    }

    /**
     * 주어진 개수에 해당하는 최근 조회 상품목록을 조회한다.
     * @param cnt 조회할 상품의 개수
     * @return 조회된 상품목록
     */
    public Set<String> getRecentViewList(int cnt) {
        return jedis.zrevrange(this.key, 0, (cnt - 1));
    }
}
