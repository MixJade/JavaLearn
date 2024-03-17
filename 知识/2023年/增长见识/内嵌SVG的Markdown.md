# 内嵌SVG的Markdown

> 时间：2024年3月22日10:54:00

可以在Markdown文件内直接使用SVG标签。这是一个例子：

<svg height="100" width="100">
 <circle cx="50" cy="50" r="40" stroke="black" stroke-width="3" fill="red" />
</svg>

* 这将在md文件渲染时显示一个红色的圆圈。
* 但是并非所有Markdown渲染器都会执行此操作。
* 例如，GitHub的Markdown渲染器由于安全原因不允许内嵌的SVG和其他HTML标记。