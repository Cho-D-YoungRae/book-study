package com.example.ch32;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Ch32_02_Read7FromFile {

    public static void main(String[] args) throws IOException {
        InputStream is = new FileInputStream("data.dat");
        int dat = is.read();
        is.close();
        System.out.println(dat);
    }
}
