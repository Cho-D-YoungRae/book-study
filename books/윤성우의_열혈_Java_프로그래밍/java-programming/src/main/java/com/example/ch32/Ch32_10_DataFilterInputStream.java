package com.example.ch32;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Ch32_10_DataFilterInputStream {

    public static void main(String[] args) throws IOException {
        try (DataInputStream is = new DataInputStream(new FileInputStream("data.dat"))) {
            int intData = is.readInt();
            double doubleData = is.readDouble();
            System.out.println(intData);
            System.out.println(doubleData);
        }
    }
}
