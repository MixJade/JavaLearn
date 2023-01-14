package com.forge;

public class TestStatic {
    public static void main(String[] args) {
        TestStatic testStatic=new TestStatic();
        testStatic.setYY();
        testStatic.getYY();
    }
    public void setYY(){
        StaticNum.setName("ddd");
    }
    public void getYY(){
        System.out.println(StaticNum.getName());
    }
}

class StaticNum{
    static String name;

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        StaticNum.name = name;
    }
}