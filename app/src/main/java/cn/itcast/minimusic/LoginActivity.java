package cn.itcast.minimusic;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    EditText name;  //创建账号
    EditText passwd;  //创建密码
    Button login_btn;
    TextView toReg;
    CheckBox cb_Remeber;
    MySQLite mySQ;
    // 动态要申请的权限
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPermission();
        /*动态获取权限*/
        initView();
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = findViewById(R.id.input_identity_text);   //获取输入的账号
                passwd = findViewById(R.id.input_password_text);     //获取输入的密码
                login_btn = findViewById(R.id.login_btn);
                String login_name = name.getText().toString();
                String login_psw = passwd.getText().toString();
                List<User> userList = mySQ.selectByAccountAndPass(login_name, login_psw);
                if (!userList.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG);

                    if (cb_Remeber.isChecked()) {
                        SharedPreferences spf = getSharedPreferences("spfRecorid", MODE_PRIVATE);
                        SharedPreferences.Editor edit = spf.edit();
                        edit.putString("login_name", login_name);
                        edit.putString("login_psw", login_psw);
                        edit.putBoolean("isRemember", true);
                        edit.apply();
                    } else {
                        SharedPreferences spf = getSharedPreferences("spfRecorid", MODE_PRIVATE);
                        SharedPreferences.Editor edit = spf.edit();
                        edit.putBoolean("isRemember", false);
                        edit.apply();
                    }
                    Intent intent = new Intent(LoginActivity.this, main_page.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });


            toReg = findViewById(R.id.toReg);
            toReg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LoginActivity.this, Register
                            .class);
                    startActivity(intent);
                }
            });

    }

    private void getPermission() {
        int i = ContextCompat.checkSelfPermission(getApplicationContext(), permissions[0]);
        int l = ContextCompat.checkSelfPermission(getApplicationContext(), permissions[1]);
        // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
        if (i != PackageManager.PERMISSION_GRANTED || l != PackageManager.PERMISSION_GRANTED) {
            // 如果没有授予该权限，就去提示用户请求
            startRequestPermission();
        }
    }

    private void startRequestPermission() {
        ActivityCompat.requestPermissions(this, permissions, 321);
    }

    private void initView(){
        name = findViewById(R.id.regiter);
        passwd=findViewById(R.id.input_password_text);
        login_btn = findViewById(R.id.login_btn);
        mySQ = new MySQLite(this);
        cb_Remeber = findViewById(R.id.cb_Remember);

    }

    /*回调方法*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 321) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    //如果没有获取权限，那么可以提示用户去设置界面--->应用权限开启权限
                }
            }
        }
    }

}



