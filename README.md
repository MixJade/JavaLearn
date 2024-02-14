# HELLO_WORLD

> 学习Java过程的所产生的代码，上传git留作纪念

* 如果用IDEA打开，记得将JavaLearn.iml.bak文件去掉后面的bak
* 然后放在.idea文件夹里面
* 这样就不会导致项目索引乱了
* （即：打开JavaLearn的文件夹后，如果不排除下面的项目，用IDEA打开下面的项目会导致找不到类）
* （如果在没有排除的情况下打开JavaLearn了，在打开子项目的时候，【文件】-【清除缓存】）

---

| 文件夹            | 简介                     |
|:---------------|:-----------------------|
| codeLearning   | 学习JavaSE时的代码           |
| experiment     | 一些小Demo                |
| fly_bird       | 飞翔的小鸟                  |
| my_school      | 学校Java代码               |
| studentsManage | JavaWeb的具象,Jsp+MyBatis |
| studentManage2 | 第一代的升级版，主要是异步请求        |

---

git五连

```bash
git init
git remote add origin git@github.com:MixJade/JavaLearn.git
git add .
git commit -m "first commit"
git branch -M main
git push -u origin main
```