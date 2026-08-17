# 01-HTML+CSS 知识点总结

> 来源：《01-前端Web开发(HTML+CSS)》
> 面向后端开发的复习重点：flex 布局、表单（尤其 name 属性）、盒子模型/版心居中。
> 每个知识点附 2 个小案例，均可直接保存为 .html 用浏览器打开运行。

---

## 一、前端三件套与 Web 标准

| 技术 | 职责 | 一句话 |
|------|------|--------|
| HTML | 结构 | 页面有什么（文字/图片/表单/表格） |
| CSS | 表现 | 长什么样（颜色/大小/位置） |
| JS | 行为 | 能干嘛（交互） |

- 浏览器里负责「解析 + 渲染」代码的部分叫**浏览器内核**。
- 不同浏览器内核不同，需要 **Web 标准**（W3C 制定）统一。

### 📝 小案例 ×2

**案例 1：只有 HTML（结构）——文字是默认样式**

```html
<body>
  <h1>我是标题</h1>
  <p>我是一段普通文字</p>
</body>
```
> 学什么：没有任何 CSS，浏览器用默认样式渲染，字是黑色、靠左。这就是「只有结构没有表现」。

**案例 2：HTML + CSS + JS 三件套齐全**

```html
<body>
  <h1 id="t" style="color:red;">点下面的按钮</h1>
  <button onclick="document.getElementById('t').innerText='行为生效啦'">点我</button>
</body>
```
> 学什么：`style` 是 CSS（表现，红色），`onclick` 是 JS（行为，点击改文字）。三个职责一眼分清。

---

## 二、HTML 重点

### 1. 基本骨架（必背）

```html
<html>
  <head><title>标题</title></head>   <!-- head 给浏览器看 -->
  <body>这里是用户看到的内容</body>   <!-- body 给用户看 -->
</html>
```

### 📝 小案例 ×2

**案例 1：最小骨架 + title 显示在标签页**

```html
<html>
  <head>
    <title>这是浏览器标签页的标题</title>
  </head>
  <body>
    这里是正文内容，用户能看见
  </body>
</html>
```
> 学什么：`title` 的文字不出现在页面里，而是显示在浏览器标签页顶部；`body` 的内容才显示在页面区域。

**案例 2：head 与 body 的区别**

```html
<html>
  <head>
    <title>标题</title>
    <style>p{color:red;}</style>   <!-- 给浏览器看，用户看不见这段代码 -->
  </head>
  <body>
    <p>我是红色文字，红色是 head 里的 style 控制的</p>
  </body>
</html>
```
> 学什么：`head` 里放「给浏览器看」的配置（样式、标题），`body` 里放「给用户看」的内容。职责分工是 HTML 的核心思想。

---

### 2. 标签特点（爱考）

- 标签**不区分大小写**，建议小写。
- 属性值**单双引号都行**，一般写双引号。
- 标签是**预定义好的**，只有 h1~h6，没有 h7。

### 📝 小案例 ×2

**案例 1：标签不区分大小写**

```html
<body>
  <H1>大写H1</H1>
  <h1>小写h1</h1>
</body>
```
> 学什么：`<H1>` 和 `<h1>` 效果一样，浏览器都认。但建议统一写小写，规范。

**案例 2：属性单双引号都行 + 没有 h7**

```html
<body>
  <a href='https://www.baidu.com'>单引号也能用</a><br>
  <a href="https://www.baidu.com">双引号推荐写法</a>
  <h7>你以为我是七级标题？其实我啥都不是，就是普通文字</h7>
</body>
```
> 学什么：单引号/双引号都行；`<h7>` 浏览器不识别，会当成普通文本处理——标签是预定义死的。

---

### 3. 常用标签速查

| 标签 | 作用 | 关键属性 |
|------|------|----------|
| `h1~h6` | 标题，h1 最大 | — |
| `a` | 超链接 | `href`(地址)、`target`(`_self`/`_blank`) |
| `img` | 图片 | `src`、`width` |
| `video`/`audio` | 视频/音频 | `src`、`controls` |
| `p` | 段落 | — |
| `div` | 块级布局 | **独占一行**，可设宽高 |
| `span` | 行内布局 | **一行多个**，宽高由内容撑开 |
| `table/tr/td` | 表格 | `thead` 表头、`tbody` 表体 |
| `form` | 表单 | `action`、`method` |

### 📝 小案例 ×2

**案例 1：标题 + 超链接 + 图片**

```html
<body>
  <h1>一级标题最大</h1>
  <h6>六级标题最小</h6>
  <a href="https://www.baidu.com" target="_blank">点我在新标签页打开百度</a>
  <img src="https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg" width="100">
</body>
```
> 学什么：h1~h6 字号递减；`target="_blank"` 是新标签页打开，`_self` 是当前页打开；`img` 的 `src` 填图片地址，`width` 控宽度。

**案例 2：div（块级）vs span（行内）**

```html
<body>
  <div style="background:#ffcccc;">我是div，独占一行</div>
  <div style="background:#ffcccc;">我也是div，被挤到下一行</div>
  <span style="background:#ccffcc;">我是span1，</span>
  <span style="background:#ccffcc;">我是span2，和span1挤在一行</span>
</body>
```
> 学什么：两个 `div` 各占一行（块级），两个 `span` 挤在同一行（行内）。这是布局最基础的分辨。

---

### 4. 路径

- 相对路径：`./` 当前目录（可省）、`../` 上一级。
- 绝对路径：磁盘路径、网络 URL。

### 📝 小案例 ×2

**案例 1：相对路径 `./` 与 `../`**

```html
<body>
  <!-- 假设当前页面在 HTML 目录下，img 在 HTML/img 里 -->
  <img src="img/1.png">      <!-- ./ 当前目录，可省略 -->
  <!-- 图片在 HTML 的上一级目录里 -->
  <img src="../1.png">       <!-- ../ 表示回到上一级 -->
</body>
```
> 学什么：`./` 指当前目录（通常省略不写），`../` 指上一级目录。相对路径以「当前文件所在位置」为基准。

**案例 2：绝对路径——网络 URL vs 磁盘路径**

```html
<body>
  <!-- 绝对网络路径：联网可用，最省事 -->
  <img src="https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg" width="100">
  <!-- 绝对磁盘路径：写死本地盘符，换机器就失效，项目里别用 -->
  <!-- <img src="C:\Users\zz\Desktop\img\1.png"> -->
</body>
```
> 学什么：网络绝对路径跨机器都能用；磁盘绝对路径写死了盘符，别人电脑上打不开，所以项目里基本用相对路径。

---

## 三、CSS 重点

### 1. 三种引入方式

| 方式 | 写法 | 使用情况 |
|------|------|----------|
| 行内 | `<span style="color:red">` | 少用，冗余 |
| 内部 | `<style>` 写在 head | 练习用 |
| **外部** | `<link rel="stylesheet" href="xx.css">` | **企业主流**，html 与 css 分离 |

### 📝 小案例 ×2

**案例 1：三种方式给同一句话上色**

```html
<head>
  <style> .inner{color:blue;} </style>          <!-- 内部样式 -->
  <link rel="stylesheet" href="style.css">       <!-- 外部样式 -->
</head>
<body>
  <span style="color:red;">行内样式-红</span><br>
  <span class="inner">内部样式-蓝</span><br>
  <span class="outer">外部样式-绿(定义在style.css里)</span>
</body>
```
> 学什么：同一条规则，三种写法都能生效。行内写在标签上、内部写在 `<style>`、外部写在独立 css 文件再 `link` 引入。

**案例 2：外部样式（企业主流写法）**

style.css 文件内容：
```css
.title { color: green; font-weight: bold; }
```

html 文件：
```html
<head>
  <link rel="stylesheet" href="style.css">
</head>
<body>
  <div class="title">我的样式来自外部 style.css 文件</div>
</body>
```
> 学什么：html 和 css 完全分离，改样式只动 css 文件，不用动 html。这是真实项目里的标准做法。

---

### 2. 选择器（后端只学常见几种）

```css
元素选择器  p { ... }        /* 选所有 p */
类选择器   .class { ... }    /* 选 class，最常用 */
id选择器   #id { ... }       /* 选 id，唯一 */
```

### 📝 小案例 ×2

**案例 1：三种选择器各自命中**

```html
<head>
  <style>
    p { color: red; }        /* 元素选择器：命中所有 p */
    .cls { color: blue; }    /* 类选择器：命中 class="cls" */
    #box { color: green; }   /* id选择器：命中 id="box" */
  </style>
</head>
<body>
  <p>我命中元素选择器，红色</p>
  <div class="cls">我命中类选择器，蓝色</div>
  <div id="box">我命中id选择器，绿色</div>
</body>
```
> 学什么：三种选择器的写法区别——元素直接写名字、类加 `.`、id 加 `#`。

**案例 2：类选择器复用（最常用）**

```html
<head>
  <style>
    .highlight { color: orange; font-weight: bold; }
  </style>
</head>
<body>
  <p class="highlight">我是第一处</p>
  <p class="highlight">我是第二处</p>
  <p class="highlight">我是第三处，同一个class能反复用</p>
</body>
```
> 学什么：同一个 class 可以给多个标签用，改一处样式所有地方一起变。而 id 是唯一的，只能出现一次。所以**类选择器最常用**。

---

### 3. 常用样式属性

- 文字：`color`、`font-size`、`font-weight`、`font-family`
- 去下划线：`text-decoration: none`
- 段落：`line-height`(行高)、`text-indent: 2em`(首行缩进2字)
- 背景：`background-color`
- 颜色三种写法：`red`、`#b2b2b2`、`rgb(...)`

### 📝 小案例 ×2

**案例 1：文字样式全家桶**

```html
<head>
  <style>
    p {
      color: #333;          /* 颜色，可用 red / #333 / rgb(51,51,51) */
      font-size: 20px;      /* 字号 */
      font-weight: bold;    /* 加粗 */
      font-family: "楷体";  /* 字体 */
    }
  </style>
</head>
<body>
  <p>颜色、大小、加粗、字体，一条龙</p>
</body>
```
> 学什么：文字四件套 `color/font-size/font-weight/font-family`，颜色三种写法任选。

**案例 2：段落样式 + 去下划线**

```html
<head>
  <style>
    p {
      text-indent: 2em;  /* 首行缩进2个字 */
      line-height: 2;    /* 行高2倍，看起来更松散 */
    }
    a { text-decoration: none; }  /* 去掉链接下划线 */
  </style>
</head>
<body>
  <p>首行会缩进两个字。行高是两倍，段落之间看起来不会挤成一团。</p>
  <a href="https://www.baidu.com">我是没有下划线的链接</a>
</body>
```
> 学什么：`text-indent: 2em` 首行缩进、`line-height` 控行距、`text-decoration: none` 去下划线（新闻页、导航栏都用得上）。

---

### 4. 盒子模型（重点概念）

每个元素都是个盒子，由内到外：

```
content(内容) → padding(内边距) → border(边框) → margin(外边距)
```

- 盒子大小 = border + padding + content（**margin 不算**）
- 简写：`padding: 20px`(四边) / `20px 10px`(上下 左右) / `上 右 下 左`

### 📝 小案例 ×2

**案例 1：一个盒子看四层结构**

```html
<head>
  <style>
    .box {
      width: 200px;
      height: 200px;
      background-color: aquamarine;  /* 内容区背景 */
      padding: 20px;                 /* 内边距 */
      border: 10px solid red;        /* 边框 */
      margin: 30px;                  /* 外边距 */
    }
  </style>
</head>
<body>
  <div class="box">我是内容区</div>
  <div class="box">按 F12 打开开发者工具，能看到 content/padding/border/margin 四层</div>
</body>
```
> 学什么：F12 里选中元素，能直观看到四层各占多少。margin 是盒子「和别人的距离」，不占盒子本身大小。

**案例 2：box-sizing: border-box（后端布局必会）**

```html
<head>
  <style>
    .box {
      width: 200px;
      height: 100px;
      padding: 20px;
      border: 10px solid red;
      box-sizing: border-box;  /* width 直接含 padding+border */
    }
  </style>
</head>
<body>
  <div class="box">我实际总宽就是200px，padding和border都在里面</div>
</body>
```
> 学什么：默认 `content-box` 下，`width:200` + padding20 + border10 会变成 260。加 `box-sizing: border-box` 后，width 就是最终总宽，布局不跑偏。**这是最常见的坑，记住它。**

---

### 5. 版心居中（必会套路）

```css
.news-content {
  width: 70%;      /* 占宽70% */
  margin: 0 auto;  /* 上下0、左右auto → 横向居中 */
}
```

### 📝 小案例 ×2

**案例 1：width + margin auto 居中**

```html
<head>
  <style>
    .box {
      width: 70%;
      margin: 0 auto;      /* 左右 auto 自动平分剩余空间 */
      background-color: #eee;
    }
  </style>
</head>
<body>
  <div class="box">我占70%宽，并且水平居中</div>
</body>
```
> 学什么：`margin: 0 auto` 让块级元素左右自动分剩余空间，实现居中。这是「版心居中」的核心一行。

**案例 2：容器包裹整页内容**

```html
<head>
  <style>
    #container { width: 80%; margin: 0 auto; background:#f5f5f5; }
  </style>
</head>
<body>
  <div id="container">
    <h1>标题</h1>
    <p>整段内容都套在一个 container 里，整体就居中了</p>
  </div>
</body>
```
> 学什么：实战里用一个外层 `div` 包住所有内容，给它 `width + margin auto`，整页就居中了（央视新闻、Tlias 页面都是这么干的）。

---

## 四、Flex 布局（重头戏）

给**父容器**加 `display: flex`，子元素就排成一行：

```css
.container {
  display: flex;
  justify-content: space-between; /* 主轴上对齐 */
  align-items: center;            /* 交叉轴居中 */
}
```

`justify-content` 常用值：

- `flex-start` 从头排 / `flex-end` 从尾排 / `center` 居中
- `space-between` **两端贴边、中间平分**（导航栏「标题左、退出登录右」就用它）
- `space-around` 两边留白、中间平分

> 主轴 `row` 是横向，`column` 是纵向。**导航栏、搜索栏一行排布全靠 flex**，务必练熟。

### 📝 小案例 ×2

**案例 1：justify-content 各值对比**

```html
<head>
  <style>
    .box { display: flex; width: 400px; height: 100px;
           background: #eee; margin-bottom: 10px; }
    .item { width: 60px; height: 30px; background: orange; }
  </style>
</head>
<body>
  <div class="box" style="justify-content:flex-start;">
    <div class="item"></div><div class="item"></div><div class="item"></div>
  </div>
  <div class="box" style="justify-content:center;">
    <div class="item"></div><div class="item"></div><div class="item"></div>
  </div>
  <div class="box" style="justify-content:space-between;">
    <div class="item"></div><div class="item"></div><div class="item"></div>
  </div>
</body>
```
> 学什么：三行分别演示 `flex-start`（左靠）、`center`（居中）、`space-between`（两端贴边中间平分）。自己改 `flex-end`、`space-around` 再对比。

**案例 2：导航栏实战（标题左 + 链接右）**

```html
<head>
  <style>
    body { margin: 0; }
    .header {
      display: flex;
      justify-content: space-between; /* 标题靠左，链接靠右 */
      align-items: center;            /* 垂直居中 */
      background: #635f5f;
      padding: 15px 30px;
    }
    .header h1 { color: white; font-size: 24px; margin: 0; }
    .header a { color: white; text-decoration: none; }
  </style>
</head>
<body>
  <div class="header">
    <h1>Tlias智能学习辅助系统</h1>
    <a href="#">退出登录</a>
  </div>
</body>
```
> 学什么：这就是你 Tlias 顶部导航栏的完整实现。`space-between` 负责左右两端对齐，`align-items: center` 负责垂直居中，flex 就这三行核心。

---

## 五、表单（⭐ 后端必须吃透）

### 1. form 两个核心属性

```html
<form action="/save" method="post">
```

- `action`：数据提交到哪。
- `method`：提交方式
  - **GET**：数据拼在 URL 后面 `?username=Tom&age=12`，**长度有限**（默认值）
  - **POST**：数据放请求体里，**大小无限制**

### 2. 表单项三大标签

- `input`（用 `type` 控制：`text`/`password`/`radio`/`checkbox`/`file`/`date`/`hidden`/`submit`/`reset`/`button`）
- `select` + `option`（下拉）
- `textarea`（多行文本域）

### 3. ⚠️ 最重要的坑

> **表单项必须写 `name` 属性，否则提交时这个数据不会被采集！**

```html
<input type="text" name="username">   <!-- 有 name 才能提交 -->
```

### 4. 查询 / 清空按钮

```html
<button type="submit">查询</button>  <!-- 提交表单 -->
<button type="reset">清空</button>   <!-- 重置回默认值，不用写JS -->
```

### 📝 小案例 ×2

**案例 1：GET vs POST 看 URL**

```html
<body>
  <!-- GET：提交后看地址栏，数据拼在 ? 后面 -->
  <form action="/save" method="get">
    用户名: <input type="text" name="username">
    年龄:   <input type="text" name="age">
    <input type="submit" value="GET提交">
  </form>
  <br>
  <!-- POST：提交后地址栏不变，数据进了请求体 -->
  <form action="/save" method="post">
    用户名: <input type="text" name="username">
    年龄:   <input type="text" name="age">
    <input type="submit" value="POST提交">
  </form>
</body>
```
> 学什么：填 Tom/12 点 GET 提交，地址栏变 `/save?username=Tom&age=12`；POST 提交地址栏不变。这就是后端 SpringBoot 拿参数时，`@RequestParam` 读的数据来源。

**案例 2：各种表单项 + name 属性的坑**

```html
<body>
  <form action="/save" method="post">
    姓名: <input type="text" name="name"><br><br>
    密码: <input type="password" name="pwd"><br><br>
    性别: <input type="radio" name="gender" value="1">男
          <input type="radio" name="gender" value="2">女<br><br>
    学历: <select name="degree">
            <option value="1">本科</option>
            <option value="2">硕士</option>
          </select><br><br>
    描述: <textarea name="desc"></textarea><br><br>
    <!-- 没写 name 的输入框，提交时会被忽略 -->
    这个框没name: <input type="text"><br><br>
    <input type="submit" value="提交">
  </form>
</body>
```
> 学什么：`radio` 要同名 `name` 才能互斥单选；`select` 用 `name` 提交选中项。重点：**最后一个没写 name 的输入框，提交时数据会丢失**——这就是那个大坑。
