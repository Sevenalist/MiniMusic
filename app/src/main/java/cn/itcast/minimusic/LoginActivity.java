package cn.itcast.minimusic;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    EditText name;  //创建账号
    EditText passwd;  //创建密码
    Button login_btn;
    TextView toReg;
    CheckBox cb_Remeber;
    MySQLite mySQ;
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


        name = findViewById(R.id.input_identity_text);   //获取输入的账号
        passwd = findViewById(R.id.input_password_text);     //获取输入的密码
        login_btn=findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String login_name = name.getText().toString();
                String login_psw = passwd.getText().toString();
                List<User> userList = mySQ.selectByAccountAndPass(login_name,login_psw);
                if (!userList.isEmpty()){
                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_LONG);

                    if (cb_Remeber.isChecked()){
                        SharedPreferences spf = getSharedPreferences("spfRecorid",MODE_PRIVATE);
                        SharedPreferences.Editor edit = spf.edit();
                        edit.putString("login_name",login_name);
                        edit.putString("login_psw",login_psw);
                        edit.putBoolean("isRemember",true);
                        edit.apply();
                    }else{
                        SharedPreferences spf = getSharedPreferences("spfRecorid",MODE_PRIVATE);
                        SharedPreferences.Editor edit = spf.edit();
                        edit.putBoolean("isRemember",false);
                        edit.apply();
                    }
                    Intent intent = new Intent(LoginActivity.this,main_page.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(LoginActivity.this,"账号或密码错误",Toast.LENGTH_SHORT).show();
                }
            }
        });
        toReg = findViewById(R.id.toReg);
        toReg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,Register
                        .class);
                startActivity(intent);
            }
        });

    }
    private void initView(){
        name = findViewById(R.id.regiter);
        passwd=findViewById(R.id.input_password_text);
        login_btn = findViewById(R.id.login_btn);
        mySQ = new MySQLite(this);
        cb_Remeber = findViewById(R.id.cb_Remember);

    }
    private void initData(){
        SharedPreferences spf = getSharedPreferences("spfRecorid",MODE_PRIVATE);
        boolean isRem = spf.getBoolean("isRemember",false);
        String account = spf.getString("account","");
        String password = spf.getString("password","");
        if(isRem){
            name.setText(account);
            passwd.setText(password);
            cb_Remeber.setChecked(true);
        }
    }
    //private void initData(){
    //  SharedPreferences spf = getSharedPreferences("spfRecorid",MODE_PRIVATE);
    //boolean isPem = spf.getBoolean();
    //}
    //添加按钮事件
//    public void Login(View v){
//        //这是能够登录的账号密码
//        String Usename = "admin";
//        String Upwd = "12345";
//
//        //创建两个String类，储存从输入文本框获取到的内容
//        String user = name.getText().toString().trim();
//        String pwd = passwd.getText().toString().trim();
//
//        //进行判断，如果两个内容都相等，就显现第一条语句，反之，第二条。
//        if(user.equals(Usename) & pwd.equals(Upwd)){
//            Toast.makeText(this, "欢迎你，新世界管理者", Toast.LENGTH_SHORT).show();
//        }else{
//            Toast.makeText(this, "身份验证错误，禁止访问", Toast.LENGTH_SHORT).show();
//        }
//
//    }

}



