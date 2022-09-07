package com.example.thisisredispractice.ch7.redislogger;

import com.example.thisisredispractice.JedisHelper;
import redis.clients.jedis.Jedis;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class LogReceiver {

    private static final JedisHelper jedisHelper = JedisHelper.getInstance();

    private static final String KEY_WAS_LOG = "was:log";

    private static final String LOG_FILE_NAME_PREFIX = "./waslog";

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HH'.log'");

    private static final int WAITING_TERM = 5000;

    /**
     * 레디스 서버에서 로그를 읽어서 파일로 저장한다.
     * 프로그램이 종료되기 전까지 무한히 실행한다.
     */
    public void start() {
        Random random = new Random();
        Jedis jedis = jedisHelper.getConnection();
        while (true) {
            String filename = writeFile(jedis.getSet(KEY_WAS_LOG, ""));
            System.out.println("LogReceiver save log : " + filename);
            try {
                Thread.sleep(random.nextInt(WAITING_TERM));
            } catch (InterruptedException e) {
                // do nothing
            }
        }
    }

    private String writeFile(String log) {
        String fileName = getCurrentFileName();
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.write(log);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    /**
     * 메서드가 호출된 시간에 해당하는 로그파일명을 생성한다.
     * @return 현재 시간에 해당하는 로그파일명
     */
    private String getCurrentFileName() {
        return LOG_FILE_NAME_PREFIX + dateFormat.format(new Date());
    }
}
