# Vue的计算属性

* 经常会出现，后端传来的值是个数字，但前端需要显示的是文本
* 比如性别,后端传(1,0),前端需要显示(男,女)
* 又比如特别多的情况,比如后端传(1,2,3,4,5),前端要显示(一,二,三,是,无)

## 三目运算符

> 适用于判断情况少的,且可以像java一样,三目套三目,来做到判断多个属性

* 模板使用三目运算符

```
<el-table-column align="center" label="性别">
    <template slot-scope="scope">
        {{ String(scope.row.sex) === '0' ? '女' : '男' }}
    </template>
</el-table-column>
```

## 使用方法判断

> 适用于情况较多,用三目运算符太过于繁琐的情况,这个方法是在vue的methods里面

* 模板使用方法判断

```
<el-table-column align="center" label="社团">
    <template slot-scope="society">
        {{societyName(society.row.societyId)}}
    </template>
</el-table-column>
```

* 相应方法(在vue的methods里面)

```
societyName(societyId) {
    switch (societyId) {
        case 1:
            return '散人'
        case 2:
            return '头文字D'
        case 3:
            return '轻音部'
        case 4:
            return '黄金之风'
        case 5:
            return '极东魔术昼寝结社'
        default:
            return '其它'
    }
},
```

## 使用computed计算属性

> 这个不推荐,因为只会计算一次,
> 这个一次是指,哪怕有很多项需要它判断,返回值永远是第一个的结果
> 用于统一模板获取时有其独到之处

* 这个暂时没有弄代码,只是简单的展示一下
* 在模板使用的时候与属性一样,后面是不能加括号的
* 在vue中定义一个computed,这个computed与method同级,都是vue的属性

* 模板语法

```
<template>
    {{societyName02}}
</template>
```

* 在vue中定义,注意,computed与method是同级的

```
computed:{
    societyName02:function (){
        return '好'
    }
},
```