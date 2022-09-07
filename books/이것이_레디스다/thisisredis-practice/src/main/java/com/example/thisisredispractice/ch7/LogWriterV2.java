package com.example.thisisredispractice.ch7;

import com.example.thisisredispractice.JedisHelper;
import lombok.RequiredArgsConstructor;
import redis.clients.jedis.Jedis;

import java.text.SimpleDateFormat;
import java.util.Date;

@RequiredArgsConstructor
public class LogWriterV2 {

    private static final String KEY_WAS_LOG = "was:log:list";

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss SSS");

    private final JedisHelper jedisHelper;

    /**
     * 레디스에 로그를 기록하는 메서드.
     * @param log 저장할 로그문자열
     * @return 저장된 후의 레디스에 저장된 로그 문자열의 길이.
     */
    public Long log(String log) {
        Jedis jedis = jedisHelper.getConnection();
        Long rtn = jedis.lpush(KEY_WAS_LOG, dateFormat.format(new Date()) + log + "\n");
        jedisHelper.returnResource(jedis);
        return rtn;
    }
}
