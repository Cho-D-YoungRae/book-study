package com.example.thisisredispractice.ch7;

import com.example.thisisredispractice.JedisHelper;
import redis.clients.jedis.Jedis;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class LogReceiverV2 {

    private static final JedisHelper jedisHelper = JedisHelper.getInstance();

    private static final String KEY_WAS_LOG = "was:log:list";

    private static final String LOG_FILE_NAME_PREFIX = "./waslog";

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HH'.log'");

    private static final int WAITING_TERM = 5000;

    /**
     * 레디스 서버에서 로그를 읽어서 파일로 저장한다.
     * 리스트에 저장된 모든 요소를 파일로 저장한다. 요소가 없으면 메서드가 종료된다.
     */
    public void start() {
        Random random = new Random();
        Jedis jedis = jedisHelper.getConnection();
        while (true) {
            String log = jedis.rpop(KEY_WAS_LOG);
            if (log == null) {
                break;
            }
            String filename = writeFile(log);
            System.out.println("LogReceiver save log : " + filename);
        }
    }

    private String writeFile(String log) {
        String fileName = getCurrentFileName();
        try (FileWriter writer = new FileWriter(fileName, true)) {
            if (log == null) {
                return null;
            }
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
