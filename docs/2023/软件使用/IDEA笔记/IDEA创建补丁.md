# IDEA创建补丁

> 2024-08-29 15:53:43

* 即像Git一样通过一个文件快速同步代码

参考教程：[IDEA 如何制作代码补丁-CSDN博客](https://blog.csdn.net/zhangdaiscott/article/details/132334082)

## 一、创建补丁

* 在变更列表上右键【从本地更改创建补丁】或【创建补丁】
* 不要改变导出选项的基路径，只修改导出编码为【UTF-8】，导出即可

注意：变更列表包括了：

1. Git提交记录
2. 本地未提交的更改列表
3. 本地历史记录

## 二、导入补丁

选择菜单栏的【Git】-【补丁文件】-【应用补丁】