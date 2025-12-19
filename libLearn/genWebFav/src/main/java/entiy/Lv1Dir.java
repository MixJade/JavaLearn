package entiy;

import java.util.List;

public record Lv1Dir(String title, String updateTime, List<Lv2Dir> lv2Dirs) {
}
