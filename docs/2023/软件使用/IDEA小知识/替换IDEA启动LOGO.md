# 替换IDEA启动LOGO

> 2024年4月25日20:39:39

图片仓库：[ProgrammingVTuberLogos](https://github.com/Aikoyori/ProgrammingVTuberLogos)

* 文件地址

```text
C:\Program Files\JetBrains\IntelliJ IDEA 2022.2.5\lib
```

* 打开app.jar包【推荐用360压缩打开】
* 取出两个文件：
  * idea_logo.png【尺寸640x400】
  * idea_logo@2x.png【尺寸1280x800】
* 然后选择自己需要替换的图片，将尺寸改成与上面两张图片一样
* 然后将自己图片的名字也改成idea_logo.png、idea_logo@2x.png
* 将两张图片、app.jar放在同一个文件夹，运行如下命令：

```sh
jar -uvf app.jar idea_logo.png
jar -uvf app.jar idea_logo@2x.png
```

* 以上两句是更新app.jar中的两张图片
* 然后将app.jar替换回去即可

附言：如果想设置背景图像，可以去【设置】-【外观与行为】-UI选项的【背景图像】中设置。

甚至还能选择平铺方式、只运用与空项目的背景