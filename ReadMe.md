## 一、前言

自定义 View 是 Android 中高级工程师进阶的必经之路，要想熟练掌握自定义 View 技能，View 绘制流程和 View 事件分发机制必须掌握的，开发过程中大多数情况下都能在网上找到类似的效果，可能修修改改也能满足项目需求，但是一旦遇到比较棘手的问题，可能就会让开发者很苦恼。

本篇文章是自定义 View 结合 View 事件分发实现一个赛事得分表效果。

如果对自定义 View 不熟悉的朋友可以参考以下文章：

>-  [Android 自定义View（一）实现时钟表盘效果](https://blog.csdn.net/jaynm/article/details/106385195)
>-  [Android 自定义View（二）实现环形进度条](https://blog.csdn.net/jaynm/article/details/106467265)

## 二、准备工作

### 1、先看效果
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200603084046845.gif#pic_center)
### 2、应用知识点

1. 自定义 View
2. View 事件分发机制
3. 设计模式-观察者模式

### 3、思路分析

1. 根据效果图首先明确主页面是一个列表；
2. 头部 Tab 栏可以水平滑动，所以父布局使用 HorizontalScrollView 实现；
3. 列表 Item 分 2 部分，一部分是[**头像和昵称**]，另一部分是[**积分**]；
4. 头部 Tab 栏水平滑动时，列表 Item 第二部分同步滑动；
5. 熟悉事件分发的朋友都知道，看到这样的布局效果，肯定需要解决滑动冲突；

## 三、代码实现

### 1、自定义横向滚动控件

- 因为上面已经分析，头部 Tab 栏父布局和 item 第二部分同步滑动，所以都采用横向滚动控件完成，所以自定义横向滚动控件，继承 HorizontalScrollView 并重写构造方法，这里重写了 3 个构造方法。

```java
public class CustomHScrollView extends HorizontalScrollView {

    ScrollViewObserver mScrollViewObserver = new ScrollViewObserver();

    public CustomHScrollView(Context context) {
        super(context);
    }

    public CustomHScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomHScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
```

- **定义外部可回调接口，当发生了滚动事件时接口，供外部访问**

```java
public static interface OnScrollChangedListener {
    public void onScrollChanged(int l, int t, int oldl, int oldt);
}
```

- 定义一个观察者，用于滑动控件时，接收水平滑动的回调。并定义添加滚动监听和移除滚动监听的方法。

```kotlin
public static class ScrollViewObserver {
    List<OnScrollChangedListener> mChangedListeners;

    public ScrollViewObserver() {
        super();
        mChangedListeners = new ArrayList<OnScrollChangedListener>();
    }
    //添加滚动事件监听
    public void AddOnScrollChangedListener(OnScrollChangedListener listener) {
        mChangedListeners.add(listener);
    }
    //移除滚动事件监听
    public void RemoveOnScrollChangedListener(OnScrollChangedListener listener) {
        mChangedListeners.remove(listener);
    }
    //回调
    public void NotifyOnScrollChanged(int l, int t, int oldl, int oldt) {
        if (mChangedListeners == null || mChangedListeners.size() == 0) {
            return;
        }
        for (int i = 0; i < mChangedListeners.size(); i++) {
            if (mChangedListeners.get(i) != null) {
                mChangedListeners.get(i).onScrollChanged(l, t, oldl, oldt);
            }
        }
    }
}
```

- 因为父布局是水平滚动 HorizontalScrollView，所以需要重写 onScrollChanged()方法，监听滑动变化。当观察者 mScrollViewObserver 不为 null 时，滑动监听通知回调观察者 ScrollViewObserver。

```java
@Override
protected void onScrollChanged(int l, int t, int oldl, int oldt) {
    //滚动时通知
    if (mScrollViewObserver != null) {
        mScrollViewObserver.NotifyOnScrollChanged(l, t, oldl, oldt);
    }
    super.onScrollChanged(l, t, oldl, oldt);
}
```

以上水平滚动 HorizontalScrollView 基本上已经完成，上面预留外部访问接口：OnScrollChangedListener，在 Tab 栏水平滑动时，可以在 Adapter 中获取滚动水平、垂直滚动位置，来设置 item 中第二部分控件位置。

### 2、完善列表

列表页采用 RecyclerView 完成，这部分不做过多描述，感兴趣的朋友可以下载源码查看。

- 这里对 RecyclerView.Adapter 中的处理，描述说明。在 ViewHolder 初始化控件时，将 Tab 栏的 ScrollView（被观察者）和 item 中的 ScrollView（观察者）完成订阅。当 Tab 栏发生水平滚动时，实时通知观察者完成界面刷新。

```java
// item列表中的ScrollView
CustomHScrollView scrollView = itemView.findViewById(R.id.h_scrollView);
// Tab栏的ScrollView
CustomHScrollView headSrcrollView =  mHead.findViewById(R.id.h_scrollView);
// 订阅
headSrcrollView.AddOnScrollChangedListener(new OnScrollChangedListenerImp(scrollView));
```

- 在 Adapter 中新建 OnScrollChangedListenerImp 类，继承自定义 CustomHScrollView 中的 OnScrollChangedListener 接口，上文提到，该接口是当发生了滚动事件时接口，供外部访问。

- 实现 onScrollChanged()方法，调用 HorizontalScrollView.smoothScrollTo()方法实现 item 中 HorizontalScrollView 滚动位置与 Tab 栏滚动实时同步效果。

**需要注意的是，这里需要记录一下初始化位置，避免因刷新数据引起错乱。**

```java
class OnScrollChangedListenerImp implements CustomHScrollView.OnScrollChangedListener {
    CustomHScrollView mScrollViewArg;

    public OnScrollChangedListenerImp(CustomHScrollView scrollViewar) {
        mScrollViewArg = scrollViewar;
    }

    @Override
    public void onScrollChanged(int l, int t, int oldl, int oldt) {
        mScrollViewArg.smoothScrollTo(l, t);
        //记录滚动的起始位置，避免因刷新数据引起错乱
        if (n == 1) {
            setPosData(oldl, oldt);
        }
        n++;
    }
}
```

### 3、解决滑动冲突

完成以上步骤后，我们发现，ListView 的垂直滑动和和 ListView 中 item 水平 ScrollView 滑动发生冲突，如果需要解决这个问题，就需要应用到我们 View 事件分发机制了，如果对事件分发机制和流程还不熟悉的朋友，可以先学习下。

- 首先，当在表头和 listView 控件上 Touch 时，我们都需要将事件分发给 ScrollView 处理，所以根据 View 事件分发机制，定义一个父布局 InterceptScrollLinearLayout 继承 LinearLayout，并且做时间拦截处理。

```java
@Override
public boolean onInterceptTouchEvent(MotionEvent ev) {
    return true;
}
```

- 然后在 Activity 中创建 OnTouchListener 子类，重写 onTouch()方法，并且让父控件 ListView 控件分别调用 setOnTouchListener()方法注册回调函数，以便在触摸事件发送到此视图时调用。

```java
class MyTouchLinstener implements View.OnTouchListener {

    @Override
    public boolean onTouch(View arg0, MotionEvent arg1) {
        //当在表头和listView控件上touch时，将事件分发给 ScrollView
        mScrollView.onTouchEvent(arg1);
        return false;
    }
}
```

```java
mHead.setOnTouchListener(new MyTouchLinstener());
recycler.setOnTouchListener(new MyTouchLinstener());
```

综上，就已经完成文章开头展示的效果图样式，希望本文对 Android 初学者有所帮助，在自定义 View 和 View 事件分发知识学习过程中可以有更深的理解。文章中只粘贴了部分代码，需要完整代码的可以下载代码参考。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200602215553123.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2pheW5t,size_16,color_FFFFFF,t_70)