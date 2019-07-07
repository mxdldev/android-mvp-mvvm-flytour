<p align="center">
   <a href="https://github.com/geduo83/FlyTour/releases">
    <img src="https://img.shields.io/github/release/geduo83/FlyTour.svg" alt="Latest Stable Version" />
  </a>
   <a href="https://developer.android.com/about/versions/android-4.0.html">
    <img src="https://img.shields.io/badge/API-14%2B-blue.svg?style=flat-square" alt="Min Sdk Version" />
  </a>
  <a href="http://www.apache.org/licenses/LICENSE-2.0">
    <img src="http://img.shields.io/badge/License-Apache%202.0-blue.svg?style=flat-square" alt="License" />
  </a>
  <a href="https://menxindiaolong.blog.csdn.net">
    <img src="https://img.shields.io/badge/Author-%E9%97%A8%E5%BF%83%E5%8F%BC%E9%BE%99-orange.svg?style=flat-square" alt="Author" />
  </a>
  <a href="https://menxindiaolong.blog.csdn.net">
    <img src="https://img.shields.io/badge/QQ%E7%BE%A4-810970432%20%7C%20303704981-orange.svg?style=flat-square" alt="QQ Group" />
  </a>
</p>
欢迎加星，打call https://github.com/geduo83/FlyTour
在使用中有任何问题，请留言，或加入Android、Java开发技术交流群<br>
* QQ群：810970432<br>
* email：303704981@qq.com

![](https://img-blog.csdnimg.cn/20190616183350269.png)

### 更新日志：
###### FlyTour 4.1.0【DataBinding版】 2019-07-07
组件化+MVVM+DataDinding+RxJava+Retrofit，在4.0.0的MVVM架构基础之上增加了DataBinding的特性，配合FlyCloud微服务系统共同使用
* 增加了DataBinding的特性

###### FlyTour 4.0.0【MVVM版】 2019-07-03
组件化+MVVM+RxJava+Retrofit，基本架构由3.0.0的MVP架构升级为MVVM架构，配合FlyCloud微服务系统共同使用
* MVVM架构彻底改版

###### FlyTour 3.0.0【网络版】 2019-06-26
组件化+MVP+RxJava+Retrofit+Dagger2,该版本是在2.1.0的版本的基础之上对Model层的数据源做了升级，由本地数据源升级为网络数据源，配合FlyCloud微服务系统共同使用
* 网络请求部分由Retrofit+RxJava完成

###### FlyTour 2.1.0【Dagger版】 2019-06-06
组件化+MVP+Dagger2，该版本在2.0.0的基础上添加了Dagger2的特性，Present、Model的创建都由Dagger2自动完成
* 1.Presenter实例的创建由Dagger2完成
* 2.Modle实例的创建有Dagger2完成
* 3.DAO实例的创建由Dagger2完成

###### FlyTour 2.0.0【稳定单机版】 2019-05-30
基本架构为组件化+MVP，数据由本地SQLLite数据库提供
以新闻资讯为功能，对app的界面进行了全新的改版，由新闻列表展示、新闻详情展示、新闻添加、新闻类型添加、删除这几个简单的功能组成，基本上覆盖了整个框架的所有核心的、常用的一些功能
* 新闻类型添加、删除、展示
* 新闻添加、展示
* 支持是否启用ToolBar
* 支持自定义ToolBar
* 支持loading加载数据
* 支持透明loading的加载数据
* 支持显示无数据
* 支持网络网络错误显示
* 支持Fragment的懒加载
* 支持最基本的下拉刷新、上拉加载更多
* 支持自定义HeadView和FootView
* 支持自动刷新
* 支持启用、禁用下拉刷新
* 支持启用、进攻上拉加载更多
* 通用小菊花样式DaisyRefreshLayout
* 通用小箭头样式ArrowRefreshLayout

###### FlyTour 1.1.0【优化版】 2019-03-34
* MVP功能的一些优化

###### FlyTour 1.0.0【初始版】 2019-01-26 
* 初始版本，以车辆运动轨迹大数据采集为功能简单的实现了组件化和MVP的基本功能
### 功能演示
* 新闻下来刷新、无数据、无网络、新闻详情展示
<div>
<img src="https://img-blog.csdnimg.cn/2019053012120717.gif">
<img align="right" src="https://img-blog.csdnimg.cn/20190530121236658.gif">
</div>
 
 * 新闻类型添加、新闻添加
<div>
 <img src="https://img-blog.csdnimg.cn/20190530121927773.gif">
 <img align="right" src="https://img-blog.csdnimg.cn/20190530122102683.gif">
</div>

### 主要功能
### 核心公共组件lib_common
七的核心公用基类
* BaseActivity
```
public abstract class BaseActivity extends RxAppCompatActivity implements BaseView {
...
}
```
* BaseMvpActivity
```
public abstract class BaseMvpActivity<M extends BaseModel,V,P extends BasePresenter<M,V>> extends BaseActivity {
...
}
```
* BaseRefreshActivity
```
public abstract class BaseRefreshActivity<M extends BaseModel, V extends BaseRefreshView<T>, P extends BaseRefreshPresenter<M, V, T>, T> extends BaseMvpActivity<M, V, P> implements BaseRefreshView<T> {
}
```
* BaseFragment
* BaseMvpFragment
* BaseRefreshFragment
* BaseAdapter
### 功能特色：
* 支持是否使用ToolBar
```
public boolean enableToolbar() {
    return true;
}
```
* 支持自定义ToolBar
```
public int onBindToolbarLayout() {
   return R.layout.common_toolbar;
}
```
* 支持loading加载数据
```
  public void showInitLoadView() {
        showInitLoadView(true);
    }

    public void hideInitLoadView() {
        showInitLoadView(false);
    }
```
* 支持透明loading的加载数据
```
  @Override
    public void showTransLoadingView() {
        showTransLoadingView(true);
    }

    @Override
    public void hideTransLoadingView() {
        showTransLoadingView(false);
    }
```
* 支持显示无数据
```
    public void showNoDataView() {
        showNoDataView(true);
    }

    public void showNoDataView(int resid) {
        showNoDataView(true, resid);
    }

    public void hideNoDataView() {
        showNoDataView(false);
    }
```

* 支持网络网络错误显示
```
public void hideNetWorkErrView() {
        showNetWorkErrView(false);
    }

    public void showNetWorkErrView() {
        showNetWorkErrView(true);
    }

```

* 支持Fragment的懒加载
```
  private void lazyLoad() {
        //这里进行双重标记判断,必须确保onCreateView加载完毕且页面可见,才加载数据
         if (isViewCreated && isViewVisable) {
            initData();
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isViewVisable = false;
        }
    }
    //默认不启用懒加载
    public boolean enableLazyData() {
        return false;
    }
```
### 上拉下拉功能组件 lib_refresh_layout
* 支持最基本的下拉刷新、上拉加载更多
* 支持自定义HeadView和FootView
* 支持自动刷新
* 支持启用、禁用下拉刷新
* 支持启用、进攻上拉加载更多
* 通用小菊花样式DaisyRefreshLayout
* 通用小箭头样式ArrowRefreshLayout

### 项目架构
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190530154504970.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9tZW54aW5kaWFvbG9uZy5ibG9nLmNzZG4ubmV0,size_16,color_FFFFFF,t_70)
* 集成模式：所有的业务组件被“app壳工程”依赖，组成一个完整的APP；
* 组件模式：可以独立开发业务组件，每一个业务组件就是一个APP；
* app壳工程：负责管理各个业务组件，和打包apk，没有具体的业务功能；
* 业务组件：根据公司具体业务而独立形成一个的工程；
* 功能组件：提供开发APP的某些基础功能，例如打印日志、下拉刷新控件等；
* Main组件：属于业务组件，指定APP启动页面、主界面；
* Common组件：属于功能组件，支撑业务组件的基础，提供多数业务组件需要的功能


### MVP架构
这是整个项目的mvp结构图：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190530144533636.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9tZW54aW5kaWFvbG9uZy5ibG9nLmNzZG4ubmV0,size_16,color_FFFFFF,t_70)
* Activity关系图：<br>
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190407122859375.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2dlZHVvXzgz,size_16,color_FFFFFF,t_70)
### Fragment的类关系图
Fragment的类关系图和Activity类似具体详见common组件下的base包和mvp包

### 组件化实现：

FlyTour新闻客户端使用阿里ARouter作为路由，实现组件与组件的通信跳转

### 集成模式和组件模式转换
Module的属性是在每个组件的 build.gradle 文件中配置的，当我们在组件模式开发时，业务组件应处于application属性，这时的业务组件就是一个 Android App，可以独立开发和调试；而当我们转换到集成模式开发时，业务组件应该处于 library 属性，这样才能被我们的“app壳工程”所依赖，组成一个具有完整功能的APP

先打开FlyTour工程的根目录下找到gradle.properties 文件，然后将 isModule 改为你需要的开发模式（true/false）， 然后点击 "Sync Project" 按钮同步项目
```
isModule=false
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190530142030271.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9tZW54aW5kaWFvbG9uZy5ibG9nLmNzZG4ubmV0,size_16,color_FFFFFF,t_70)
```
if (isModule.toBoolean()) {
    apply plugin: 'com.android.application'
} else {
    apply plugin: 'com.android.library'
}
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190530142043496.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9tZW54aW5kaWFvbG9uZy5ibG9nLmNzZG4ubmV0,size_16,color_FFFFFF,t_70)
### 组件之间AndroidManifest合并问题
我们可以为组件开发模式下的业务组件再创建一个 AndroidManifest.xml，然后根据isModule指定AndroidManifest.xml的文件路径，让业务组件在集成模式和组件模式下使用不同的AndroidManifest.xml，这样表单冲突的问题就可以规避了
已module_main组件为例配置如下：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190530150350275.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9tZW54aW5kaWFvbG9uZy5ibG9nLmNzZG4ubmV0,size_16,color_FFFFFF,t_70)
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
### 组件模式下的Application
在每个组件的debug目录下创建一个Application并在module下的AndroidManifest.xml进行配置
配图：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190530142154452.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9tZW54aW5kaWFvbG9uZy5ibG9nLmNzZG4ubmV0,size_16,color_FFFFFF,t_70)
### 集成开发模式下的Application
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190530150933283.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9tZW54aW5kaWFvbG9uZy5ibG9nLmNzZG4ubmV0,size_16,color_FFFFFF,t_70)

### 问题反馈
欢迎加星，打call https://github.com/geduo83/FlyTour
在使用中有任何问题，请留言，或加入Android、Java开发技术交流群
* QQ群：810970432
* email：geduo_83@163.com
![](https://img-blog.csdnimg.cn/20190126213618911.png)
### 关于作者
```
var geduo_83 = {
    nickName  : "门心叼龙",
    site : "http://www.weibo.com/geduo83"
 }
```
### License
```
Copyright (C)  menxindiaolong, FlyTour Framework Open Source Project

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
