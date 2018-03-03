package com.example.filmcatalog.utils;

import java.io.IOException;
import java.io.InputStream;

public class ResourceUtils {

    public String readString(String fileName) {
        InputStream stream = getClass().getClassLoader().getResourceAsStream(fileName);
        try {
            int size = stream.available();
            byte[] buffer = new byte[size];
            int result = stream.read(buffer);
            return new String(buffer, "utf8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
