# DBeaver使用

> 2024年3月20日15:24:29

免费的、勉强能用的数据库连接工具，对比其它工具，省去破解的时间精力。

* DBeaver下载地址：[Download | DBeaver Community](https://dbeaver.io/download/)

## 一、设置连接驱动

### 1.1 添加本地文件

> 但这种没有太大意义，因为要添加的文件很多

1. 打开【连接设置】，该界面可以通过【新建连接】，或对已有连接【编辑连接】来打开
2. 在【连接设置】左下角有一个按钮：【编辑驱动设置】，点击打开新界面
3. 选项卡中选【库】-【添加文件】，添加你自己下载的驱动jar包。

### 1.2 设置Maven下载镜像

> 当前DBeaver版本：24.0.0

* 【窗口】-【首选项】-【驱动】-【Maven】
* 点击【添加】按钮，输入阿里云仓库链接：https://maven.aliyun.com/repository/public
* 然后就会自动添加，接着把阿里云的Maven放在最上面就行。

## 二、连接数据库

> 连接数据库有两种方式，这里推荐JDBC

这里的连接是连接Oracle数据库的流程，MySQL的连接也差不多的其实。

### 2.1 通过JDBC进行连接

1. 【新建连接】-- 一直操作到【连接设置】界面

2. 将【连接类型】从【基本】切换到【自定义】

3. 在【JDBC URL Template】框中，输入你的JDBC链接，比如：

   ```url
   jdbc:mysql://localhost:3306/play
   ```

4. 在下面的【用户名】、【密码】输入对应数据库的用户名密码即可。

5. 然后点击【测试连接】,连接成功点击【完成】按钮即可。

6. 补充：可能数据库导航的选项卡显示的信息过长(因为会带有jdbc前缀)，这时可以点击【编辑连接】，然后【主要】选项卡的【连接类型】，从【自定义】切换到【基本】，然后保存即可。

### 2.2 通过基本连接(不推荐)

1. 【新建连接】-- 一直操作到【连接设置】界面，这时【连接类型】默认是【基本】
2. 在【主机】输入ip地址，注意不带端口号，如`127.0.0.1`
3. 在【端口】输入端口号，如`3306`
4. 在【数据库】输入数据库名称，如`play`
5. 在在下面的【用户名】、【密码】输入用户名密码，点击【测试链接】即可。

## 三、部分常用功能

1. 【SQL编辑器】：将鼠标放在**自己的数据库**上，然后右键，选择【SQL编辑器】,一般这样操作数据库。
2. 【SQL控制台】: 打开【SQL编辑器】时可以选择【SQL控制台】，用于临时操作数据库。
3. 【在SQL控制台读取数据】：将鼠标放在**具体的表**上，右键选择【在SQL控制台读取数据】，一般这样临时操作数据库。
4. 【转储数据库】:将鼠标放在**具体的表**上，右键选择【工具】-【转储数据库】
5. 【导入/导出】：选择具体的表、注意导出为csv的时候可以设置具体的编码格式。
6. 【复制为SQL】:在表中的某一行，鼠标右键-【高级复制】-【复制为SQL】，即可复制为insert语句。
7. 【SQL美化】：`ctrl + alt + F` ,或在菜单栏“编辑“中格式化SQL

## 四、快捷键

* `ctrl + enter` 执行单条sql
* `alt+X`执行多条选中sql
* `ctrl + shift + F` 对sql语句进行格式化
* `ctrl + d` 删除当前行
* `alt + ↑` 向上选定一条sql语句
* `alt + ↓` 向下选定一条sql语句
* `ctrl + /` 行注释
* `ctrl + shift+ / `块注释
* `ctrl + f `查找、替换
* `ctrl + space` sql提示(如果写了from table后也会自动提示field)
* `ctrl + shift + U` 将选定的sql转换成大写字母
* `ctrl + shift + L` 将选定的sql转换成小写字母

## 五、(补充)迁移到新电脑

* 我想将我的Dbeaver给迁移到一台不能联网的电脑上
* 但需要保留我的驱动包、数据库连接信息。
* 这个时候需要将自己的本地软件、本地软件数据打包，迁移到新电脑上即可

### 5.1 迁移本地软件

* 打开自己安装Dbeaver的目录，这个是当初安装时自己指定的，比如我的是

```url
D:\DBeaver
```

* 这个从桌面图标点击**"打开文件所在位置"**即可。
* 然后整个打包压缩

### 5.2 迁移本地数据

* 打开文件夹

```url
%userprofile%\AppData\Roaming\DBeaverData
```

* 整个打包即可。然后迁移到新电脑上对应的位置，即安装完成