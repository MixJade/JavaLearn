# VUE学习笔记

---



## ref与setup语法糖

先新建一个组件:MyHello.vue

```vue
<template>
  <h1>{{ role }}</h1>
  <h2>你好世界</h2>
  <h2>当前功德：{{ count }}</h2>
  <div>
    <span>当前台词：{{ man.line }}</span>
    <br>
    <span>当前薪水：{{ man.money }}</span>
  </div>
  <button @click="update">敲木鱼</button>
</template>

<script setup lang="ts">
defineProps<{
  role: string
}>()
import {ref} from "vue";
// 定义响应式数据 ref对象，一般设置基本类型的数据
const count = ref(1)
// 也可以应用与对象类型的数据，但这内部是调用reactive实现
const man = ref({
  line: "你好",
  money: 2500
})
const lines = ["南无", "阿唎耶", "婆卢羯帝", "烁钵啰耶", "菩提萨埵婆耶", "摩诃萨埵婆耶"]
// 更新响应式数据的函数
const update = () => {
  count.value = count.value + 1
  man.value.line = lines[count.value % (lines.length - 1)]
  man.value.money += 50
}
</script>

<style scoped>
h2 {
  color: red;
}

button {
  height: 50px;
  width: 100px;
  color: whitesmoke;
  background-color: #725f0d;
  border: 2px solid #057915;
  border-radius: 10px;
  font-size: large;
}
</style>
```

然后在APP.vue这样引入

```vue
<script setup lang="ts">
import MyHello from './components/MyHello.vue'
</script>
```

```vue
<template>
  <main>
    <MyHello role="你好"/>
  </main>
</template>
```

---



## reactive函数

> 只能用于对象类型，不要用于基本类型
>
> 对象取值比ref方便

```vue
<script setup lang="ts">
defineProps<{
  role: string
}>()
import {reactive, ref} from "vue";
// 定义响应式数据 ref对象，一般设置基本类型的数据
const count = ref(1)
// 调用reactive，取值比较方便
const man = reactive({
  line: "你好",
  money: 2500
})
const lines = reactive(["南无", "阿唎耶", "婆卢羯帝", "烁钵啰耶", "菩提萨埵婆耶", "摩诃萨埵婆耶"])
// 更新响应式数据的函数
const update = () => {
  count.value = count.value + 1
  // 取值更加方便
  man.line = lines[count.value % (lines.length - 1)]
  man.money += 50
}
// 根据索引，更改数组的某一元素，reactive的特点
const updateLine = () => {
  const index=count.value % (lines.length - 1)
  lines[index]="这个台词被更改了"
  man.line=lines[index]
}
</script>
```

---



## setup注意：

由于setup的创建时间早于beforeCreate，故，setup中不能使用this

---



## emit自定义事件的使用

- 新建hello2.vue
- 使用按钮以及test函数是为了给这个事件一个触发方式
- 不然直接emit就会导致事件立即触发

```vue
<template>
  <p>页面大小:{{ pageImpl.size }}，当前页码:{{ pageImpl.num }}</p>
  <button @click="test">翻页(子组件)</button>
</template>

<script setup lang="ts">
import {reactive} from "vue";
// 定义事件与返回参数
const emit = defineEmits<{
  (e: "hello", value: Page): void
}>()

// 定义返回参数的接口
interface Page {
  size: number,
  num: number
}

// 实际返回参数
const pageImpl = reactive({
  size: 7,
  num: 1
})
// 触发返回参数的事件
const test = () => {
  pageImpl.num++
  emit("hello", pageImpl)
}
</script>
```

- 在父组件中应用事件，定义函数，emit会给这个函数返回值


```vue
<script setup lang="ts">
import Hello2 from "@/components/Hello2.vue";
import Page from "@/components/Hello2.vue";

function showHelloMsg(value: Page): void {
  alert(`来自父组件：收到参数:${value.size}，${value.num}`)
}
</script>

<template>
  <main>
    <!--    触发emit事件，获取子组件传值-->
    <Hello2 @hello="showHelloMsg"></Hello2>
  </main>
</template>
```

---



## 计算属性的使用

与vue2不同，最终使用的不是函数，而是使用computed函数返回的字段。

后续可以有可修改的计算属性，要在computed区别get，set(value)方法，现在默认的是只有get方法

```vue
<template>
  <h1>{{ role }}</h1>
  <span>当前薪水：{{ testComputed }}</span>
</template>

<script setup lang="ts">
defineProps<{
  role: string
}>()
import {computed, reactive} from "vue";
// 调用reactive，取值比较方便
const man = reactive({
  line: "你好",
  money: 2500
})
// 计算属性测试
const testComputed=computed(()=>man.money+"元")
</script>
```

---



## watch侦听器

* 一般在这里执行一些异步方法，如注册输入用户名时，向后端查询是否有重名
* 注意，点击事件里可以直接对数据进行操作，但是只能使用`++`或者`+=`、`=`这种赋值运算符

### 监视单个数据

```vue
<template>
  <p>当前数据：{{ watchNum }}</p>
  <br>
  <p>监视器输出：{{ output }}</p>
  <br>
  <button @click="watchNum++">测试监视器</button>
</template>

<script setup lang="ts">
import {ref, watch} from "vue";

const watchNum = ref(1)
const output = ref("暂无")
// 监视器的使用
watch(watchNum, (newValue, oldValue) => {
  output.value = `新的数据：${newValue}，旧的数据:${oldValue}`
})
</script>

<style scoped>

</style>
```

引用，注意文件引用记得加vue后缀：

```vue
<template>
  <HowWatch/>
</template>

<script setup>
import HowWatch from "@/components/HowWatch.vue";
</script>
```

### 监视多个数据

* 其实可以同时定义多个watch，犯不着挤在一起

* 但集中在一起的好处是，比如联合搜索，可以将条件放在一起
* 注意：这时监视器返回的是数组，数组内的元素即监视的元素

```vue
<template>
  <h2>测试监视器</h2>
  <p>当前数据：{{ watchNum }}</p>
  <p>被监视的另外一个数据：{{ watchStr }}</p>
  <br>
  <p>监视器输出：{{ output }}</p>
  <br>
  <button @click="watchNum++">数字增加</button>
  <button @click="watchStr+='是'">文字增加</button>
</template>

<script setup lang="ts">
import {ref, watch} from "vue";

const watchNum = ref(1)
const watchStr = ref("是")
const output = ref("暂无")
// 监视器的使用，这次监视两个
watch([watchNum,watchStr], (newValue, oldValue) => {
  output.value = `新的数据：${newValue}，旧的数据:${oldValue}`
})
</script>

<style scoped>

</style>
```

### 侦听器配置

监视器的第三个参数即为监视器配置，目前有

* 立即监视：监视器一创建就执行监视任务，`immediate: true`
* 深层监视：监视引用数据类型`deep: true`

```Js
watch([watchNum,watchStr], (newValue, oldValue) => {
  output.value = `新的数据：${newValue}，旧的数据:${oldValue}`
},{ immediate: true })
```

### 监视reactive

bug:

* 不能够只监视对象中的某个属性，如果非要如此，自己定义一个返回函数
* 监视器的深层监视，有bug，旧的数据跟新的一样
* reactive定义的数据，强制开启深度监视，使用deep:false都关不掉

```vue
<template>
  <h2>测试监视器</h2>
  <p>姓名：{{ person.name }}</p>
  <p>年龄：{{ person.age }}</p>
  <br>
  <p>监视器输出：{{ output }}</p>
  <br>
  <button @click="person.age++">数字增加</button>
  <button @click="person.name+='i'">文字增加</button>
</template>

<script setup lang="ts">
import {reactive, ref, watch} from "vue";

const person = reactive({
  name: "li",
  age: 0
})
const output = ref("暂无")
// 监视器的深层监视，有bug，旧的数据跟新的一样
// 注意：reactive定义的数据，强制开启深度监视，使用deep:false都关不掉
watch(person, (newValue, oldValue) => {
  output.value = `新的数据：${newValue.age}${newValue.name}，旧的数据:${oldValue?.age},${oldValue?.name}`
},{immediate:true})
</script>
```

只监视对象的某一个属性，这样可以看到旧数据：

```vue
<template>
  <h2>测试监视器</h2>
  <p>姓名：{{ person.name }}</p>
  <p>年龄：{{ person.age }}</p>
  <br>
  <p>监视器输出：{{ output }}</p>
  <br>
  <button @click="person.age++">数字增加</button>
  <button @click="person.name+='i'">文字增加</button>
</template>

<script setup lang="ts">
import {reactive, ref, watch} from "vue";

const person = reactive({
  name: "li",
  age: 0
})
const output = ref("暂无")
// 只监视对象的一个属性，要使用返回函数
// 这里可以看到旧的数据
watch(()=>person.age, (newValue, oldValue) => {
  output.value = `新的数据：${newValue}${person.name}，旧的数据:${oldValue},${person?.name}`
}, {immediate: true})
</script>

<style scoped>

</style>
```

* 监视嵌套对象的某一属性时，与上面一样，不用deep:true

* 但是监视嵌套的对象时，情况变了，必须加上deep:true，不然监视器认为监视对象没有变化，无法触发

```vue
<template>
  <h2>测试监视器</h2>
  <p>姓名：{{ person.name }}</p>
  <p>薪水：{{ person.job.j1.salary }}k</p>
  <br>
  <p>监视器输出：{{ output }}</p>
  <br>
  <button @click="person.job.j1.salary++">数字增加</button>
  <button @click="person.name+='i'">文字增加</button>
</template>

<script setup lang="ts">
import {reactive, ref, watch} from "vue";

const person = reactive({
  name: "li",
  job: {
    j1:{
    salary: 20
    }
  }
})
const output = ref("暂无")
// 监视对象中的嵌套对象，必须开启deep:true
watch(() => person.job.j1, (newValue, oldValue) => {
  output.value = `新的数据：${newValue.salary}${person.name}，旧的数据:${oldValue?.salary},${person?.name}`
}, {immediate: true,deep:true})
</script>

<style scoped>

</style>
```

---



## watchEffect侦听器

相比于watch，可以不必声明所监听的对象，只要在它的函数体中调用，它就会默认监视。它的第一个参数就是回调函数，第二个参数可以配置监听器的配置。

```vue
<template>
  <h2>测试监视器</h2>
  <p>姓名：{{ person.name }}</p>
  <p>薪水：{{ person.job.j1.salary }}k</p>
  <br>
  <p>监视器2输出：{{ output2 }}</p>
  <br>
  <button @click="person.job.j1.salary++">数字增加</button>
  <button @click="person.name+='i'">文字增加</button>
</template>

<script setup lang="ts">
import {reactive, ref, watchEffect} from "vue";

const person = reactive({
  name: "li",
  job: {
    j1:{
    salary: 20
    }
  }
})
// watchEffect，监视对象与回调一样的watch
// 不必显式声明监视对象
const output2=ref("暂无")
watchEffect(()=>{
  output2.value=`薪水>${person.job.j1.salary}`
})
</script>
```

它也对嵌套对象无能为力，但这个可以避免，只要调用对应的值就好

```js
const output2=ref("暂无")
let a=1
watchEffect(()=>{
  const x=person.job.j1 // 无能为力，但调用里面的值就行
  a++
  output2.value=`薪水>${a}`
})
```

---



## 生命周期

> 暂时只展示setup里面的钩子，这些钩子可以移到setup外面，只要去掉前面的on就行

```vue
<template>
  <h3>生命周期{{a}}</h3>
  <button @click="a++">更新</button>
</template>

<script setup lang="ts">
// 使用生命周期钩子
import {onBeforeMount, onBeforeUnmount, onBeforeUpdate, onMounted, onUnmounted, onUpdated, ref} from "vue";
// 下面注释的是配置项写法，要在setup外面生效
/*beforeCreate() {
  console.log('---beforeCreate---')
},
created() {
  console.log('---created---')
},*/
onBeforeMount(() => {
  console.log('---beforeMount，挂载之前---')
})
onMounted(() => {
  console.log('---mounted，挂载完成---')
})
onBeforeUpdate(() => {
  console.log('---beforeUpdate，更新前---')
})
onUpdated(() => {
  console.log('---updated，更新完成---')
})
onBeforeUnmount(() => {
  console.log('---beforeUnmount，卸载前---')
})
onUnmounted(() => {
  console.log('---unmounted，卸载完成---')
})
const a=ref(1)
</script>

<style scoped>

</style>
```

引用:

```vue
<template>
  <h2>测试生命周期</h2>
  <br>
  <button @click="isShow=!isShow">测试生命周期</button>
  <LifeTerm v-if="isShow"/>
</template>

<script setup>
import {ref} from "vue";
import LifeTerm from "@/components/LifeTerm.vue";

const isShow = ref(true)
</script>
```

---

## 自定义hooks

- 可以将一段逻辑封装为单独的文件，可以引入vue的生命周期使之可以自动加载卸载。
- 将这段逻辑作为一个函数进行默认暴露 
- 从vue中抽离的钩子函数，注意是setup的钩子函数，得在setup里面用

* usePopText.ts

```ts
import {onMounted,onBeforeUnmount} from "vue";

export default ()=>{
    const body = document.body;// 卡哇伊的弹出文字
    const textArr = ["乐观", "❤", "积极", "向上", "自由", "正能量", "(*^▽^*)", "元气满满", "开心", "快乐", "善良", "可爱", "|ू･ω･` )", "卡哇伊", "(ﾟ▽ﾟ*) ", "ヽ(￣▽￣)ﾉ"];
    const popText = (ev: MouseEvent): void => {
        const baseX = ev.pageX;
        const baseY = ev.pageY;
        const new_span = document.createElement('span');
        new_span.innerText = textArr[~~(Math.random() * textArr.length)]
        new_span.style.position = 'fixed';
        new_span.style.left = baseX - 10 + 'px';
        new_span.style.top = baseY + 'px';
        new_span.style.color = 'RGB(255,0,0)';
        new_span.style.userSelect = 'none';
        body.appendChild(new_span);
        const timer = window.setInterval(() => {
            new_span.style.top = parseInt(window.getComputedStyle(new_span, null)['top']) - 2 + 'px';
            new_span.style.opacity = String(Number(window.getComputedStyle(new_span, null)['opacity']) - 0.02);
        }, 30);
        window.setTimeout(() => {
            window.clearInterval(timer);
            body.removeChild(new_span);
        }, 1000);
    }
    onMounted(() => {
        document.addEventListener('click', popText)
    })
    onBeforeUnmount(() => {
        document.removeEventListener('click', popText)
    })
}
```

* 然后在vue的setup中，这样使用
* 然后就可以随着组件自动加载与卸载

```ts
// 使用引用函数
import usePopText from "@/hook/usePopText";
usePopText()
```

---



## toRef的使用

> 将某一对象的属性转变为ref对象，用于多层嵌套时的简洁引用。

这样对老东家进行操作，toRef的对象会变

```vue
<template>
  <p>toRef的测试:{{salary}}</p>
  <p>toRef的测试2:{{name}}</p>
  <button @click="person.job.j1.salary++">数字增加</button>
  <button @click="person.name+='i'">文字增加</button>
</template>

<script setup lang="ts">
import {reactive, toRef} from "vue";

const person = reactive({
  name: "li",
  job: {
    j1:{
    salary: 20
    }
  }
})
// toRef测试，不用setup语法糖可以直接return而不需const定义
const name=toRef(person,'name')
const salary=toRef(person.job.j1,'salary')
</script>

<style scoped>

</style>
```

事实上，toRef并不是重新拷贝定义一个对象，对toRef的对象进行操作，它的老东家一样在变

```vue
<template>
  <p>姓名：{{ person.name }}</p>
  <p>薪水：{{ person.job.j1.salary }}k</p>
  <p>toRef的测试:{{salary}}</p>
  <p>toRef的测试2:{{name}}</p>
  <button @click="salary++">数字增加</button>
  <button @click="name+='i'">文字增加</button>
</template>

<script setup lang="ts">
import {reactive, toRef} from "vue";

const person = reactive({
  name: "li",
  job: {
    j1:{
    salary: 20
    }
  }
})
// toRef测试
const name=toRef(person,'name')
const salary=toRef(person.job.j1,'salary')
</script>
```

---

## toRefs使用

与toRef相比，可以转换一整个对象，配合上ES6的`...展开`运算符，可以快速拆封

```vue
<template>
  <p>姓名：{{ name }}</p>
  <p>薪水：{{ job.j1.salary }}k</p>
  <button @click="job.j1.salary++">数字增加</button>
  <button @click="name+='i'">文字增加</button>
</template>

<script lang="ts">
import {reactive, toRefs} from "vue";

export default {
  "setup"() {
    const person = reactive({
      name: "li",
      job: {
        j1: {
          salary: 20
        }
      }
    })
    // toRefs测试，前面三个点是ES6的快速拆封
    return {
      ...toRefs(person)
    }
  }
}

</script>
```

---

## readonly使用

可以用readonly将对象保护起来，能看不能改

```vue
<template>
  <p>姓名：{{ name }}</p>
  <p>薪水：{{ job.j1.salary }}k</p>
  <button @click="name+='i'">文字增加</button>
</template>

<script lang="ts">
import {reactive, readonly, toRefs} from "vue";

export default {
  "setup"() {
    const person = readonly(reactive({
      name: "li",
      job: {
        j1: {
          salary: 20
        }
      }
    }))
    // toRefs测试
    return {
      ...toRefs(person)
    }
  }
}

</script>

<style scoped>

</style>
```

---

## shallow函数

比如：

```
shallowRef()
shallowReactive()
shallowReadonly()
```

都是只处理对象的第一层数据，相当于`deep:false`

---

## 路由

### 基本使用

```shell
npm i -D vue-router@4
```

接着在main.ts中定义应用路由

```ts
import {createApp} from 'vue'
import App from './App.vue'
import './assets/main.css'
// 路由
import router from './router'

const app = createApp(App)
app.use(router)
app.mount('#app')
```

然后新建文件夹`router`，再在下面新建文件`index.ts`

```ts
import {createRouter, createWebHashHistory} from "vue-router"

const routes = [
    {
        path: '/',
        component: () => import('@/views/HELLO_01.vue')
    },
    {
        path: '/test1',
        name: "test1",
        component: () => import('@/views/HELLO_02.vue')
    }
]
export const router = createRouter({
    history: createWebHashHistory(),
    routes: routes
})

export default router
```

接着新建文件夹views，专门放路由组件

里面建立两个组件`HELLO_02.vue`与`HELLO_01.vue`

然后在App.vue中使用路由

```vue
<script setup lang="ts">
import {useRouter} from "vue-router";

const router = useRouter()

</script>

<template>
  <div class="nav">
    <ul>
      <li @click="router.push('/test1')">测试路由1</li>
      <li @click="router.push('/')">测试路由2</li>
      <li>jj</li>
    </ul>
  </div>
  <router-view></router-view>
</template>

<style scoped>
.nav {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 50px;
  position: fixed;
  width: 100%;
  top:0;
  left: 0;
  background-color: #282828;
}

ul {
  position: relative;
  display: flex;
}

ul li {
  list-style: none;
  width: 80px;
  color: whitesmoke;
  line-height: 40px;
  text-align: center;
  cursor: default;
}

</style>
```

### 子路由

* 虽然子 路由可以直接写在Index.ts中，但这太难看了
* 建议抽离一个文件，以数组的形式记录
* 如果能够直接把父路由给抽离就更好了

* index.ts

```ts
import {createRouter, createWebHashHistory} from "vue-router"
import test1_children from "@/router/test1_children";

const routes = [
    {
        path: '/',
        component: () => import('@/views/HELLO_01.vue')
    },
    {
        path: '/test1',
        component: () => import('@/views/HELLO_02.vue'),
        children: [...test1_children],
    }
]
export const router = createRouter({
    history: createWebHashHistory(),
    routes: routes
})

export default router
```

* test1_children.ts

```ts
/**
 * 路由2的子路由，
 * 父路径：/test1
 * 父文件：HELLO_02.vue
 */
const test1_children = [
    {
        path: 'life',
        component: () => import('@/views/LifeTerm.vue')
    }, {
        path: 'prop/:role',
        prop: true,
        component: () => import('@/views/MyHello.vue')
    }, {
        path: 'query',
        component: () => import('@/views/QueryTest.vue')
    }, {
        path: 'query02',
        component: () => import('@/views/QueryTest02.vue')
    },
]

export default test1_children
```

* 使用(在其父路由的组件中使用)
* 父组件要实现`<router-view></router-view>`进行显示
* 直接在最外面调用子组件会连同父组件一起显示

```vue
<template>
  <h1>你好世界</h1>
  <ul>
    <li><router-link to="/test1/life">子路由:生命周期</router-link></li>
    <li><router-link to="/test1/prop/role=999">子路由:prop传递</router-link></li>
    <li><router-link to="/test1/query?msg1=收到&msg2=十点多&msg3=89">子路由:参数传递</router-link></li>
    <li><router-link to="/test1/query02?msg1=收到&msg2=十点多&msg3=89">子路由:参数传递2</router-link></li>
  </ul>
  <router-view></router-view>
</template>

<script>
export default {
  name: "HELLO_02"
}
</script>

<style scoped>
</style>
```

### 路由传参获取

* 两种方式
* 在子路由中这样设置：
* 注：useRouter()是整个路由对象，可以通过它进行跳转，也可以通过它获取传参
* 注：useRoute()是传参对象，可以更加方便的获取参数

```vue
<template>
  <ul>
    <li>参数一：{{ msg.msg1 }}</li>
    <li>参数二：{{ msg.msg2 }}</li>
    <li>总参数：{{ msg }}</li>
  </ul>
</template>

<script setup lang="ts">
import {useRoute} from "vue-router";

interface Query {
  msg1: string,
  msg2: string
}

let msg: Query = {
  msg1: "无", msg2: "无"
}
// const query = <Query>useRouter().currentRoute.value.query.valueOf()
const query = <Query>useRoute().query.valueOf()
const setMsg = (q: Query) => {
  msg.msg1 = q?.msg1
  msg.msg2 = q?.msg2
}
setMsg(query)
</script>

<style scoped>

</style>
```

或者在setup外面（不推荐）：

```vue
<template>
<ul>
  <li>参数一：{{ $route.query.msg1 }}</li>
  <li>参数二：{{ $route.query.msg2 }}</li>
  <li>总参数：{{ $route.query}}</li>
</ul>
</template>

<script>
export default {
  name: "QueryTest02",
  "mounted"() {
    console.log(this.$route["query"])
  }
}
</script>

<style scoped>

</style>
```

### 通过name使用路由

两个好处:

* 通过配置项进行params传参只能使用name
* 可以跳过父级路径直接引用子路径

**路由配置**

```ts
/**
 * 路由2的子路由，
 * 父路径：/test1
 * 父文件：HELLO_02.vue
 */
const test1_children = [
 {
        path: 'query02', // 即使使用name，仍然需要path
        name:'query02', // 可以使用name，这样引用就不用加父前缀
        component: () => import('@/views/QueryTest02.vue')
    },
]

export default test1_children
```

**通过配置项使用（也只能通过配置项使用）**

```html
<li>
    <router-link :to="{
                          name:'query02',
                          query:{
                          msg1:'收到',
                          msg2:'十点多',
                          msg3:999
                          }
                      }">子路由:参数传递2
    </router-link>
</li>
```

### 向prop传参（三种方式）

**一：只需要加上prop=true，再将路径变为param形式（这只是第一种方式）**

但有一个问题，这样会将所有的param参数解析，并且会导致无法使用query

```ts
/**
 * 路由2的子路由，
 * 父路径：/test1
 * 父文件：HELLO_02.vue
 */
const test1_children = [
	{
        path: 'prop/:role',
        props:true,
        component: () => import('@/views/MyHello.vue')
    }
]

export default test1_children
```

**然后这么用**

```html
<li>
  <router-link to="/test1/prop/路由传递props">子路由:prop传递</router-link>
</li>
```

**组件**

```vue
<template>
  <h1>{{ role }}</h1>
</template>

<script setup lang="ts">
defineProps<{
  role: string
}>()
</script>
```

**二：prop还有第二种方式传参，就是在路由配置处直接传对象**

```json
{
    path: 'prop/:role',
    props:{role:"sss"},
    component: () => import('@/views/MyHello.vue')
}
```

**三：第三种，使用函数进行解构传参**

比较灵活，但不如第一种方法方便。

原来是这样写的

```json
{
    path: 'prop',
    name:'propTest',
    props:($route:any):object=>{return {role:$route.query.role}},
    component: () => import('@/views/MyHello.vue')
}, 
```

可以简化成这样*(通过解构赋值与箭头函数)*:

```json
{
    path: 'prop',
    name:'propTest',
    props:({query}:any):object=>({role:query.role}),
    component: () => import('@/views/MyHello.vue')
},
```

然后这样使用

```html
<router-link :to="{
  name:'propTest',
  query:{
    role:'测试Query传prop'
  }
}">子路由:prop传递</router-link>
```

**四：补充：第三种方式如何传其它的参数**

```js
{
    path: 'prop/:roll',
    name:'propTest',
    props:({query}:any):object=>({role:query.role}),
    component: () => import('@/views/MyHello.vue')
}
```

```html
<router-link :to="{
                      name:'propTest',
                      params:{roll:'ss'},
                      query:{
                      role:'测试Query传prop'
                      }
                  }">子路由:prop传递</router-link>
```

### replace使用

```html
<router-link replace to="/test1/life">子路由:生命周期</router-link>
```

这样就会替换掉当前的浏览器地址，也就是说，上一个浏览历史记录不见了。

用于同页切换

它的编程式导航这样使用

```html
<li @click="router.replace('/test1/life')">直接子路由</li>
```

### keep-alive

```html
  <router-view v-slot="{ Component }">
    <keep-alive include="MyHello">
      <component :is="Component" />
    </keep-alive>
  </router-view>
```

* 这是让路由在切换后不要卸载，仍然保有数据的一种手段

* Include后跟着组件名，表示需要缓存的路由，这也可以是多个

* ```html
  <router-view v-slot="{ Component }">
    <keep-alive include="TimerTest,NumTest">
      <component :is="Component" />
    </keep-alive>
  </router-view>
  ```

### 新的生命周期

保持缓存的路由组件有两个生命周期，activated与deactivated

对应着激活与取消激活

这样使用

```vue
<template>
  <p>组件已激活时间：{{ actionNum }}</p>
</template>

<script setup lang="ts">
import {onActivated, onDeactivated, ref} from "vue";
// 设置计时器
const actionNum = ref(0)
let timer:number
// 设置路由激活函数
onActivated(()=>{
  console.log("路由被激活")
  timer=setInterval(() => {
    actionNum.value++
  }, 1000)
})
// 设置路由取消激活函数
onDeactivated(()=>{
  console.log("路由被取消激活")
  clearInterval(timer)
})
</script>

<style scoped>

</style>
```

只有在保持生命的路由视图中有效

```html
<router-view v-slot="{ Component }">
  <keep-alive>
    <component :is="Component" />
  </keep-alive>
</router-view>
```

当然，也可以这样玩：

```vue
<template>
  <ul>
    <li :style="{opacity:b}">数字增加:</li>
    <li>{{ a }}</li>
    <li>
      <button @click="a++">点击增加</button>
    </li>
  </ul>
</template>

<script setup lang="ts">
import {onActivated, onDeactivated, ref} from "vue";

const a = ref(1)
// 定义透明度
const b = ref(1)
// 设置定时器，让透明度变化
let isAdd = false
let timer: number
// 设置路由激活函数
onActivated(() => {
  console.log("路由2被激活")
  timer = setInterval(() => {
    if (isAdd) b.value += 0.1
    else b.value -= 0.1
    if (b.value <= 0 || b.value >= 1) isAdd = !isAdd
  }, 100)
})
// 设置路由取消激活函数
onDeactivated(() => {
  console.log("路由2被取消激活")
  clearInterval(timer)
})
</script>
```

