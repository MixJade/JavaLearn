# Maven多项目打包不使用本地依赖的问题

> 2024-6-26 20:59:57

* Maven多项目打包时，一直试图从服务器获取依赖(该依赖是同时在编译的另一个项目)，虽然这个依赖的项目就在旁边，但它视而不见，自然就报错了。

## 一、问题描述

我有一个项目，目录结构如下：

```text
├─prj-center
│  ├─prj
│    └─pom.xml
│  ├─prj-api
│    └─pom.xml
│  ├─prj-core
│    └─pom.xml
│  ├─prj-model
│    └─pom.xml
└─pom.xml
```

其中，在`prj-center`目录下的`pom.xml`文件定义了它的子模块:

```xml
<project>
	<modules>
		<module>prj-core</module>
		<module>prj-api</module>
		<module>prj-model</module>
		<module>prj</module>
	</modules>
</project>
```

有以下条件：

* `prj-core`依赖`prj-model`
* `prj-model`不存在于远程仓库中，甚至不存在于本地仓库。

这个时候，我点击Maven的`package`插件，发现打不了包。

Maven一直试图从远程仓库获取`prj-model`，但一直获取不到，同时又对它旁边的`prj-model`项目视而不见，就报错了。

## 二、问题分析

Maven 是按照一定的顺序解析依赖的，可能是项目的模块顺序没有配置正确，导致编译时找不到依赖。你可以检查下 POM 文件的`<modules>`标签，确保依赖的模块在被依赖的模块之前。

那么，将`pom.xml`中的子模块顺序改一下就行了

```xml
<project>
	<modules>
        <module>prj-model</module>
		<module>prj-core</module>
		<module>prj-api</module>
		<module>prj</module>
	</modules>
</project>
```

* `prj-model`总是被其它模块依赖，放在前面就行。
* 注意依赖其它模块的模块，要放在其所依赖的模块后面。