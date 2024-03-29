# tree列出子目录

* 直接在window的终端输入命令`tree`即可

* 输入`tree /?`可以查看帮助

* ```text
  tree的参数
  
  /F 递归列出所有文件
  /A 只查看文件夹, 忽略文件
  ```

* ```text
  tree的两个用法
  
  tree /F > tree.txt
  生成的文件目录树形结构写入到 tree.txt
  
  tree /A > tree.txt
  查看文件夹,忽略文件
  ```