EasyReflect
-----------

## Overview
上 Java 课的时候无聊想出来的玩意儿，如果把 Java 反射机制的几门神器放在一起用会怎么样呢。。。

于是乎就有了这个！

## Download
``` Groovy
dependencies {
    compile 'moe.haruue:easy-reflect:1.1.0'
}
```

## Usage
首先根据你要反射的类写一个接口，接口里包含有你要反射的方法的方法签名，此外你还可以使用 `@Setter` 和 `@Getter` 注解来创建一个字段的 Getter 和 Setter 方法。假如你要反射 `Target` 类的 `method` 方法和 `staticMethod` 方法，还需要反射 `field` 字段，那就这样写：

``` Java
public interface TargetInterface {
    String method(int length);
    String staticMethod(int length);

    @Getter(name = "field")
    String getField();

    @Setter(name = "field")
    void setField(String s);
}
```

然后使用 `EasyReflect` 创建代理，通过代理对象直接调用即可。。。

``` Java
TargetInterface proxy = EasyReflect.from(target, TargetInterface.class);
proxy.method(5);
proxy.staticMethod(6);
proxy.getField();
proxy.setField("abc");
```

如果你没有这个类的实例，甚至连这个类本身都需要在运行时反射出来，你仍然可以反射它的静态方法。。。

``` Java
TargetInterface proxy = EasyReflect.from(null, Class.forName("moe.haruue.reflect.test.Target"), TargetInterface.class);
proxy.staticMethod(7);
```

静态字段的 Setter 和 Getter 等同于静态方法。

## Example
具体示例请戳 [这里](./easy-reflect/src/test/java/moe/haruue/reflect/test/Test.java)

## License

``` License
Copyright 2017 Haruue Icymoon

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
