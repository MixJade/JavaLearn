package mix.utils;

import mix.model.TsName;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                    else if (line.startsWith("/"))
                        myTsList.add(new TsName(baseUrl + getNameFromUrl(line), getNameFromUrl(line)));
                    else
                        myTsList.add(new TsName(baseUrl + line, line));
                }
            }
            // 最后输出
            return myTsList;
        } catch (IOException e) {
            return myTsList;
        }
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

    /**
     * 从m3u8文件中读取是否有key
     *
     * @param m3u8Path m3u8的文件路径
     * @return key的文件名
     */
    public static String readKey(String m3u8Path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(m3u8Path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // 存在key就返回key的名字
                if (line.startsWith("#EXT-X-KEY:")) {
                    // 匹配URI="xxx"中的xxx
                    Pattern p = Pattern.compile("(?<=URI=\").*(?=\")");
                    Matcher m = p.matcher(line);
                    if (m.find()) {
                        String keyName = m.group();
                        if (keyName.startsWith("http") || keyName.startsWith("/")) {
                            keyName = getNameFromUrl(keyName);
                        }
                        return keyName;
                    }
                }
            }
            return "";
        } catch (IOException e) {
            return "";
        }
    }

    /**
     * 含有key的行变成真的key
     *
     * @param line 含有key的行,如 #xxxxxx,URI="/xxs/key.key"
     * @return 新的key行, 如 #xxxxxx,URI="key.key"
     */
    public static String getKeyLineForLine(String line) {
        // 匹配URI="xxx"中的xxx
        Pattern p = Pattern.compile("(?<=URI=\").*(?=\")");
        Matcher m = p.matcher(line);
        if (m.find()) {
            String keyName = m.group();
            if (keyName.startsWith("http") || keyName.startsWith("/"))
                line = line.replace(keyName, getNameFromUrl(keyName));
        }
        return line;
    }
}