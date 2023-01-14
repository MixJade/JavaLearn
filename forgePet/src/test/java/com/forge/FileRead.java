package com.forge;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileRead {
    private final String basePath = "C:\\MixJade\\forgePet\\notice\\";

    public static void main(String[] args) {
        FileRead read = new FileRead();
        String s = read.fileRead(read.basePath + "notice-1.txt");
        System.out.println(s);
        read.fileWr(s, read.basePath + "test-2.txt");
    }

    public String fileRead(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("没有找到文件" + e);
        }
        return null;
    }

    public void fileWr(String text, String path) {
        FileWriter writer;
        try {
            writer = new FileWriter(path);
            writer.write("");//清空原文件内容
            writer.write(text);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("文件写入失败");
        }
    }
}
