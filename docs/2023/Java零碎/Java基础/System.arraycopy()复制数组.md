# System.arraycopy()复制数组

System.arraycopy()是Java中的一个用于复制数组的方法。 

该方法从指定源数组中复制一个数组，将其粘贴至目标数组中。源数组和目标数组都可以是任何数据类型的数组。方法需要指定源数组，源数组的起始位置，目标数组，目标数组的起始位置，以及要复制的元素数。

这是其基本语法：

System.arraycopy(Object src, int srcPos, Object dest, int destPos, int length)

参数：
src - 源数组。
srcPos - 源数组中的起始位置。
dest - 目标数组。
destPos - 目标数据中的起始位置。
length - 要复制的数组元素的数量。

例如，如果你有两个数组，一个是int[] a = {1, 2, 3, 4, 5}，另一个是int[] b = new int[5]，然后你可以用System.arraycopy()方法将数组a复制到数组b，具体如下：

System.arraycopy(a, 0, b, 0, 5);

结果是，数组b现在也变成了{1, 2, 3, 4, 5}。