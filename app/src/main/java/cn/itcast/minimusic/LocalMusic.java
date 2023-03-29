package cn.itcast.minimusic;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LocalMusic extends Fragment implements View.OnClickListener {
    ImageView nextIv, playIv, lastIv;
    TextView singerTv, songTv;
    RecyclerView musicRv;
    private View view;

    // 数据源
    List<LocalMusicBean> mDatas;
    private LocalMusicAdapter adapter;

    // 记录当前正在播放的音乐位置
    int currentPlayPosition = -1;
    MediaPlayer mediaPlayer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        绑定布局
        view = inflater.inflate(R.layout.local_music_book, container, false);
        initView();
        mediaPlayer = new MediaPlayer();
        mDatas = new ArrayList<>();
//      创建适配器对象
        adapter = new LocalMusicAdapter(getActivity(), mDatas);
        musicRv.setAdapter(adapter);
//      设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        musicRv.setLayoutManager(layoutManager);

//        加载本地数据源
        loadLocalMusicData();
//      设置每一项的点击事件
        setEventListener();
        return view;
    }


    private void setEventListener(){
        /*设置每一项的点击事件*/
        adapter.setOnItemClickListener(new LocalMusicAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                startMusic(position);
            }
        });
    }

    private void playMusic() {
        /*播放音乐的函数*/

        if (mediaPlayer!=null && !mediaPlayer.isPlaying()) {
            try {
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            playIv.setImageResource(R.mipmap.play);
        }
    }

    private void stopMusic() {
        /*停止音乐的函数*/
        if (mediaPlayer!=null) {
            mediaPlayer.pause();
            mediaPlayer.seekTo(0);
            mediaPlayer.stop();
            playIv.setImageResource(R.mipmap.pause);
        }
    }

    private void startMusic(int position){
        currentPlayPosition = position;
        LocalMusicBean musicBean = mDatas.get(position);
//                设置底部音乐栏的歌手名和歌曲名
        singerTv.setText(musicBean.getSinger());
        songTv.setText(musicBean.getSong());
        stopMusic();
//                重置多媒体播放器, 删掉原来的地址
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(musicBean.getPath());
            playMusic();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopMusic();
    }

    private void loadLocalMusicData() {
        /*加载本地存储中的mp3文件到集合中*/
//        1. 获取ContentResolver (内容接收者)
        ContentResolver resolver = getActivity().getContentResolver();
//        2. 获取本地音乐存储的Uri地址
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//        3. 开始查询地址 东西都存在Cursor里面 条件没有所以都为null
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!=0";
        Cursor cursor = resolver.query(uri, null, selection, null, null);
//        4. 遍历Cursor对象
        int id = 0;
        while (cursor != null && cursor.moveToNext()) {
            @SuppressLint("Range") String song = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            @SuppressLint("Range") String singer = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            @SuppressLint("Range") String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
            @SuppressLint("Range") String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            @SuppressLint("Range") long time = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
            SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
            String duration = sdf.format(new Date(time));
            if(duration.equals("00:00")){
                continue;
            }
            String sid = String.valueOf(++id);
            // 将一行中的数据封装到对象中
            LocalMusicBean bean = new LocalMusicBean(sid, song, singer, album, duration, path);
            mDatas.add(bean);
        }
//        数据源发生变化，提示适配器更新
        adapter.notifyDataSetChanged();
    }

    private void initView() {
        /*初始化控件的函数*/
        nextIv = view.findViewById(R.id.next_photo);
        playIv = view.findViewById(R.id.pause_photo);
        lastIv = view.findViewById(R.id.last_photo);
        musicRv = view.findViewById(R.id.music_list);
        singerTv = view.findViewById(R.id.play_singer_message);
        songTv = view.findViewById(R.id.play_song_message);
        nextIv.setOnClickListener(this);
        playIv.setOnClickListener(this);
        lastIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.next_photo:
                if(currentPlayPosition < mDatas.size() - 1){
                    startMusic(currentPlayPosition+1);}
                break;
            case R.id.last_photo:
                if(currentPlayPosition > 0){
                    startMusic(currentPlayPosition-1);}
                break;
            case R.id.pause_photo:
                pauseMusic();
                break;
        }
    }

    private void pauseMusic() {
        if (mediaPlayer!=null && currentPlayPosition != -1) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                playIv.setImageResource(R.mipmap.pause);
            } else {
                mediaPlayer.start();
                playIv.setImageResource(R.mipmap.play);
            }
        }
    }
}