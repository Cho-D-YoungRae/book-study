package com.example.ch32;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

public class Ch32_08_BufferedFileCopier {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("대상 파일: ");
        String src = sc.nextLine();

        System.out.println("사본 파일: ");
        String dst = sc.nextLine();

        try (InputStream is = new FileInputStream(src); OutputStream os = new FileOutputStream(dst)) {
            byte[] buf = new byte[1024];
            int len;
            while (true) {
                len = is.read(buf);
                if (len == -1) {
                    break;
                }
                os.write(buf, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
