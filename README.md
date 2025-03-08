# HELLO_WORLD

> 学习Java过程的所产生的代码，上传git留作纪念

* 如果用IDEA直接打开JavaLearn，会导致项目索引紊乱。
* （如果用IDEA打开JavaLearn了，在打开子项目的时候，【文件】-【清除缓存】）

---

## Git样例

```bash
git init
git remote add origin git@github.com:MixJade/JavaLearn.git
git add .
git commit -m "first commit"
git branch -M main
git push -u origin main
```

## 目录结构

```text
├─bootLearn	【使用了SringBoot的项目】
│  ├─bootDemo	【关于SpringBoot与Mybatis-plus学习】
│  ├─chat		【使用WebSocket实现的简易在线聊天】
│  ├─droolsDemo	【流程引擎droolsDemo的学习】
│  ├─mySecurity	【SpringSecurity的小Demo】
│  └─payment	【记账项目的demo】
├─codeLearning	【学习JavaSE时的代码】
├─docs	【学习笔记】
│  ├─2022	【大学时学习笔记】
│  └─2023	【工作后学习笔记】
├─guiLearn	【JavaGui项目】
│  ├─demoFx	【JavaFx样例】
│  ├─downM3u8	【多线程下载电影】
│  ├─fly_bird	【飞翔的小鸟】
│  ├─myPwd		【我的加密工具】
│  ├─snakeGame	【贪吃蛇】
│  └─swingTes	【JavaSwing样例】
├─libLearn	【对一些库的使用(非web/gui项目)】
│  ├─csvAndXml	【专门处理csv+xml的库】
│  ├─mybatisMaven	【mybatis的各种学习】
│  └─TesFreeMarker	【FreeMarker模板学习】
├─my_school	【学校Java代码】
└─webLearn	【学习JavaWeb时的代码】
    ├─ajaxAttempt		【Ajax尝试，包括了vue与axios，还有基础的myBatis与注册页面】
    ├─springMVC			【springMVC的学习】
    ├─springTrain		【关于Spring的学习，包括了aop、ioc、事务传播】
    ├─studentManage2	【JavaWeb2，异步请求+vue2+html+MyBatis】
    ├─studentsManage	【JavaWeb的具象,Jsp+MyBatis】
    ├─studentsMVC3		【第一代的升级版，主要是异步请求】
    └─webMaven			【【JavaWeb的各种学习，包括jsp、cookie、请求转发、拦截器】
```

