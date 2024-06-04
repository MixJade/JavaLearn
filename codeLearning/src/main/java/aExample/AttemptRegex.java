package aExample;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 关于正则表达式的使用
 */
public class AttemptRegex {
    public static void main(String[] args) {
        System.out.println("------Begin------");
        concealPrefixSuffix();
        System.out.println("----------");
        checkTelephone("13882244623");
        checkTelephone("123456789");
        System.out.println("----------");
        checkEmail("1940443155@qq.com");
        checkEmail("iLikeMixJade@163.com");
        checkEmail("123456789");
        System.out.println("----------");
        divisionAndReplace();
        System.out.println("---End----");
    }

    private static void concealPrefixSuffix() {
        String str = "My name is MixJade end,i like to eat orange,and i have a dog and a cat,the dog name is SuperMan,and the cat named Kitty";
        Pattern p = Pattern.compile("(?<=name\\sis\\s)[a-zA-Z]+|(?<=named\\s)[a-zA-Z]+");
        Matcher m = p.matcher(str);
        while (m.find()) {
            System.out.println(m.group());
        }
        System.out.println(str);
    }

    private static void checkTelephone(String telephone) {
        System.out.print("输入手机号：" + telephone + " ,");
        //正确格式: 13882244623
        if (telephone.matches("1[3-9]\\d{9}")) {
            System.out.println(" You are right!");
        } else {
            System.out.println(" You are fault");
        }
    }

    private static void checkEmail(String email) {
        System.out.print("输入邮箱：" + email + " ,");
        //正确格式: 1940443155@qq.com
        //matches 是看能否完全将字符串与正则表达式匹配，能完全匹配返回true，正如下方把com改成co会导致返回false
        if (email.matches("\\w{5,30}@[a-zA-z\\d]{2,5}\\.com")) {
            System.out.println(" You are right!");
        } else {
            System.out.println(" You are fault");
        }
    }

    private static void divisionAndReplace() {
        String str02 = "小龙女$4#3dx@fchgnf甄志丙77yhb6g欧阳锋97976jn杨过";
        //split是匹配要去掉的字符串，所以这里用小写的w来匹配数字与字母，再通过联合来去掉特殊字符
        String[] arr02 = str02.split("[\\w@#$%]+");
        for (String s : arr02) {
            System.out.print(s + " ");
        }
        //替换函数:replaceAll(正则表达式,要替换的值)
        String name02 = str02.replaceAll("[\\w@#$%]+", " ");
        System.out.println("\n" + name02);
    }
}
