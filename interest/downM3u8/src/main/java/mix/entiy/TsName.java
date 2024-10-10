package mix.entiy;

import java.util.Objects;

/**
 * ts的下载路径+文件名
 *
 * @param url      下载路径
 * @param fileName 文件名
 */
public record TsName(String url, String fileName) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TsName tsName = (TsName) o;
        return fileName.equals(tsName.fileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileName);
    }
}
