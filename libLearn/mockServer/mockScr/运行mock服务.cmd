@echo off
chcp 65001 >nul
cd ..
:: 创建target/classes目录
md target\classes 2>nul
:: 编译java 文件
javac -cp . -d target\classes -encoding UTF-8 src\main\java\*.java src\main\java\mock\*.java
:: 运行demo
java -Dfile.encoding=UTF-8 -classpath target\classes MockDemo
pause