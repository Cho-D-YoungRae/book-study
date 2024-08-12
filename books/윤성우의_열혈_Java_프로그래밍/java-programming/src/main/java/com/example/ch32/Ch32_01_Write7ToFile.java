package com.example.ch32;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Ch32_01_Write7ToFile {

    public static void main(String[] args) throws IOException {
        OutputStream os = new FileOutputStream("data.dat");
        os.write(7);
        os.close();
    }
}
