package com.example.ch32;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Ch32_03_Write7ToFile2 {

    public static void main(String[] args) throws IOException {
        OutputStream os = null;

        try {
            os = new FileOutputStream("data.dat");
            os.write(7);
        } finally {
            if (os != null) {
                os.close();
            }
        }
    }
}
