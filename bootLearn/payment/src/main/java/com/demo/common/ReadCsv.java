package com.demo.common;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadCsv {
    public static void main(String[] args) {
        ReadCsv readCsv= new ReadCsv();
        readCsv.ssssssssss();
    }
    public void ssssssssss() {
        String csvFile = "微信支付账单.csv";
        String line;
        String csvSplitBy = ",";
        String[] headers;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // 读取表头
            headers = br.readLine().split(csvSplitBy);

            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                for (int i = 0; i < headers.length; i++) {
                    System.out.print(headers[i] + ": " + data[i] + " ");
                }
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
