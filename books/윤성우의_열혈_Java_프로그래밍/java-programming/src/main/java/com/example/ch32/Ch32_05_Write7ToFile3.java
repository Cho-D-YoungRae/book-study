package com.example.ch32;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Ch32_05_Write7ToFile3 {

    public static void main(String[] args) throws IOException {
        try (OutputStream os = new FileOutputStream("data.dat")) {
            os.write(7);
        }
    }
}
