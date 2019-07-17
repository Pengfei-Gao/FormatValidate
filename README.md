### 一、简介

这是一个用于校验输入参数格式的组件，有着丰富的校验规则，支持规则扩展。

### 二、支持的规则

多个规则间可使用`,`隔开，规则和参数之间使用`|`间隔，多个参数使用`:`隔开。

| 规则  | 说明                                               | 使用                                                                                                           |
| --    | --                                                 | --                                                                                                             |
| int   | 必须为整数                                         | `int`                                                                                                          |
| range | 必须在某个区间内                                   | `range\|1:6`, 表示传入的参数必须 `>= 1` 且 `<= 6`                                                              |
| max   | 指定参数的最大值                                   | `max\|1`,表示传入的参数必须`<= 1`                                                                              |
| min   | 指定参数的最小值                                   | `min\|1`,表示传入的参数必须`>= 1`                                                                              |
| in    | 指定参数必须在某个集合内                           | `in\|DOG:CAT:FISH`, 表示传入的参数必须在集合 { DOG, CAT,FISH } 内                                              |
| phone | 指定参数必须为手机号，目前可选为：`zh_cn`、`zh_hk` | `phone\|zh_cn`,表示必须为国内手机号；`phone\|zh_cn:zh_hk`, 表示参数可为过呢诶手机号，也可为国外手机号          |
| email | 指定参数必须为邮箱，可传入域名。                   | `email`,表示为合法邮箱地址即可；`email\|gmail.com:qq.com:outlook.com`,表示参数只可为gmail、qq、outlook系的邮箱 |

### 三、使用

**一个简单的使用示例如下：**

```java

import net.aifei8.FormatValidate;
import java.util.HashMap;
import java.util.Map;

public class FormatCheck {
    
    public static void main(String[] args) {

        FormatValidate checker = new FormatValidate();

        Map<String, String> input = new HashMap<String, String>(){
            {
                put("a", "1");
                put("c", "1");
            }
        };

        Map<String, String> rules = new HashMap<String, String>(){
            {
                put("a", "int,range|1:4");
                put("c", "int,min|0,max|2");
                put("c", "int,min|0,max|2");
                put("d", "int,min|0,max|2");
                put("d", "phone|zh_cn");
                put("e", "email|gmail.com:baidu.com");
            }
        };

        try{
            checker.validate(input, rules);
        }catch (Exception e){
            System.out.println(">>>>>>>>>>>" + e.getMessage());
        }

    }
}
```

**或者可以采用一种更简洁的写法：**

```java
public class RequestController {
    public int processResuest(){
        try{
            FormatValidate checker = new FormatValidate();
            checker.validate(getParameterMap(), new HashMap<String, String>(){
                {
                    put("a", "int,range|1:4");
                    put("b", "int,min|0,max|2");
                    put("c", "int,min|0,max|2");
                    put("d", "int,in|1,4,7");
                }
            });

        }catch (Exception e){
            System.out.println(">>>>>>>>>>>" + e.getMessage());
        }
    } 
}
```


### 四、编写自己的规则

校验规则的接口约束如下：

```java
package net.aifei8;

public interface FormatValidateRuleInterface {

    public void Validate(String key, String value, String args[]) throws Exception;

}

```

所有规则类都必须遵从该接口的约束。接口中`Validate`方法的参数说明如下：

| 参数  | 类型   | 说明                                 |
| --    | --     | --                                   |
| key   | String | 参数的键值                           |
| value | String | 参数的值                             |
| args  | Array  | 该规则对应的参数列表。若规则为`range | 1:6`, 那么`args`数组中的值依次为1、6 |

下面给出`range`规则类的定义，作为示例：

```java
package net.aifei8.rules;

import net.aifei8.FormatValidateException;
import net.aifei8.FormatValidateRuleInterface;

public class Range implements FormatValidateRuleInterface {

    public void Validate(String key, String value, String args[]) throws FormatValidateException{
        if(args.length < 2){
            return;
        }
        int v = Integer.parseInt(value);
        int min = Integer.parseInt(args[0]);
        int max = Integer.parseInt(args[1]);
        if(!(v >= min && v <= max)){
            throw new FormatValidateException(String.format("parameter %s must more than %s and less than %s",
                    key, args[0], args[1]), 1);
        }
    }
}
```

当规则写完后，需要向规则引擎注册该规则。 默认自带的规则列表全部位于`net.aifei8.FormatValidate.rules`内:

```java
    ...
    public FormatValidate(){
        this.registerRule("int", net.aifei8.rules.BeInteger.class);
        this.registerRule("range", net.aifei8.rules.Range.class);
        this.registerRule("max", net.aifei8.rules.Max.class);
        this.registerRule("min", net.aifei8.rules.Min.class);
    }
    ...
```

可以调用`public FormatValidate registerRule(String ruleName, Class<?> rule)`函数注册规则：

```java
public class FormatValidate {

    public static void main(String[] args) {

        FormatValidate checker = new FormatValidate();

        Map<String, String> input = new HashMap<String, String>(){
            {
                put("a", "1");
                put("c", "1");
            }
        };

        Map<String, String> rules = new HashMap<String, String>(){
            {
                put("a", "int,range|1:4");
                put("c", "int,min|0,max|2");
            }
        };

        try{
            checker.registerRule("min", net.aifei8.rules.Min.class).validate(input, rules);
        }catch (Exception e){
            System.out.println(">>>>>>>>>>>" + e.getMessage());
        }

    }
}


```
