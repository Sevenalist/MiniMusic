package cn.itcast.minimusic;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment1 extends Fragment  {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;


    private final ArrayList<infoBean> banners;

    public BlankFragment1() {
        banners = new ArrayList<>();
        banners.add(new infoBean(R.drawable.astronout));
        banners.add(new infoBean(R.drawable.nightalong));
        banners.add(new infoBean(R.drawable.penguin));
    }

    public static BlankFragment1 newInstance(String param1, String param2) {
        BlankFragment1 fragment = new BlankFragment1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState){
        View inflate = inflater.inflate(R.layout.fragment_blank1, container, false);
        Banner banner = inflate.findViewById(R.id.banner);
        //添加生命周期
        banner.addBannerLifecycleObserver(this)
                .setAdapter(new BannerViewAdapter(banners,this))
                //添加指示器
                .setIndicator(new CircleIndicator(getContext()));

        banner.setOnBannerListener(new OnBannerListener() {
            Intent intent=new Intent();
            @Override
            public void OnBannerClick(Object data, int position) {

                Log.i("tag", "你点了第"+position+"张轮播图");
                switch (position){
                    case 0:
                        intent.setClass(getActivity(),tiao_1.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent.setClass(getActivity(),tiao_2.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent.setClass(getActivity(),tiao_3.class);
                        startActivity(intent);
                        break;
                }
            }
        });

        Button btn_ilike=inflate.findViewById(R.id.btn_ilike);
        btn_ilike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), i_like.class);
                startActivity(intent);
            }
        });
        return inflate;
    }
}



