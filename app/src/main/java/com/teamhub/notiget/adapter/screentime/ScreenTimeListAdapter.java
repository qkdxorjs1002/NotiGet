package com.teamhub.notiget.adapter.screentime;

import android.content.Context;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamhub.notiget.R;
import com.teamhub.notiget.model.screentime.ItemApplication;

import java.util.ArrayList;

public class ScreenTimeListAdapter extends BaseAdapter {
    ArrayList<ItemApplication> items;
    Context context;
    PackageManager pm;

    public ScreenTimeListAdapter(ArrayList<ItemApplication> items, Context context) {
        this.items = items;//앱 리스트
        this.context = context;
        pm = context.getPackageManager();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_screentime_list_item, parent, false);
        }

        TextView tv1, tv2;
        ImageView iv;

        tv1 = convertView.findViewById(R.id.tv1);
        tv2 = convertView.findViewById(R.id.tv2);
        iv = convertView.findViewById(R.id.iv_appicon);

        ItemApplication item = items.get(i);

        try {
            iv.setImageDrawable(pm.getApplicationIcon(item.getPackageName()));//앱 아이콘 적용
            tv1.setText(pm.getApplicationLabel(pm.getApplicationInfo(item.getPackageName(), 0)));//앱 이름 표시

            //사용 시분초 표시

            //밀리세컨드를 시, 분, 초로 바꾸는 코드
            int seconds = (int) (item.getUsageTime() / 1000) % 60 ;
            int minutes = (int) ((item.getUsageTime() / (1000*60)) % 60);
            int hours   = (int) ((item.getUsageTime() / (1000*60*60)) % 24);

            String text = "";
            text += hours == 0 ? "" : hours + "시간 ";
            text += minutes == 0 ? "" : minutes + "분 ";
            text += seconds+"초";
            tv2.setText(text);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return convertView;
    }
}
