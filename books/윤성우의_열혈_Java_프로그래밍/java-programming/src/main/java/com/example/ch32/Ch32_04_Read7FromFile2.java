package com.example.ch32;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Ch32_04_Read7FromFile2 {

    public static void main(String[] args) throws IOException {
        InputStream is = null;

        try {
            is = new FileInputStream("data.dat");
            int dat = is.read();
            System.out.println(dat);
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }
}
