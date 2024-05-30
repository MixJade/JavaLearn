# Github引用自己图片

> 2024年4月25日22:55:18

可以利用`GitHub Pages`实现通过https来引用自己上传的图片。以下是操作步骤：

1. 新建一个repo
2. 点击【Settings】-左侧【Pages】
3. 找到【GitHub Pages】
4. 在【Source】下的【branch】选择main 
5. 点击【branch】旁的【Save】按钮
6. 把需要的图片上传到你的repo，然后你可以通过`https://你的用户名.github.io/repo的名字/你的图片路径`来访问它们。

总的说来，只要像挂载Web服务器上的其他资源一样，图片上传到GitHub之后就等同于上传到GitHub服务器上，然后可以通过特定的URL在Web上访问、引用这些资源。

注意：GitHub并没有提供一个真正的图片托管服务，其实主要还是面向代码仓库的，如果图片过多过大，频繁访问，可能GitHub会提醒你。对于一些静态资源，我们推荐使用专业的CDN或者静态资源托管服务。