:: 第一步：定义Maven仓库根路径变量
set MAVEN_REP=D:\apache-maven-3.8.6\mavRepository
:: 第二步：引用变量执行Java命令
java --module-path "%MAVEN_REP%\org\openjfx\javafx-controls\17.0.2;%MAVEN_REP%\org\openjfx\javafx-graphics\17.0.2;%MAVEN_REP%\org\openjfx\javafx-base\17.0.2;%MAVEN_REP%\org\openjfx\javafx-fxml\17.0.2" ^
--add-modules javafx.controls,javafx.fxml ^
-cp demoFx-1.0-SNAPSHOT.jar ^
mix.jade.StartApp
:: 可选：执行完暂停，方便查看报错信息
pause