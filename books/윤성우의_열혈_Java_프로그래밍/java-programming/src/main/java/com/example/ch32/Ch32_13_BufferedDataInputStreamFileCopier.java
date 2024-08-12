package com.example.ch32;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Ch32_13_BufferedDataInputStreamFileCopier {

    public static void main(String[] args) throws IOException {
        try (DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream("data.dat")))) {
            int intData = in.readInt();
            double doubleData = in.readDouble();
            System.out.println(intData);
            System.out.println(doubleData);
        }
    }
}
