package cn.itcast.minimusic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText name;  //创建账号
    EditText passwd;  //创建密码
    Button login_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.input_identity_text);   //获取输入的账号
        passwd = findViewById(R.id.input_password_text);     //获取输入的密码
        login_btn=findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login_name="admin";
                String login_psw="12345";
                String inputname=name.getText().toString();
                String inputpsw=passwd.getText().toString();
                if(login_name.equals(inputname)&login_psw.equals(inputpsw)){
                    Intent intent=new Intent(MainActivity.this,main_page.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(MainActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
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
