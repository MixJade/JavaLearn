# README

> 2024年8月17日16:03:28

* 依赖于java17

## 一、运行打包后的jar

* 本项目已配置了自动导出依赖到jar包旁的lib目录下，只需要打包然后执行以下命令

```bash
java --module-path "lib" --add-modules javafx.controls,javafx.fxml -jar demoFx-1.0-SNAPSHOT.jar
```

* 这是指定模块的意思
* 上述命令结构如下

```bash
java --module-path "<your javafx lib path>" --add-modules javafx.controls,javafx.fxml -jar <your jar file>
```