package mix.utils;

import mix.model.TsFileGroup;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 检查m3u8
 *
 * @since 2024-10-16 18:01:14
 */
public class CheckM3u8 {
    /**
     * 通过检查m3u8中的重复播放组来寻找疑似广告
     *
     * @param m3u8FilePath m3u8文件路径
     * @return 输出的字符串
     */
    public static String checkDupTsGroup(String m3u8FilePath) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(m3u8FilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException ignored) {
        }

        List<TsFileGroup> result = new ArrayList<>();

        // 将每个播放组的前四个时长保存为对象
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).startsWith("#EXT-X-DISCONTINUITY")) {
                if (i + 7 > lines.size() - 1) continue; // 防止越界
                TsFileGroup tsFileGroup = new TsFileGroup(
                        lines.get(i + 1).trim(),
                        lines.get(i + 3).trim(),
                        lines.get(i + 5).trim(),
                        lines.get(i + 7).trim(),
                        lines.get(i + 2).trim()
                );
                result.add(tsFileGroup);
            }
        }
        // 将该列表变为map,以时长的hash为key,方便筛选重复播放组
        Map<Integer, List<TsFileGroup>> tsGroupMap = result.stream()
                .collect(Collectors.groupingBy(TsFileGroup::getKey));
        // 筛出重复了两次以上的播放组(即：疑似广告的播放组)
        List<List<TsFileGroup>> dupTsNamesList = tsGroupMap.values().stream()
                .filter(value -> value.size() > 1)
                .toList();
        // 输出结果
        StringBuilder outStr = new StringBuilder("重复的ts组有" + dupTsNamesList.size() + "个\n");
        for (int i = 0; i < dupTsNamesList.size(); i++) {
            List<TsFileGroup> dupTsNames = dupTsNamesList.get(i);
            outStr.append("【疑似 广告")
                    .append(i + 1)
                    .append("】 时长:")
                    .append(dupTsNames.get(0).time1().replace("#EXTINF:", ""))
                    .append("\n");
            for (TsFileGroup dupTsName : dupTsNames) {
                outStr.append(dupTsName.tsName())
                        .append("\n");
            }
        }
        return outStr.toString();
    }
}