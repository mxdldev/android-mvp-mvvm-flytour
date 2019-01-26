### 1.前言
在长期的实战开发过程中，随着APP功能的不断增多，业务逻辑的越来越复杂，各个模块之间有着错综复杂的关系，各个模块高度的耦合在一起，修改一个功能牵一发而动全身，长期以来整个项目就是铁板一块。这样非常的不利于项目的后期的维护和开发。
* 传统架构图：<br>
![](https://img-blog.csdnimg.cn/20190126210043776.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2dlZHVvXzgz,size_16,color_FFFFFF,t_70)<br>
上图是目前比较普遍使用的Android APP技术架构，往往是在一个界面中存在大量的业务逻辑，而业务逻辑中充斥着各种网络请求、数据操作等行为，整个项目中也没有模块的概念，只有简单的以业务逻辑划分的文件夹，并且业务之间也是直接相互调用、高度耦合在一起的。
* 传统业务架构图:<br>
![](https://img-blog.csdnimg.cn/20190126215521137.png)<br>
为了着解决这些问题，近年来也涌现了多种插件框架实现方案，有的叫组件化，很多人将插件化和组件化混为一谈，虽然其目的都是一致的，对单一结构体的项目进行拆分进行解耦，但是其实两者是有着本质的区别。
* 组件化：有开发模式和发布模式之分，在开发模式下每个业务模块都是都是一个独立的apk，在发布模式打包的时候，每个业务模块都变成了一个个独立的组件library。
* 插件化：没有开发模式和发布模式之分，每个业务模块就是一个独立的apk，通过主工程apk来动态加载部署各个业务模块apk。
### 2.插件化
* 插件化架构图：<br>
![](https://img-blog.csdnimg.cn/20190126210108633.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2dlZHVvXzgz,size_16,color_FFFFFF,t_70)
现在市面上也涌现了很多插件化框架
*  1.android-pluginmgr
它的实现原理是使用DexMaker的动态热部署功能生成Activity，让这个Activity继承自目标插件所在的Activity。https://github.com/houkx/android-pluginmgr
* 2.dynamic-load-apk
它是基于代理的方式实现插件框架的，它需要按照一定的规则来开发插件APK,插件中的组件需要实现经过改造后的Activity、FragmentActivity，Service等的子类
https://github.com/singwhatiwanna/dynamic-load-apk
教程地址：https://blog.csdn.net/singwhatiwanna/article/details/40283117
* 3.DynamicAPK
这是携程实现的一种多APK/DEX加载的插件框架解决方案，加载实现Android App多apk插件化和动态加载，支持资源分包和热修复
https://github.com/CtripMobile/DynamicAPK
* 4.DroidPlugin
它是360手机助手实现的一种插件框架它可以在无需安装、修改的情况下运行APK文件,此机制对改进大型APP的架构，实现多团队协作开发具有一定的好处
https://github.com/DroidPluginTeam/DroidPlugin
以上就是几种常见的插件化框架，但是他们都是有一定学习成本的，实现比较麻烦，相比较而言组件化的实现还是比较简单的
### 3.组件化
组件化架构图：<br>
![](https://img-blog.csdnimg.cn/20190126210220224.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2dlZHVvXzgz,size_16,color_FFFFFF,t_70)<br>
上图是组件化工程模型，为了方便理解这张架构图，下面会列举一些组件化工程中用到的名词的含义：
* 集成模式：所有的业务组件被“app壳工程”依赖，组成一个完整的APP；
* 组件模式：可以独立开发业务组件，每一个业务组件就是一个APP；
* app壳工程：负责管理各个业务组件，和打包apk，没有具体的业务功能；
* 业务组件：根据公司具体业务而独立形成一个的工程；
* 功能组件：提供开发APP的某些基础功能，例如打印日志、树状图等；
* Main组件：属于业务组件，指定APP启动页面、主界面；
* Common组件：属于功能组件，支撑业务组件的基础，提供多数业务组件需要的功能，例如提供网络请求功能
### 4.组件化实施流程
* 4.1 组件模式和集成模式的转换
Android Studio中的Module主要有两种属性，分别为：
application属性，可以独立运行的Android程序，也就是我们的APP;
```
apply plugin: 'com.android.application'
```
library属性，不可以独立运行，一般是Android程序依赖的库文件;
```
 apply plugin: 'com.android.library'
```
Module的属性是在每个组件的 build.gradle 文件中配置的，当我们在组件模式开发时，业务组件应处于application属性，这时的业务组件就是一个 Android App，可以独立开发和调试；而当我们转换到集成模式开发时，业务组件应该处于 library 属性，这样才能被我们的“app壳工程”所依赖，组成一个具有完整功能的APP；
但是我们如何让组件在这两种模式之间自动转换呢？总不能每次需要转换模式的时候去每个业务组件的 Gralde 文件中去手动把 Application 改成 library 吧？如果我们的项目只有两三个组件那么这个办法肯定是可行的，手动去改一遍也用不了多久，但是在大型项目中我们可能会有十几个业务组件，再去手动改一遍必定费时费力，这时候就需要程序员发挥下懒的本质了。
试想，我们经常在写代码的时候定义静态常量，那么定义静态常量的目的什么呢？当一个常量需要被好几处代码引用的时候，把这个常量定义为静态常量的好处是当这个常量的值需要改变时我们只需要改变静态常量的值，其他引用了这个静态常量的地方都会被改变，做到了一次改变，到处生效；根据这个思想，那么我们就可以在我们的代码中的某处定义一个决定业务组件属性的常量，然后让所有业务组件的build.gradle都引用这个常量，这样当我们改变了常量值的时候，所有引用了这个常量值的业务组件就会根据值的变化改变自己的属性；可是问题来了？静态常量是用Java代码定义的，而改变组件属性是需要在Gradle中定义的，Gradle能做到吗？
Gradle自动构建工具有一个重要属性，可以帮助我们完成这个事情。每当我们用AndroidStudio创建一个Android项目后，就会在项目的根目录中生成一个文件 gradle.properties，我们将使用这个文件的一个重要属性：在Android项目中的任何一个build.gradle文件中都可以把gradle.properties中的常量读取出来；那么我们在上面提到解决办法就有了实际行动的方法，首先我们在gradle.properties中定义一个常量值 isModule（是否是组件开发模式，true为是，false为否）：
每次更改“isModule”的值后，需要点击 "Sync Project" 按钮。
```
isModule=false
```
然后我们在业务组件的build.gradle中读取 isModule，但是 gradle.properties 还有一个重要属性： gradle.properties 中的数据类型都是String类型，使用其他数据类型需要自行转换；也就是说我们读到 isModule 是个String类型的值，而我们需要的是Boolean值，代码如下：
```
if (isModule.toBoolean()) {
    apply plugin: 'com.android.application'
}else {
    apply plugin: 'com.android.library'
}
```
* 4.2 组件之间AndroidManifest合并问题
在 AndroidStudio 中每一个组件都会有对应的 AndroidManifest.xml，用于声明需要的权限、Application、Activity、Service、Broadcast等，当项目处于组件模式时，业务组件的 AndroidManifest.xml 应该具有一个 Android APP 所具有的的所有属性，尤其是声明 Application 和要 launch的Activity，但是当项目处于集成模式的时候，每一个业务组件的 AndroidManifest.xml 都要合并到“app壳工程”中，要是每一个业务组件都有自己的 Application 和 launch的Activity，那么合并的时候肯定会冲突，试想一个APP怎么可能会有多个 Application 和 launch 的Activity呢？
我们可以为组件开发模式下的业务组件再创建一个 AndroidManifest.xml，然后根据isModule指定AndroidManifest.xml的文件路径，让业务组件在集成模式和组件模式下使用不同的AndroidManifest.xml，这样表单冲突的问题就可以规避了。
```
sourceSets {
        main {
            if (isModule.toBoolean()) {
                manifest.srcFile 'src/main/module/AndroidManifest.xml'
            } else {
                manifest.srcFile 'src/main/AndroidManifest.xml'
            }
        }
}
```
* 4.3 组件之间调用和通信
在组件化开发的时候，组件之间是没有依赖关系，我们不能在使用显示调用来跳转页面了，因为我们组件化的目的之一就是解决模块间的强依赖问题，假如现在要从A业务组件跳转到业务B组件，并且要携带参数跳转，这时候怎么办呢？而且组件这么多怎么管理也是个问题，这时候就需要引入“路由”的概念了，由本文开始的组件化模型下的业务关系图可知路由就是起到一个转发的作用,这里我们会用到开源库的ActivityRouter。<br>
![](https://img-blog.csdnimg.cn/20190126212318172.png)
### 5. FlyTour组件化框架
* 功能展示<br><br>
![](https://img-blog.csdnimg.cn/20190126222359146.gif)
* 项目结构<br><br>
![](https://img-blog.csdnimg.cn/20190126214200270.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2dlZHVvXzgz,size_16,color_FFFFFF,t_70)<br>
* 源码地址<br><br>
欢迎加星，打call https://github.com/geduo83/FlyTour
### 6.问题反馈
在使用中有任何问题，请留言，或加入Android、Java开发技术交流群
* QQ群：810970432
* email：geduo_83@163.com
### 7.关于作者
```
var geduo_83 = {
    nickName  : "门心叼龙",
    site : "http://www.weibo.com/geduo83"
 }
```


