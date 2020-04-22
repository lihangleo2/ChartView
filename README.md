[![](https://jitpack.io/v/lihangleo2/ChartView.svg)](https://jitpack.io/#lihangleo2/ChartView)

# ChartView
统计图组件,其中包括2个自定义控件：
* ChartLineView 折线图统计图
* ChartCircleView 饼状统计图

#### ChartView诞生了
* [ChartView诞生日](https://github.com/lihangleo2/ChartView/wiki) 

<br>

# 一、折线统计图
<strong>效果展示:</strong>

![](https://github.com/lihangleo2/ChartView/blob/master/gifs/showLine1.gif)

## 扫描二维体验效果(下载密码是：123456)
![](https://github.com/lihangleo2/ChartView/blob/master/gifs/eLth.png)

<br>

## 添加依赖

 - 项目build.gradle添加如下
   ```java
   allprojects {
		repositories {
			maven { url 'https://jitpack.io' }
		}
	}
   ```
 - app build.gradle添加如下
    ```java
   dependencies {
	        implementation 'com.github.lihangleo2:ChartView:1.0.0'
	}
   ```
   
<br>

## 使用（未用到自定义属性，则使用默认值）
xml里只需要这样：

```xml
<com.lihang.chart.ChartLineView
    android:id="@+id/chartLineView"
    android:layout_width="match_parent"
    android:layout_height="200dp"
    />
```

<br>

初始化数据代码：
```java
    public void initData() {
        //横坐标titles数据 
        ArrayList<String> axesTitles = new ArrayList<>();
        arrayList.add("2");
        arrayList.add("4");
        arrayList.add("6");
        arrayList.add("8");
        arrayList.add("10");
        //设置横坐标titles
        chartLineView.setHoriItems(arrayList);


        //第一条折线数据
        ArrayList<Integer> points = new ArrayList<>();
        points.add(0);
        points.add(40);
        points.add(0);
        points.add(0);
        points.add(70);
        points.add(50);
        //第二条折线数据
        ArrayList<Integer> points_second = new ArrayList<>();
        points_second.add(0);
        points_second.add(0);
        points_second.add(30);
        points_second.add(30);
        //初始化折线统计图item
        ArrayList<ChartLineItem>items = new ArrayList<>();
        /*
        * 参数：
        * 1、折线统计的数据源
        * 2、此折线的颜色值
        * 3、手势操作后，展示此折线数据的描述语
        * 4、此折线是否带阴影填充色
        * 5、此折线是否带动画展示
        * */
        items.add(new ChartLineItem(points, R.color.red, "昨日", true, true));
        items.add(new ChartLineItem(points_second, R.color.black, "今日", true, true));
        //设置折线数据源
        binding.chartLineView.setItems(items);
    }
```

<br>
<br>

## 折线统计图自定义属性
在我们还未设置任何属性的时候，我们的坐标轴长这样：

<img src="https://github.com/lihangleo2/ChartView/blob/master/gifs/source1.png" alt="Sample"  width="350">

<br>
<br>

#### 1、坐标轴颜色  app:cl_axesColor="#ff0000"
#### 2、坐标轴宽度（粗细）</strong>  app:cl_axesWidth="2dp"

修改后如图：

<img src="https://github.com/lihangleo2/ChartView/blob/master/gifs/axexAbout.png" alt="Sample"  width="350">

<br>
<br>

#### 3、刻度值颜色  app:cl_divideColor="#ff0000"
#### 4、刻度值宽度（粗细）  app:cl_divideWith="2dp"
#### 5、刻度值高度  app:cl_divideHeight="5dp"

修改后如图：

<img src="https://github.com/lihangleo2/ChartView/blob/master/gifs/divideAbout.png" alt="Sample"  width="350">

<br>
<br>

#### 6、是否隐藏奇数刻度值（解决刻度值过密） app:cl_divide_hideOdd="true" 

图1为刻度值过密 --> 图2为隐藏奇数刻度值

<img src="https://github.com/lihangleo2/ChartView/blob/master/gifs/moreDivide1.png" alt="Sample"  width="350"><img src="https://github.com/lihangleo2/ChartView/blob/master/gifs/hideOdd2.png" alt="Sample"  width="350">

<br>
<br>

#### 7、坐标轴文字颜色  app:cl_textColor="#ff0000"
#### 8、坐标轴文字大小  app:cl_textSize="15dp"

修改后如图：

<img src="https://github.com/lihangleo2/ChartView/blob/master/gifs/textAbout.png" alt="Sample"  width="350">

<br>
<br>

#### 9、纵轴最大值  app:cl_max="1000"
#### 10、纵轴分几段  app:cl_span="4"
> 这里要注意，如果传入的数值中，有比最大值还大，那么最终最大值为  传入数值最大值+设置最大值/4 

修改后如图：

<img src="https://github.com/lihangleo2/ChartView/blob/master/gifs/maxAndSpan.png" alt="Sample"  width="350">

<br>
<br>

#### 11、是否隐藏Y轴  app:cl_hideY="true"

修改后如图：

<img src="https://github.com/lihangleo2/ChartView/blob/master/gifs/hideY.png" alt="Sample"  width="350">

<br>
<br>

#### 12、Y轴刻度值虚线是否显示  app:cl_Y_showDash="true"
#### 13、Y轴刻度值虚线颜色  app:cl_Y_dashColor="#CCCCCC"
#### 14、Y轴刻度值虚线宽度（粗细）  app:cl_Y_dashWith="1dp"
#### 15、Y轴刻度值虚线间隔  app:cl_Y_dashDivide="10dp"
#### 16、Y轴刻度值虚线的实线长度  app:cl_Y_dash_solidLength="5dp"

修改后如图：

<img src="https://github.com/lihangleo2/ChartView/blob/master/gifs/yDash.png" alt="Sample"  width="350">

<br>
<br>

#### 17、手势虚线颜色 app:cl_dashColor="#ff0000"
#### 18、手势虚线宽度（粗细）  app:cl_dashWith="1dp"
#### 19、手势虚线间隔  app:cl_dashDivide="10dp"
#### 20、手势虚线的实线长度  app:cl_dash_solidLength="5dp"
#### 21、手指离开后多久，虚线消失  app:cl_dashStay_duration="1500"（-1表示常驻不会消失）
#### 22、是否禁止手势操作  app:cl_isOnTouch="false"

修改后如图：

<img src="https://github.com/lihangleo2/ChartView/blob/master/gifs/dashOnTouch.png" alt="Sample"  width="350">

<br>
<br>

#### 23、提醒背景颜色  app:cl_remind_backColor="#00BCD4"
#### 24、提醒文字颜色  app:cl_remind_textColor="#ff0000"
#### 25、提醒文字大小  app:cl_remind_textSize="15dp"

修改后如图：

<img src="https://github.com/lihangleo2/ChartView/blob/master/gifs/remind.png" alt="Sample"  width="350">

<br>
<br>

#### 26、折线图动画时间  app:cl_lineAnim_duration="1500"

<br>
<br>

# 二、饼状统计图
同样我们先看看饼状统计图的效果：

|添加/移除item|初始角度startAngle|圆环or扇形|
|:---:|:---:|:---:|
|![](https://github.com/lihangleo2/ChartView/blob/master/gifs/circle/addItem1.gif)|![](https://github.com/lihangleo2/ChartView/blob/master/gifs/circle/startAngle2.gif)|![](https://github.com/lihangleo2/ChartView/blob/master/gifs/circle/isArc3.gif)
|圆环比率|
|![](https://github.com/lihangleo2/ChartView/blob/master/gifs/circle/circleRate4.gif)|

<br>

## 使用(未使用自定义属性，则使用默认值)
```xml
<com.lihang.chart.ChartCircleView
    android:id="@+id/charCircleView"
    android:layout_width="wrap_content"
    android:layout_height="200dp" />
```

<br>

初始化数据代码：
```java
    private void initData() {
        ArrayList<ChartCircleItem> items = new ArrayList<>();
        /*
        * 参数：
        * 1、当前的value的值
        * 2、绘制此部分的颜色值
        * 3、此部分的文字描述
        * */
        items.add(new ChartCircleItem(1, R.color.yellow, "原价"));
        items.add(new ChartCircleItem(3, R.color.blue, "优惠"));
        //设置数据源
        charCircleView.setItems(items);
    }
```

这样你就能用了。soEasy！

<br>
<br>

## 饼状统计图自定义属性

#### 1、文字大小  app:cv_textSize="16sp"
- 这里指的是demo中原价/优惠的字体大小
<br>

#### 2、文字颜色  app:cv_textColor="#ff0000"
- demo中原价/优惠字体颜色
<br>

#### 3、初始旋转角度  app:cv_startAngle="-90"
- 可以控制统计图从哪个角度开始启动。默认是0度
<br>

#### 4、圆环比率  app:cv_rate="0.68"
- 圆环比率，这里可以简单认为是控制圆环粗细的属性
<br>

#### 5、扇形or圆环  app:cv_isArc="true"
- 改变控件外观。默认为圆环状，通过app:cv_isArc="true"可改变为扇形
<br>

#### 6、圆环动画时间  app:cv_animDuration="1500"
- 圆环开始到结束的动画时间
<br>

#### 是否需要动画  app:cv_isAnim="true"
- 圆环是否需要动画。这里要注意也可以动态改变，最终以代码为最终结果
```java
//改变是否需要动画有2种方法
//方法1.
charCircleView.setAnim(true);

//方法2.（设置数据的时候）
charCircleView.setItems(true,items);

```

<br>

## 关于作者。
Android工作多年了，一直向往大厂。在前进的道路上是孤独的。如果你在学习的路上也感觉孤独，请和我一起。让我们在学习道路上少些孤独
* [关于我的经历](https://mp.weixin.qq.com/s?__biz=MzAwMDA3MzU2Mg==&mid=2247483667&idx=1&sn=1a575ea2c636980e5f4c579d3a73d8ab&chksm=9aefcb26ad98423041c61ad7cbad77f0534495d11fc0a302b9fdd3a3e6b84605cad61d192959&mpshare=1&scene=23&srcid=&sharer_sharetime=1572505105563&sharer_shareid=97effcbe7f9d69e6067a40da3e48344a#rd)
 * QQ群： 209010674 <a target="_blank" href="//shang.qq.com/wpa/qunwpa?idkey=5e29576e7d2ebf08fa37d8953a0fea3b5eafdff2c488c1f5c152223c228f1d11"><img border="0" src="http://pub.idqqimg.com/wpa/images/group.png" alt="android交流群" title="android交流群"></a>（点击图标，可以直接加入）

<br>

## Licenses

```
MIT License

Copyright (c) 2020 leo

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
```


