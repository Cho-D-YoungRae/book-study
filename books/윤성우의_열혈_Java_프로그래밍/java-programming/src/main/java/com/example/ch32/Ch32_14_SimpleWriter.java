package com.example.ch32;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class Ch32_14_SimpleWriter {

    public static void main(String[] args) throws IOException {
        try (Writer out = new FileWriter("data.txt")) {
            out.write('A');
            out.write('한');
        }
    }
}
