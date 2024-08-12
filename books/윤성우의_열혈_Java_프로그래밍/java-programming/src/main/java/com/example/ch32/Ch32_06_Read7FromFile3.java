package com.example.ch32;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Ch32_06_Read7FromFile3 {

    public static void main(String[] args) throws IOException {
        try (InputStream is = new FileInputStream("data.dat")) {
            int dat = is.read();
            System.out.println(dat);
        }
    }
}
