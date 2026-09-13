# 第四章 · XML 编程题

> 动手写。写完对照《复习_第一至四章.md》或笔记核对。⭐ = 重点（2 题）。

---

## 题目 1：写一个符合规范的 XML 文件 ⭐

按要求写一个 `students.xml`，必须满足以下 XML 基本语法：

1. 第一行第一列写文档声明（UTF-8 编码）
2. 根标签有且只有一个 `<students>`
3. 里面放两个 `<student>` 子标签，每个 student 里有 name、age
4. 给 `<student>` 加一个属性 `id="1"`（属性必须有值、值加引号）

```xml
<!-- 在这里写你的 XML -->

```

**目标效果**：能被 XML 解析器正常解析，不报语法错误。

---

## 题目 2：用 DOM4J 解析上面的 XML ⭐

写一个 Java 类，用 DOM4J 解析题目 1 的 `students.xml`，打印每个 student 的 name 和 age。

只给骨架，方法体里的步骤你来补（**不写答案**）：

```java
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import java.io.InputStream;
import java.util.List;

public class ParseXml {
    public static void main(String[] args) throws Exception {
        // TODO 1: 创建 SAXReader 对象

        // TODO 2: 读 students.xml 的字节输入流，得到 Document 对象

        // TODO 3: 获取根标签 root

        // TODO 4: 获取根下所有 student 子标签（用 elements("student")）

        // TODO 5: 遍历每个 student，用 element("name").getText() 拿 name，
        //          用 element("age").getText() 拿 age，打印出来

        // TODO 6（加分）：用 attributeValue("id") 打印每个学生的 id 属性
    }
}
```

**提示（关键 API）**：
- `new SAXReader()`
- `reader.read(输入流)` → 返回 Document
- `document.getRootElement()` → 根标签
- `root.elements("student")` → 指定名的子标签列表
- `element.element("name").getText()` → 子标签文本
- `element.attributeValue("id")` → 属性值
