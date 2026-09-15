# 第五章补充 Servlet接口详解

> 本篇是对 `05_第五章 Servlet.md` 中 **5.1 Servlet 接口** 的详细展开。
> 讲义 5.1 只罗列了 5 个方法的签名，没有讲"为什么会有这个接口"，本篇把这个"底"补上。

# 一 核心认知 Servlet是一个"规范"

> 讲义原话：`Servlet 规范接口,所有的Servlet必须实现`

+ **规范 = 一份合同**：规定了"长什么样"，不关心"怎么做"

## 1.1 用JDBC类比(方向反过来)

> 学JDBC时,你是**调用方**

``` java
java.sql.Connection                          ← 接口(规范,SUN定的)
com.mysql.cj.jdbc.ConnectionImpl             ← 实现(MySQL驱动厂商写的)
```

+ 你面向 `Connection` 接口编程,底层换MySQL还是Oracle都不用改代码

> Servlet是同一套思路,但**方向反过来了**

``` java
jakarta.servlet.Servlet                      ← 接口(规范,SUN/Oracle定的)
com.atguigu.servlet.UserServlet              ← 实现(我们自己写的)
Tomcat                                       ← 调用方!
```

+ **关键点：Tomcat才是调用方,我们的类是被调用的一方**

## 1.2 Tomcat内部如何使用Servlet(伪代码)

``` java
// 这是Tomcat内部的伪代码,不是真实源码
Servlet servlet = 根据web.xml/注解找到你的类并new出来;
servlet.init(servletConfig);      // Tomcat调你
servlet.service(req, resp);       // Tomcat调你
servlet.destroy();                // Tomcat调你
```

+ Tomcat的源码里从来没有 `UserServlet` 这个名字,它只认识 `Servlet` 这个类型
+ 所以：**你的类必须"长得像Servlet接口",Tomcat才认得你、才敢调你**
+ 这就是"所有的Servlet必须实现Servlet接口"的真正含义

> 一句话总结

+ Servlet接口是Tomcat和你的代码之间的**插头标准**
+ Tomcat是插座,你的类是插头,插头必须是国标形状(实现接口),插座才插得上

## 1.3 这解释了第四章的奇怪现象

+ 第四章讲义说："Servlet对象是Servlet容器创建的,生命周期方法都是由容器调用的,这一点和我们之前编写的代码有很大不同"

> 为什么不同

+ 以前是**你new对象、你调方法** (`new ArrayList<>(); list.add()`)
+ 从Servlet开始,变成**容器new对象、容器调你的方法**
+ 这种思想叫**控制反转(IoC)**,后面Spring会把它玩到极致
+ 讲义那句"越来越多的对象交给容器或框架来创建"就是在埋伏笔

# 二 五个方法逐个拆解

> Servlet接口完整定义

``` java
public interface Servlet {
    void init(ServletConfig config) throws ServletException;
    ServletConfig getServletConfig();
    void service(ServletRequest req, ServletResponse res) throws ServletException, IOException;
    String getServletInfo();
    void destroy();
}
```

## 2.1 init(ServletConfig config) 初始化方法

+ **谁调用**：容器(Tomcat),在**构造完对象之后**自动调用,只调用**1次**
+ **参数哪来的**：容器负责new一个ServletConfig对象传进来,是容器塞给我们的
+ **干什么**：提供"开机自检"的机会,比如读配置、加载资源、初始化连接池

> ⚠️ 本课程最大的坑：init有两个重载

+ 第四章4.2测试代码里写的是：

``` java
@Override
public void init() throws ServletException {   // 注意：无参!
    System.out.println("初始化方法");
}
```

+ 但是 **Servlet接口里根本没有无参的 `init()`**!接口里只有 `init(ServletConfig config)`
+ 无参 `init()` 是 **GenericServlet 加的**(5.2节内容),源码如下：

``` java
public void init(ServletConfig config) throws ServletException {
    this.config = config;   // 先把config存到属性上
    this.init();            // 再去调用无参的init()
}

public void init() throws ServletException {
    // 空实现,留给我们重写
}
```

> 为什么要这么绕

+ 如果让我们重写 `init(ServletConfig config)`,很可能写成：

``` java
@Override
public void init(ServletConfig config) throws ServletException {
    System.out.println("初始化");
    // 忘了写 super.init(config) !!!
}
```

+ 一旦忘了 `super.init(config)`,`this.config = config` 就没执行,config属性还是null
+ 后面 `getServletConfig()` 拿到的就是null,想读初始化参数直接 **NullPointerException**
+ 所以设计者干脆**再开一个无参的 `init()`** 让我们重写,`this.config = config` 由GenericServlet保证执行

> **结论(考试/面试爱问)**

+ 自己写Servlet要写初始化逻辑,重写**无参的 `init()`**,不要重写 `init(ServletConfig)`

## 2.2 getServletConfig() 获取配置对象

``` java
public ServletConfig getServletConfig();
```

+ 表面看很无聊——"把刚才传进来的东西再取出来",但它**必须存在**
+ 原因：`init(ServletConfig config)` 的 config 是**方法参数(局部变量)**,方法执行完就没了
+ 容器下次调用的是 `service()`,service里怎么拿到配置？**只能靠属性存着**

``` java
private transient ServletConfig config;   // GenericServlet里的字段,就是为了存它
```

+ 这一对方法的本质：**init负责存,getServletConfig负责取**,一个存一个取,缺一不可
+ 拿到之后的用法(第六章6.1)：

``` java
String encoding = getServletConfig().getInitParameter("encoding");
```

## 2.3 service(ServletRequest req, ServletResponse res) 核心

``` java
public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException;
```

+ 这是**唯一一个每次请求都会被调用**的方法,是真正写业务的地方
+ init/destroy一辈子只跑一次,这个跑N次

> ⚠️ 注意参数类型

+ 是 `ServletRequest` / `ServletResponse`,**不是** `HttpServletRequest` / `HttpServletResponse`
+ 因为 **Servlet规范不只服务于HTTP协议**
+ 理论上FTP、SMTP等其他协议也能用Servlet技术(实际上99.99%都是HTTP)
+ 所以规范这一层只能定义最抽象的 `ServletRequest`——"一个请求"该有的能力

> HttpServlet帮我们做了向下转型(5.3节内容)

``` java
// HttpServlet源码
public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
    HttpServletRequest request;
    HttpServletResponse response;
    try {
        request = (HttpServletRequest) req;      // 强转!
        response = (HttpServletResponse) res;
    } catch (ClassCastException e) {
        throw new ServletException("non-HTTP request or response");
    }
    service(request, response);                   // 调用下面那个重载版本
}
```

> 5.1和5.3的关系

| 层次 | service方法签名 | 谁写的 |
| ---- | ------------------------------------------- | -------------- |
| Servlet接口 | `service(ServletRequest, ServletResponse)` | 规范定义 |
| HttpServlet | 重写上面那个 + 新增 `service(HttpServletRequest, HttpServletResponse)` | Tomcat的实现者 |
| 我们自己 | 重写 `service(HttpServletRequest, HttpServletResponse)` | 自己 |

+ 2.2节写的 `service(HttpServletRequest req, HttpServletResponse resp)`,**重写的是HttpServlet里那个重载版本,不是接口里的方法**
+ 讲义在5.3/5.4才点明这一点,所以看5.1时会觉得对不上号——不是理解问题,是讲义顺序问题

> ⚠️ 特别容易踩的坑：service和doGet/doPost二选一

+ HttpServlet的 `service(HttpServletRequest, HttpServletResponse)` 里包含了"判断请求方式→分发到doGet/doPost"的逻辑
+ **如果重写了这个方法,doGet/doPost就永远不会被调用了**
+ 这就是5.4那句话的意思：
    + 要么重写 `service` 方法
    + 要么重写 `doGet` / `doPost` 方法
    + **二选一,别两个都写**
+ 讲义前面章节(2.2、4.2)一直让重写 `service`,是为了先把"请求-响应"讲清楚
+ 后面标准写法就换成重写 doGet/doPost 了

## 2.4 getServletInfo() 基本没用

``` java
public String getServletInfo();
```

+ 返回一段**描述这个Servlet的字符串**,比如作者、版本、版权信息
+ 类似 `java -version` 那种自报家门的信息
+ 实际开发中根本不用,GenericServlet给的是"平庸实现"(讲义原话),返回 `""` 空字符串

## 2.5 destroy() 销毁方法

+ **谁调用**：容器(Tomcat)
+ **什么时候**：容器**正常关闭**时(比如停掉Tomcat)
+ **干什么**：释放资源,比如关掉数据库连接池、关掉文件流、把内存数据落盘
+ 注意讲义措辞："Servlet实例在**销毁之前**调用的方法"——是"之前",给一个临终遗言的机会,不是销毁本身

> ⚠️ 细节

+ 某些极端情况(进程被kill -9、断电)**destroy()根本来不及被调用**
+ 别把"必须执行"的关键逻辑放这里,能放的只有"尽力而为的清理"

# 三 5.1和第四章"生命周期"的对应关系

> 第四章那张生命周期表里的方法名,**全部来自这个接口**

| 第四章讲的生命周期 | 5.1接口里的方法 | 调用次数 | 谁调用 |
| ------------------ | ------------------------------ | -------- | -------------------------- |
| 构造对象 | (接口里没有,就是个构造器) | 1 | 容器 |
| 初始化 | `init(ServletConfig config)` | 1 | 容器 |
| 处理服务 | `service(req, resp)` | **N** | 容器 |
| 销毁 | `destroy()` | 1 | 容器 |
| —— | `getServletConfig()` | 想调就调 | 我们(容器保证能取到) |
| —— | `getServletInfo()` | 爱调不调 | 我们 |

+ **第四章是"时序",5.1是"这些方法是谁规定的"**,两章讲的是同一件事的两个角度
+ 这5个方法里,只有 `service` 是需要真正动脑子写业务的
+ 其他4个要么是容器用的(init/destroy),要么是工具(getServletConfig/getServletInfo)

# 四 为什么不直接实现Servlet接口

## 4.1 头铁直接implements会怎样

``` java
public class BadServlet implements Servlet {
    @Override
    public void init(ServletConfig config) throws ServletException { }

    @Override
    public ServletConfig getServletConfig() { return null; }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        // ❌ 拿到的是ServletRequest,想读请求头还得自己强转
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String method = request.getMethod();
        // ❌ 还得自己写一堆 if (method.equals("GET")) ... else if (POST) ...
    }

    @Override
    public String getServletInfo() { return ""; }

    @Override
    public void destroy() { }
}
```

## 4.2 痛点总结

1. **5个方法一个都不能少**,哪怕其中有4个根本不需要
2. **要自己强转** `ServletRequest` → `HttpServletRequest`
3. **要自己按请求方式分发**到不同的处理逻辑
4. 每写一个Servlet就要重复上面这些垃圾代码

## 4.3 extends HttpServlet之后

| 困扰 | HttpServlet帮你解决的 |
| ------------------------- | ------------------------------------------------------------ |
| 5个方法都要写 | 已实现好4个(init/getServletConfig/getServletInfo/destroy由GenericServlet给平庸实现),service(ServletRequest, ServletResponse)由HttpServlet实现 |
| 要自己强转 | 已转好,把HttpServletRequest版本的service给我们 |
| 要自己判断GET/POST | 已判断好,拆成doGet/doPost/doPut...等着我们重写 |
| 重复代码 | 只需要重写1个方法 |

## 4.4 三层继承结构的分工

``` text
Servlet 接口          →  定规范：你必须有这5个方法(纯合同,没有实现)
   ↑
GenericServlet 抽象类 →  实现"跟协议无关"的通用部分：存config、提供无参init()、log()等
   ↑
HttpServlet 抽象类    →  实现"跟HTTP协议相关"的部分：强转、按method分发到doXxx
   ↑
我们的UserServlet     →  只重写doGet/doPost(或早期写法重写service),写业务
```

# 五 一次完整请求的调用链

> 浏览器访问 `http://localhost:8080/app/userServlet?username=atguigu`

``` text
① Tomcat解析请求 → 匹配到url-pattern /userServlet → 找到UserServlet
② Tomcat new UserServlet()          ← 构造器(第一次请求时)
③ Tomcat组装ServletConfig对象
④ Tomcat调用 servlet.init(config)   ← 我们重写的无参init()在里面被调
⑤ Tomcat把请求报文封装成HttpServletRequest,响应对象搞成HttpServletResponse
⑥ Tomcat调用 service(ServletRequest, ServletResponse)      ← 【接口方法】
     └─ HttpServlet内部强转成 HttpServletRequest / HttpServletResponse
     └─ 调用 service(HttpServletRequest, HttpServletResponse) ← 【我们重写的】
         └─ if 重写了service：直接执行业务代码
         └─ if 只重写了doGet：HttpServlet判断method==GET → 调doGet
⑦ 往resp里write("YES") → Tomcat转成响应报文 → 浏览器看到YES
⑧ ... 后续每个请求：只走⑥(init不再调,对象是单例复用的)
⑨ 停掉Tomcat → Tomcat调用 servlet.destroy()
```

# 六 记忆主线(一句话版)

> **Servlet接口 = Tomcat和我们之间的合同,只有5个方法。**
>
> **Tomcat负责new我们、调我们的init/service/destroy；其中service是唯一每次请求都会跑、也是唯一要我们写业务的方法。**
>
> **因为规范面向所有协议,所以参数是ServletRequest/ServletResponse；HttpServlet帮我们强转成HTTP版本,还帮我们把请求分发到doGet/doPost,所以现实中我们从不直接实现接口,而是继承HttpServlet。**
