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

## 折线统计图属性
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



