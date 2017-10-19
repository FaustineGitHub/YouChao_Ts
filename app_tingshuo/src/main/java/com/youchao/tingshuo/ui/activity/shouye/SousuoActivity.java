package com.youchao.tingshuo.ui.activity.shouye;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.youchao.tingshuo.R;
import com.youchao.tingshuo.db.RecordSQLiteOpenHelper;
import com.youchao.tingshuo.ui.adapter.shouye.SearchAdapter;
import com.youchao.tingshuo.ui.base.BaseActivity;
import com.youchao.tingshuo.utils.L;
import com.youchao.tingshuo.utils.MToast;
import com.youchao.tingshuo.view.FullyLinearLayoutManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mxx on 2016/10/20.
 * 搜索
 * 功能存在bug,删除到最后一行时报错
 * 删除时，list出现重复数据
 */
public class SousuoActivity extends BaseActivity{
    @Bind(R.id.et_search)
    EditText mEtSearch;
    @Bind(R.id.cancel_search)
    TextView mCancelSearch;
    @Bind(R.id.tv_tip)
    TextView mTvTip;
    @Bind(R.id.tv_clear)
    TextView mTvClear;
    @Bind(R.id.search_recyclerview)
    RecyclerView mSearchRecyclerview;

    private Context mContext;
    //ListView
    private List<String[]> listDatas = new ArrayList<>();
    private SearchAdapter mSearchAdapter;

    //SQLite
    /*数据库变量*/
    private RecordSQLiteOpenHelper helper ;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    public int initResource() {
        return R.layout.activty_search_oeder_item1;
    }

    @Override
    protected void initComponent() {
        mTvClear.setVisibility(View.GONE);

    }

    @Override
    public void initData() {
        mContext = this;
        final FullyLinearLayoutManager manager = new FullyLinearLayoutManager(this);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        manager.setSmoothScrollbarEnabled(true);
        mSearchRecyclerview.setLayoutManager(manager);
        //实例化数据库SQLiteOpenHelper子类对象
        helper = new RecordSQLiteOpenHelper(mContext);
        // 第一次进入时查询所有的历史记录
        queryData("");
        mSearchAdapter = new SearchAdapter(listDatas,this);
        initListener();
        if (listDatas.size() == 0) {
            mTvTip.setVisibility(View.GONE);
        }else {
            mTvTip.setVisibility(View.VISIBLE);
        }
    }

    public void initListener() {
        //"清空搜索历史"按钮
        mTvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //清空数据库
                deleteData();
                queryData("");
            }
        });
        mCancelSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                L.e("TAG",mEtSearch.getText().length()+"");
                if (mEtSearch.getText().length() == 0){
                    finish();
                }else {
                    queryData("");
                    mEtSearch.setText("");
                }

            }
        });
        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() == 0) {
                    //若搜索框为空,则模糊搜索空字符,即显示所有的搜索历史
                    mTvTip.setText("搜索历史");
                } else {
                    mTvTip.setText("搜索结果");
                }
                //每次输入后都查询数据库并显示
                //根据输入的值去模糊查询数据库中有没有数据
                String tempName = mEtSearch.getText().toString();
                queryData(tempName);
            }
        });
        mSearchAdapter.setOnTotalItemClickListener(new SearchAdapter.OnTotalItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //获取到用户点击列表里的文字,并自动填充到搜索框内
                TextView textView = v.findViewById(R.id.tv_item_search);
                String name = textView.getText().toString();
                mEtSearch.setText(name);
            }
        });
        mSearchAdapter.setOnDeleteClickListener(new SearchAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(View v, int position) {
                if (listDatas.size()>0) {
                    String name = listDatas.get(position)[1];
                    deleteWhereData(name);
                    L.e("TAG", "删除 = " + name + position);
                    listDatas.remove(position);
                }
                mSearchAdapter.notifyDataSetChanged();
                MToast.showToast(mContext,"删除");

            }
        });
        // 搜索框的键盘搜索键
        // 点击回调
        mEtSearch.setOnKeyListener(new View.OnKeyListener() {// 输入完后按键盘上的搜索键
            // 修改回车键功能
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    // 隐藏键盘，这里getCurrentFocus()需要传入Activity对象，如果实际不需要的话就不用隐藏键盘了，免得传入Activity对象，这里就先不实现了
                  /*((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                          .getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);*/
                    // 按完搜索键后将当前查询的关键字保存起来,如果该关键字已经存在就不执行保存
                    boolean hasData = hasData(mEtSearch.getText().toString().trim());
                    if (!hasData) {
                        insertData(mEtSearch.getText().toString().trim());
                        String tempName = mEtSearch.getText().toString();
                        queryData(tempName);
                        //queryData("");
                    }
                    //根据输入的内容模糊查询商品，并跳转到另一个界面，这个需要根据需求实现
                    Toast.makeText(mContext, "点击搜索", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        //清空事件监听
        //tv_item_list_sousuo.setOnClickListener(this);
    }
    /**
     * 封装的函数
     */
    /*插入数据*/
    private void insertData(String tempName) {
        sqLiteDatabase = helper.getWritableDatabase();
        sqLiteDatabase.execSQL("insert into records(name) values('" + tempName + "')");
        sqLiteDatabase.close();
    }
    /*检查数据库中是否已经有该条记录*/
    private boolean hasData(String tempName) {
        //从Record这个表里找到name=tempName的id
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name =?", new String[]{tempName});
        //判断是否有下一个
        return cursor.moveToNext();
    }
    /*模糊查询数据 并显示在ListView列表上*/
    private void queryData(String tempName) {
        //模糊搜索
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name like '%" + tempName + "%' order by id desc ", null);
        listDatas.clear();
        for (int i = cursor.getCount(); i > 0; i--) {
            cursor.moveToPosition(i - 1);
            //获得记录
            String[] str = {cursor.getString(0),cursor.getString(1)};
            listDatas.add(str);
        }
        // 创建adapter适配器对象,装入模糊搜索的结果
        // 设置适配器
        mSearchRecyclerview.setAdapter(mSearchAdapter);
    }
    /*清空某条数据*/
    private void deleteWhereData(String tempName) {
        sqLiteDatabase = helper.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from records where name = ('" + tempName + "')");
        sqLiteDatabase.close();
    }

    /*清空数据*/
    private void deleteData() {
        sqLiteDatabase = helper.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from records");
        sqLiteDatabase.close();
    }



    private void getShangPinInfo(String str) {
        //requestHandleArrayList.add(requestAction.shop_hp_atuohint(this, str));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //关闭数据库
        sqLiteDatabase.close();
    }

    @Override
    public void requestSuccess(int requestTag, JSONObject response, int showLoad) throws JSONException {
        super.requestSuccess(requestTag, response, showLoad);
        switch (requestTag) {
            /*case RequestAction.TAG_SHOP_HP_ATUOHINT:
                if ("成功".equals(response.getString("状态"))) {
                    listDatas.clear();
                    JSONArray jsonArray = response.getJSONArray("数据");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        listDatas.add(jsonArray.getString(i));
                    }
                    commonAdapter.notifyDataSetChanged();
                }
                break;*/
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
