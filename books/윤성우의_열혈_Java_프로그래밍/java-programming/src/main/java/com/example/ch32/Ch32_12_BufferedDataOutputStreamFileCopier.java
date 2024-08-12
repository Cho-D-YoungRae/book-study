package com.example.ch32;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Ch32_12_BufferedDataOutputStreamFileCopier {

    public static void main(String[] args) throws IOException {
        try (DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("data.dat")))) {
            out.writeInt(100);
            out.writeDouble(3.14);
        }
    }
}
