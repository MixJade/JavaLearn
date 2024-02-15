# Git永久忽略特定文件的更改

> 2024年3月8日15:18:29

如果你希望永久性地忽略某个特定文件的修改，你可以使用`git update-index --assume-unchanged <file>`。这样一来，git将会忽视你对这个文件做的任何改动。当你想让git再次跟踪该文件的修改时,你可以使用 `git update-index --no-assume-unchanged <file>`撤销这个设置。 

例如：

```bash
git update-index --assume-unchanged file3.txt
```

这样就可以在执行`git add .`的时候忽略掉`file3.txt`的修改。

* 甚至这样IDEA也不会提交这个更改，有的时候能够提交，但真提交了会报错