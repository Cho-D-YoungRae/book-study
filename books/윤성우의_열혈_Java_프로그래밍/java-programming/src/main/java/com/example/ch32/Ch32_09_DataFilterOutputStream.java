package com.example.ch32;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Ch32_09_DataFilterOutputStream {

    public static void main(String[] args) throws IOException {
        try (DataOutputStream os = new DataOutputStream(new FileOutputStream("data.dat"))) {
            os.writeInt(100);
            os.writeDouble(3.14);
        }
    }
}
