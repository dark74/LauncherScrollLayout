package com.dk.asandroidlauncher;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.util.Log;

import com.dk.asandroidlauncher.adapter.ScrollAdapter;
import com.dk.asandroidlauncher.bean.MoveItem;
import com.dk.asandroidlauncher.widget.ScrollLayout;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("HandlerLeak")
public class MainActivity extends Activity implements ScrollLayout.OnAddOrDeletePage,
        ScrollLayout.OnPageChangedListener, ScrollLayout.OnEditModeListener {
    private static final String TAG = "MainActivity";
    // 滑动控件的容器Container
    private ScrollLayout mContainer;

    // Container的Adapter
    private ScrollAdapter mItemsAdapter;
    // Container中滑动控件列表
    private List<MoveItem> mList;

    //xUtils中操纵SQLite的助手类
    private DbUtils mDbUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 从缓存中初始化滑动控件列表
        getDataFromCache();
        // 初始化控件
        initView();
        //初始化容器Adapter
        loadBackground();
    }

    private void getDataFromCache() {
        mDbUtils = DbUtils.create(this);
        try {
            //使用xUtils，基于orderId从SQLite数据库中获取滑动控件
            mList = mDbUtils.findAll(Selector.from(MoveItem.class).orderBy("orderId", false));
            if (mList != null) {
                Log.d(TAG, "getDataFromCache- list size:" + mList.size());
            } else {
                Log.d(TAG, "getDataFromCache- list 为空 ");
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        mContainer = (ScrollLayout) findViewById(R.id.container);
        //如果没有缓存数据，则手动添加10条
        if (mList == null || mList.size() == 0) {
            mList = new ArrayList<MoveItem>();
            for (int i = 1; i < 11; i++) {
                MoveItem item = new MoveItem();
                //根据drawable name获取对于的ID
                item.setImgdown(getDrawableId("item" + i + "_down"));
                item.setImgurl(getDrawableId("item" + i + "_normal"));
                item.setOrderId(i);
                item.setMid(i);
                mList.add(item);
            }
        }
        //初始化Container的Adapter
        mItemsAdapter = new ScrollAdapter(this, mList);
        //设置Container添加删除Item的回调
        mContainer.setOnAddPage(this);
        //设置Container页面换转的回调，比如自第一页滑动第二页
        mContainer.setOnPageChangedListener(this);
        //设置Container编辑模式的回调，长按进入修改模式
        mContainer.setOnEditModeListener(this);
        //设置Adapter
        mContainer.setSaAdapter(mItemsAdapter);
        //动态设置Container每页的列数为2行
        mContainer.setColCount(2);
        //动态设置Container每页的行数为4行
        mContainer.setRowCount(4);
        //调用refreView绘制所有的Item
        mContainer.refreView();
    }

    // 设置Container滑动背景图片
    private void loadBackground() {
        Options options = new Options();
        options.inSampleSize = 2;
        mContainer.setBackGroud(BitmapFactory.decodeResource(getResources(),
                R.mipmap.main_bg, options));
    }

    private int getDrawableId(String name) {
        return getResources().getIdentifier(name, "mipmap", "com.dk.customlauncher");
    }

    @Override
    public void onBackPressed() {
        //back键监听，如果在编辑模式，则取消编辑模式
        if (mContainer.isEditting()) {
            mContainer.showEdit(false);
            return;
        } else {
            try {
                //退出APP前，保存当前的Items，记得所有item的位置
                List<MoveItem> list = mContainer.getAllMoveItems();
                mDbUtils.saveAll(list);
            } catch (DbException e) {
                e.printStackTrace();
            }
            super.onBackPressed();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    @Override
    public void onEdit() {
        Log.e("test", "onEdit");
    }

    @Override
    public void onPage2Other(int former, int current) {
        Log.e("test", "former-->" + former + "  current-->" + current);
    }

    public void onAddOrDeletePage(int page, boolean isAdd) {
        Log.e("test", "page-->" + page + "  isAdd-->" + isAdd);
    }
}
