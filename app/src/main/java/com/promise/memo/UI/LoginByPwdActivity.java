package com.promise.memo.UI;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.promise.memo.DB.UserDao;
import com.promise.memo.R;

public class LoginByPwdActivity extends Activity implements View.OnClickListener, TextWatcher {


    private EditText mLoginNameEt;
    private EditText mLoginPasswordEt;
    private Button mXz;
    private Button mBtnLogin;
    UserDao userdao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_bypwd);
        initView();
    }


    private void initView() {

        mLoginNameEt = (EditText) findViewById(R.id.login_name_et);
        mLoginPasswordEt = (EditText) findViewById(R.id.login_password_et);
        mLoginNameEt.addTextChangedListener(this); //设置电话样式
        mLoginPasswordEt.addTextChangedListener(this);
        findViewById(R.id.login_detil_iv).setOnClickListener(this);
        findViewById(R.id.login_return_ll).setOnClickListener(this);
        mXz = (Button) findViewById(R.id.login_xz_bt);
        mXz.setOnClickListener(this);
        mBtnLogin = (Button) findViewById(R.id.login_longin_bt);
        mBtnLogin.setOnClickListener(this);

        findViewById(R.id.login_register_tv).setOnClickListener(this);


    }

    boolean isSelected = false;  //点击显示密码

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.login_longin_bt:  //点击登录
                login();
                break;
            case R.id.login_return_ll:  //点击退出
                finish();
                break;
            case R.id.login_detil_iv:  //账号栏点击清空
                mLoginNameEt.setText("");
                break;
            case R.id.login_xz_bt:   //密码选择样式

                if (isSelected) {
                    isSelected = false;
                    mXz.setSelected(false);
                    mLoginPasswordEt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    isSelected = true;
                    mXz.setSelected(true);
                    mLoginPasswordEt.setInputType(InputType.TYPE_CLASS_TEXT);
                }

                break;
            case R.id.login_register_tv:   //注册
                finish();
                break;


        }
    }


    private void login() {
                userdao = new UserDao(LoginByPwdActivity.this);
        final String username=mLoginNameEt.getText().toString();
        final String password=mLoginPasswordEt.getText().toString();

        if(username.isEmpty()){
            Toast.makeText(getApplicationContext(),"帐号不能为空",Toast.LENGTH_LONG).show();
            return;
        }else if(password.isEmpty()){
            Toast.makeText(getApplicationContext(),"密码不能为空",Toast.LENGTH_LONG).show();
            return;
        }

        Cursor cursor = userdao.query(username.trim(), password.trim());
        if (cursor.moveToNext()) {
            Intent intent = new Intent();
            intent.setClass(LoginByPwdActivity.this,MainActivity.class);
            intent.putExtra("login_user",username);

            cursor.close();
            startActivity(intent);
            finish();
        }else{

            Toast.makeText(LoginByPwdActivity.this, "密码验证失败，请重新验证登录", Toast.LENGTH_SHORT).show();
        }

    }




    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    //显示电话的样式
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        if (!TextUtils.isEmpty(mLoginNameEt.getText().toString()) && !TextUtils.isEmpty(mLoginPasswordEt.getText().toString())) {
            mBtnLogin.setClickable(true);

        } else {
            mBtnLogin.setClickable(false);
            mBtnLogin.setBackgroundResource(R.drawable.button_back);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }


}
