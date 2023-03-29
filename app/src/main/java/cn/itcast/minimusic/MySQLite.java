package cn.itcast.minimusic;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import androidx.annotation.Nullable;


import java.util.ArrayList;
import java.util.List;

public class MySQLite extends SQLiteOpenHelper{
        //数据库的名字
        private static final String DB_NAME = "mySQLite.db";
        //表格的名字
        private static final String TABLE_NAME_USER = "user";
        //插入语句
        private static final String CREATE_TABLE_SQL = "create table " + TABLE_NAME_USER + "(id INTEGER PRIMARY KEY AUTOINCREMENT, account text, pass text)";
        public MySQLite(Context context) {
            super(context, DB_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }

        //插入数据
        public long insertData(User user){
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("account",user.getAccount());
            values.put("pass",user.getPass());
            return db.insert(TABLE_NAME_USER,null,values);

        }


        //通过账号密码查询
        public List<User> selectByAccountAndPass(String account, String password) {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.query(TABLE_NAME_USER, null, "account=? and pass=?", new String[]{account, password}, null, null, null);
            List<User> userList = new ArrayList<>();
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    @SuppressLint("Range") String account1 = cursor.getString(cursor.getColumnIndex("account"));
                    @SuppressLint("Range") String password1 = cursor.getString(cursor.getColumnIndex("pass"));
                    User user = new User();
                    user.setAccount(account1);
                    user.setPass(password1);
                    userList.add(user);
                }
                return userList;
            }
            return null;
        }

        //通过账号查询
        public List<User> selectByAccount(String account) {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.query(TABLE_NAME_USER, null, "account=?", new String[]{account}, null, null, null);
            List<User> userList = new ArrayList<>();
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    @SuppressLint("Range") String account1 = cursor.getString(cursor.getColumnIndex("account"));
                    User user = new User();
                    user.setAccount(account1);
                    userList.add(user);
                }
                return userList;
            }
            return null;
        }
    }

