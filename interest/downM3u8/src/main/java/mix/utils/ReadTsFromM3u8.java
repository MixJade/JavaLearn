package mix.utils;

import mix.entiy.TsName;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 从m3u8文件中读取ts文件
 */
public class ReadTsFromM3u8 {

    /**
     * 从m3u8文件中读取ts的文件路径
     *
     * @param m3u8Path m3u8的文件路径
     * @param baseUrl  基础下载路径
     * @return ts的下载路径+文件名
     */
    public static List<TsName> readTsNameList(String m3u8Path, String baseUrl) {
        List<TsName> myTsList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(m3u8Path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // 是#EXT开头则读取下一行
                if (line.startsWith("#EXTINF:") && (line = reader.readLine()) != null) {
                    if (line.startsWith("http"))
                        myTsList.add(new TsName(line, getNameFromUrl(line)));
                    else
                        myTsList.add(new TsName(baseUrl + line, line));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 最后输出
        System.out.println("一共" + myTsList.size() + "个ts文件");
        return myTsList;
    }

    /**
     * 从url中读取文件名
     *
     * @param url http路径,如：http://127.0.0.1/sss/sa.ts?sss
     * @return ts文件名, 如：sa.ts
     */
    @SuppressWarnings("JavadocLinkAsPlainText")
    public static String getNameFromUrl(String url) {
        String[] parts = url.split("/");
        String filename = parts[parts.length - 1];
        String[] nameParts = filename.split("\\?");
        return nameParts[0];
    }
}