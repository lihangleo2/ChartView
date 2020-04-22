[![](https://jitpack.io/v/lihangleo2/ChartView.svg)](https://jitpack.io/#lihangleo2/ChartView)

# ChartView
统计图组件,其中包括2个自定义控件：
* ChartLineView 折线图统计图
* ChartCircleView 饼状统计图

#### ChartView诞生了
* [ChartView诞生日](https://github.com/lihangleo2/ChartView/wiki) 

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

## 1.2、折线统计图属性

<font color="#FF0000">我是红色字体</font> 
$\color{#4285f4}{更}\color{#ea4335}{丰}\color{#fbbc05}{富}\color{#4285f4}{的}\color{#34a853}{颜}\color{#ea4335}{色}$


### 坐标轴
* 修改坐标轴颜色 app:cl_axesColor="#ff0000"
* 修改坐标轴宽度（粗细） app:cl_axesWidth="2dp"

<img src="https://github.com/lihangleo2/ChartView/blob/master/gifs/axexAbout.png" alt="Sample"  width="350"><img src="https://github.com/lihangleo2/ChartView/blob/master/gifs/axexAbout.png" alt="Sample"  width="350">



