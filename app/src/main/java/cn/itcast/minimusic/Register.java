package cn.itcast.minimusic;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;


public class Register extends AppCompatActivity implements View.OnClickListener{
    private EditText etAccount,etPass,etPassConfirm;
    private Button btnRegister, button2;
    private CheckBox cbAgree;

    private MySQLite mySQLiteOpenHelper;

//    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regiter);

//        返回登录页面
        button2 = (Button) findViewById(R.id.button2);

        etAccount = findViewById(R.id.regiter);
        etPass = findViewById(R.id.mima);
        etPassConfirm = findViewById(R.id.input_password_text_agin);
        btnRegister = findViewById(R.id.button);
        cbAgree = findViewById(R.id.cb_Agree);

        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                Intent intent = new Intent(Register.this,LoginActivity.class);
                startActivity(intent);
            }
        });

//        btnRegister.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Register.this, LoginActivity.class);
//                startActivity(intent);
//            }
//        });

//       第一种方式写监听事件
//        btnRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                …………
//            }
//        });

//        第二种方式写监听事件
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String account = etAccount.getText().toString().trim();
        String pass = etPass.getText().toString().trim();
        String passConfirm = etPassConfirm.getText().toString();

//        TextUtils.isEmpty()  和   name.isEmpty()   的区别
//        第一个可以判断 NULL 和 “”  两种为空的情况，且均判断为空
//        第二个只能判断“”这一种情况，如果为NULL则会报空指针异常。

        if(TextUtils.isEmpty(account)){
            Toast.makeText(Register.this,"用户名不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        mySQLiteOpenHelper = new MySQLite(this);
//        查询这个账号是否已经被注册
        List<User> userList = mySQLiteOpenHelper.selectByAccount(account);
        if(!userList.isEmpty()){  //说明已经被注册过了
            Toast.makeText(Register.this,"该账号已经被注册！",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(pass)){
            Toast.makeText(Register.this,"密码不能为空",Toast.LENGTH_LONG).show();
            return;
        }
        if(!TextUtils.equals(pass,passConfirm)){
            Toast.makeText(Register.this,"两次密码不一致",Toast.LENGTH_LONG).show();
            return;
        }
        if(!cbAgree.isChecked()){
            Toast.makeText(Register.this,"请同意用户协议",Toast.LENGTH_LONG).show();
            return;
        }


        User user = new User();
        user.setAccount(account);
        user.setPass(pass);

        //插入数据到数据库中
        mySQLiteOpenHelper = new MySQLite(this);
        long rowId = mySQLiteOpenHelper.insertData(user);
        if(rowId != -1){
            ToastUtil.toastShort(this,"注册成功");
            Intent intent = new Intent(Register.this,LoginActivity.class);
            startActivity(intent);
        }else {
            ToastUtil.toastShort(this,"注册失败");
        }


    }
}
