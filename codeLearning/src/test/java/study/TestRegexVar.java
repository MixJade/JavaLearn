package study;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 测试正则表达式提取字符串变量
 */
public class TestRegexVar {
    public static void main(String[] args) {
        MyVar[] myVars = new MyVar[]{
                new MyVar("var1", "1"),
                new MyVar("var2", "3"),
        };
        String rule1 = "{var1}==1";
        String rule2 = "{var1}==1&&{var2}==2";
        String rule3 = "{var1}==1||{var2}==2";
        // 校验1
        boolean b1 = checkRule(rule1, myVars);
        System.out.println("rule1" + (b1 ? "成功" : "失败"));
        // 校验2
        boolean b2 = checkRule(rule2, myVars);
        System.out.println("rule2" + (b2 ? "成功" : "失败"));
        // 校验3
        boolean b3 = checkRule(rule3, myVars);
        System.out.println("rule3" + (b3 ? "成功" : "失败"));
    }

    private static boolean checkRule(String ruleStr, MyVar[] myVars) {
        if (ruleStr == null || "".equals(ruleStr)) return false;
        // 判断是不是复合规则
        int orIndex = ruleStr.indexOf("||");
        if (orIndex == -1) {
            int andIndex = ruleStr.indexOf("&&");
            if (andIndex == -1) {
                System.out.println("是单个规则");
                return checkOneRule(ruleStr, myVars);
            } else {
                System.out.println("是复合【和】规则");
                boolean oneRule1 = checkOneRule(ruleStr.substring(0, andIndex), myVars);
                boolean oneRule2 = checkOneRule(ruleStr.substring(andIndex + 2), myVars);
                return oneRule1 && oneRule2;
            }
        } else {
            System.out.println("是复合【或】规则");
            boolean oneRule1 = checkOneRule(ruleStr.substring(0, orIndex), myVars);
            boolean oneRule2 = checkOneRule(ruleStr.substring(orIndex + 2), myVars);
            return oneRule1 || oneRule2;
        }
    }

    private static boolean checkOneRule(String ruleStr, MyVar[] myVars) {
        // 校验规则是否正确
        Pattern pattern = Pattern.compile("\\{(.*?)}==([a-zA-Z0-9]*)");
        Matcher matcher = pattern.matcher(ruleStr);
        if (matcher.find()) {
            for (MyVar myVar : myVars) {
                if (myVar.name().equals(matcher.group(1))) {
                    return myVar.strVal().equals(matcher.group(2));
                }
            }
        }
        return false;
    }


    record MyVar(String name, String strVal) {
    }
}
