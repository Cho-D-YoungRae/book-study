package net.sf.redisbook.ch7.uniquevisit;

import java.text.SimpleDateFormat;
import java.util.Date;
import redis.clients.jedis.*;
import net.sf.redisbook.JedisHelper;

public class UniqueVisit {
	private Jedis jedis;
	private static final String KEY_PAGE_VIEW = "page:view:";
	private static final String KEY_UNIQUE_VISITOR = "unique:visitors:";

	public UniqueVisit(JedisHelper helper) {
		this.jedis = helper.getConnection();
	}

	/**
	 * 특정 사용자의 순 방문횟수와 누적 방문횟수를 증가 시킨다.
	 * @param userNo 사용자번호
	 */
	public void visit(int userNo) {
		Pipeline p = this.jedis.pipelined();
		p.incr(KEY_PAGE_VIEW + getToday());
		p.setbit(KEY_UNIQUE_VISITOR + getToday(), userNo, true);
		p.sync();
	}

	/**
	 * 요청된 날자의 누적 방문자수를 조회한다.
	 * @param date 조회대상 날자
	 * @return 누적 방문자수
	 */
	public int getPVCount(String date) {
		int result = 0;
		try {
			result = Integer.parseInt(jedis.get(KEY_PAGE_VIEW + date));
		}
		catch (Exception e) {
			result = 0;
		}
		return result;
	}

	/**
	 * 요청된 날자의 순 방문자수를 조회한다.
	 * @param date 조회대상 날자
	 * @return 순 방문자수
	 */
	public Long getUVCount(String date) {
		return jedis.bitcount(KEY_UNIQUE_VISITOR + date);
	}

	private String getToday() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(new Date());
	}
}
