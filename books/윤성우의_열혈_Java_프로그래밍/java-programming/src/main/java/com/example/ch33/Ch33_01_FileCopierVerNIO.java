package com.example.ch33;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class Ch33_01_FileCopierVerNIO {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("대상 파일: ");
        Path src = Paths.get(sc.nextLine());

        System.out.println("사본 파일: ");
        Path dst = Paths.get(sc.nextLine());

        // 하나의 버퍼 생성
        ByteBuffer buf = ByteBuffer.allocate(1024);

        // try 에서 두 개의 채널 생성
        try (FileChannel ifc = FileChannel.open(src, StandardOpenOption.READ);
             FileChannel ofc = FileChannel.open(dst, StandardOpenOption.WRITE, StandardOpenOption.CREATE)) {
            int num;
            while (true) {
                num = ifc.read(buf);
                if (num == -1) {
                    break;
                }

                buf.flip();
                ofc.write(buf);
                buf.clear();
            }
        }

    }
}
