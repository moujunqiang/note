package com.android.memo.UI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.memo.Adapter.TypeListAdapter;
import com.android.memo.Bean.TypeBean;
import com.android.memo.DB.TypeDao;
import com.android.memo.R;

import java.util.List;

public class TypeManagerActivity extends AppCompatActivity {
    private RecyclerView rvType;
    List<TypeBean> typeBeans;
    TypeListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_manager);
        rvType = (RecyclerView) findViewById(R.id.rv_type);
        rvType.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TypeListAdapter();
        TypeDao typeDao = new TypeDao(this);


        typeBeans = typeDao.queryTypesAll();

        adapter.setTypes(typeBeans);
        rvType.setAdapter(adapter);
        adapter.setOnItemLongClick(new TypeListAdapter.OnLoginClick() {
            @Override
            public void onClick(int position) {
                showDeleteDialog(position);
            }
        });
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        findViewById(R.id.tv_note_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog();
            }
        });

    }

    private void showInputDialog() {
        /*@setView 装入一个EditView
         */
        final EditText editText = new EditText(TypeManagerActivity.this);
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(TypeManagerActivity.this);
        inputDialog.setTitle("请输入分组名称").setView(editText);
        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!TextUtils.isEmpty(editText.getText().toString())) {
                            TypeBean typeBean = new TypeBean(editText.getText().toString());
                            new TypeDao(TypeManagerActivity.this).insertNote(typeBean);
                            typeBeans = new TypeDao(TypeManagerActivity.this).queryTypesAll();
                            adapter.setTypes(typeBeans);
                            adapter.notifyDataSetChanged();
                        }

                        dialog.dismiss();

                    }
                }).show();
    }

    /**
     * 长按显示删除弹框
     *
     * @param position
     */
    private void showDeleteDialog(final int position) {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(TypeManagerActivity.this);
        normalDialog.setTitle("删除");
        normalDialog.setMessage("是否删除该分类?");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new TypeDao(TypeManagerActivity.this).DeleteNote(typeBeans.get(position).getId());
                        typeBeans = new TypeDao(TypeManagerActivity.this).queryTypesAll();
                        adapter.setTypes(typeBeans);
                        adapter.notifyDataSetChanged();

                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }
}