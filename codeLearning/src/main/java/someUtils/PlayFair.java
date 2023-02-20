package someUtils;

import java.nio.CharBuffer;


public class PlayFair {
    public static void main(String[] args) {
        String plain = "you are a good listener".replaceAll(" ", ""); // 明文,去掉空格
        String key = "hello world".replaceAll(" ", ""); // 密钥，去掉空格
        System.out.println("明文: " + plain + " \n密钥: " + key);
        PlayFair playFair = new PlayFair();
        String cipher = playFair.encode(plain, key);
        System.out.println("==========================================");
        System.out.println("密文: " + cipher);
        String plain1 = playFair.decode(cipher, key); // 输入密文和密钥解密
        System.out.println("解密后的明文:" + plain1);
    }

    /**
     * PlayFair的加密
     */
    public String encode(String plain01, String key01) {
        char[] plain = plain01.toLowerCase().toCharArray(), key = key01.toLowerCase().toCharArray();
        char replace = 'x';
        // 构造解密矩阵
        // 为缓冲区分配空间
        CharBuffer xPlain = CharBuffer.allocate(plain.length * 2);
        // 重新生成明文
        int i, len = 0;
        for (i = 0; i < plain.length - 1; i++) {
            xPlain.append(plain[i]);
            if (i == plain.length - 1) {
                xPlain.append(replace);
            } else if (plain[i] == plain[i + 1]) {
                xPlain.append(replace);
            } else {
                xPlain.append(plain[i + 1]);
                i++;
            }
            len += 2;
        }
        // 剩余最后一个字符
        if (i == plain.length - 1) {
            xPlain.append(plain[i]);
            xPlain.append(replace);
            len += 2;
        }
        char[] xxPlain = new char[len];
        xPlain.position(0);
        xPlain.get(xxPlain);
        System.out.println("整理后的明文: " + new String(xxPlain));
        return getCipher(xxPlain, key);
    }

    /**
     * 处理密文，交给解密函数
     */
    public String decode(String cipher01, String key01) {
        char[] cipher = cipher01.toLowerCase().toCharArray(),
                key = key01.toLowerCase().toCharArray();
        return getPlain(cipher, key);
    }

    /**
     * 根据密文和密钥解密密文并返回生成的明文
     */
    private String getPlain(char[] cipher, char[] key) {
        char[][] matrix = constructMatrix(key);
        char[] plain = new char[cipher.length];
        int index = 0;
        for (int i = 0; i < cipher.length; i += 2) {
            int row1, row2, col1, col2;
            String[] pos1, pos2;
            pos1 = getPosition(matrix, cipher[i]);
            pos2 = getPosition(matrix, cipher[i + 1]);
            if (pos1 == null || pos2 == null) throw new RuntimeException("密文中包含无效字符");
            row1 = Integer.parseInt(pos1[0]);
            col1 = Integer.parseInt(pos1[1]);
            row2 = Integer.parseInt(pos2[0]);
            col2 = Integer.parseInt(pos2[1]);
            if (row1 == row2) {
                // 同一行的情况
                if (col1 == 0) {
                    plain[index++] = matrix[row1][4];
                    plain[index++] = matrix[row1][col2 - 1];
                } else if (col2 == 0) {
                    plain[index++] = matrix[row1][col1 - 1];
                    plain[index++] = matrix[row1][4];
                } else {
                    plain[index++] = matrix[row1][col1 - 1];
                    plain[index++] = matrix[row1][col2 - 1];
                }
            } else if (col1 == col2) {
                // 同一列的情况
                if (row1 == 0) {
                    plain[index++] = matrix[4][col1];
                    plain[index++] = matrix[row2 - 1][col1];
                } else if (row2 == 0) {
                    plain[index++] = matrix[row1 - 1][col1];
                    plain[index++] = matrix[4][col1];
                } else {
                    plain[index++] = matrix[row1 - 1][col1];
                    plain[index++] = matrix[row2 - 1][col2];
                }
            } else {
                plain[index++] = matrix[row1][col2];
                plain[index++] = matrix[row2][col1];
            }
        }
        return String.valueOf(plain);
    }

    /**
     * 根据明文和加密矩阵得到密文
     */
    private String getCipher(char[] plain, char[] key) {
        char[] cipher = new char[plain.length];
        char[][] matrix = constructMatrix(key);
        int index = 0;
        for (int i = 0; i < plain.length - 1; i += 2) {
            int row1, row2, col1, col2;
            String[] pos1, pos2;
            pos1 = getPosition(matrix, plain[i]);
            pos2 = getPosition(matrix, plain[i + 1]);
            if (pos1 == null || pos2 == null) {
                throw new RuntimeException("明文中包含无效字符!!!");
            }
            row1 = Integer.parseInt(pos1[0]);
            col1 = Integer.parseInt(pos1[1]);
            row2 = Integer.parseInt(pos2[0]);
            col2 = Integer.parseInt(pos2[1]);
            if (row1 == row2) {
                // 同一行的情况
                if (col1 == 4) {
                    cipher[index++] = matrix[row1][0];
                    cipher[index++] = matrix[row1][col2 + 1];
                } else if (col2 == 4) {
                    cipher[index++] = matrix[row1][col1 + 1];
                    cipher[index++] = matrix[row1][0];
                } else {
                    cipher[index++] = matrix[row1][col1 + 1];
                    cipher[index++] = matrix[row1][col2 + 1];
                }
            } else if (col1 == col2) {
                //同一列的情况
                if (row1 == 4) {
                    cipher[index++] = matrix[0][col1];
                    cipher[index++] = matrix[row2 + 1][col1];
                } else if (row2 == 4) {
                    cipher[index++] = matrix[row1 + 1][col1];
                    cipher[index++] = matrix[0][col1];
                } else {
                    cipher[index++] = matrix[row1 + 1][col1];
                    cipher[index++] = matrix[row2 + 1][col2];
                }
            } else {
                cipher[index++] = matrix[row1][col2];
                cipher[index++] = matrix[row2][col1];
            }
        }
        return String.valueOf(cipher);
    }

    /**
     * 返回字符在矩阵中的位置
     */
    private String[] getPosition(char[][] matrix, char ch) {
        String[] pos = new String[]{null, null};
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if ((matrix[i][j] == ch) || (matrix[i][j] == 'j' && ch == 'i') || (matrix[i][j] == 'i' && ch == 'j')) {
                    pos[0] = i + "";
                    pos[1] = j + "";
                    return pos;
                }
            }
        }
        return null;
    }

    /**
     * 用密钥构造矩阵
     */
    private char[][] constructMatrix(char[] key) {
        char[][] matrix = new char[5][5];
        CharBuffer buf = CharBuffer.allocate(25);
        buf.append(key[0]);
        // 移除密钥中重复的字符
        for (int i = 1; i < key.length; i++) {
            if (contains(buf.array(), key[i])) {
                buf.append(key[i]);
            }
        }
        // 将字母表中剩余的字符加入
        for (int i = 0; i < 26; i++) {
            char ch = (char) ('a' + i);
            if (contains(buf.array(), ch)) {
                buf.append(ch);
            }
        }
        int index = 0;
        buf.position(0);
        System.out.println("开始构造矩阵...");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (index != buf.length()) {
                    matrix[i][j] = buf.get(index++);
                    System.out.print(matrix[i][j] + "\t");
                }
            }
            System.out.println();
        }
        buf.clear();
        return matrix;
    }

    /**
     * 判断是否包含字符（这里将i和j视为同一个字符）
     */
    private boolean contains(char[] buf, char c) {
        for (char value : buf) {
            if (value == c || (c == 'j' && value == 'i') || (c == 'i' && value == 'j')) return false;
        }
        return true;
    }
}
