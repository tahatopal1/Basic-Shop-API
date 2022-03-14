package com.example.productws.util;

import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

@Component
public class FileUtil {

    public StringBuffer readFile(String path) {
        try {
            File file = ResourceUtils.getFile(path);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            fr.close();
            return sb;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
