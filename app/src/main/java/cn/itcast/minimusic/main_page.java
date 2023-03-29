package cn.itcast.minimusic;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

//public class main_page extends AppCompatActivity {
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main_page);
//    }
//}

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class main_page extends AppCompatActivity implements View.OnClickListener {
    //创建需要用到的控件的变量
    private TextView tv1,tv2;
    private FragmentManager fm;
    private FragmentTransaction ft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        //绑定控件
        tv1=(TextView)findViewById(R.id.home);
        tv2=(TextView)findViewById(R.id.songbook);
        //设置监听器，固定写法
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        //若是继承FragmentActivity，fm=getFragmentManger();
        fm=getSupportFragmentManager();
        //fm可以理解为Fragment显示的管理者，ft就是它的改变者
        ft=fm.beginTransaction();
        //默认情况下就显示frag1
        ft.replace(R.id.content,new BlankFragment1());
        //提交改变的内容
        ft.commit();
    }
    @Override
    //控件的点击事件
    public void onClick(View v){
        ft=fm.beginTransaction();
        //切换选项卡
        switch (v.getId()){
            case R.id.home:
                ft.replace(R.id.content,new BlankFragment1());
                break;
            case R.id.songbook:
                ft.replace(R.id.content,new LocalMusic());
                break;
            default:
                break;
        }
        ft.commit();
    }

    @Override
    public void onBackPressed(){   //从zhu_page中直接退出到那个桌面，退出程序
        AlertDialog dialog;
        AlertDialog.Builder builder=new AlertDialog.Builder(this) //设置builder对用户是否退出进行提示
                .setTitle("退出提示")
                .setMessage("你要退出应用?")
                .setPositiveButton("我要",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        Activity activity = (Activity) main_page.this;
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addCategory(Intent.CATEGORY_HOME);//退出到桌面
                        activity.startActivity(intent);
                        System.exit(0);


                    }
                })
                .setNegativeButton("不要", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });
        dialog=builder.create();
        dialog.show();
    }

}

