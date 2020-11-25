package com.teamhub.notiget.adapter.screentime;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamhub.notiget.R;
import com.teamhub.notiget.model.screentime.ItemApplication;

import java.util.ArrayList;
import java.util.List;

public class SettingListAdapter extends BaseAdapter {
    List<ApplicationInfo> items;
    ArrayList<String> checkedApps;
    Context context;
    PackageManager pm;

    public SettingListAdapter(List<ApplicationInfo> items, ArrayList<String> checkedApps, Context context) {
        this.items = items;//설치된 모든 앱들
        this.context = context;
        this.checkedApps = checkedApps;//체크된 앱들
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
        CheckBox checkBox;

        tv1 = convertView.findViewById(R.id.tv1);
        tv2 = convertView.findViewById(R.id.tv2);
        iv = convertView.findViewById(R.id.iv_appicon);
        checkBox = convertView.findViewById(R.id.check);

        checkBox.setVisibility(View.VISIBLE);

        ApplicationInfo item = items.get(i);
//            Log.d("asdf", i + " " + item.packageName + " " + checkedApps.size());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {//체크
                    if (!checkedApps.contains(item.packageName))
                        checkedApps.add(item.packageName);
                } else {//체크 해제
                    checkedApps.remove(item.packageName);
                }
            }
        });

        try {
            iv.setImageDrawable(pm.getApplicationIcon(item.packageName));//앱 아이콘 적용
            tv1.setText(pm.getApplicationLabel(pm.getApplicationInfo(item.packageName, 0)));//앱 이름 적용
            tv2.setText(item.packageName);//앱 패키지명 적용

            if (checkedApps.contains(item.packageName)) {
                //만약 체크한 앱 리스트 중에 해당 앱이 있다면 체크표시
                checkBox.setChecked(true);
            } else {
                //아니라면 체크 해제
                checkBox.setChecked(false);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return convertView;
    }

    public String getCheckedAppsToString() {
        //체크한 앱 리스트를 sharedPreference에 저장하게 위해 Arraylist를 String 으로 변환환
       String apps = "";
        boolean flag = false;
        for (String app : checkedApps) {
            if (flag) apps += ",";
            apps += app;
            flag = true;
        }
        return apps;
    }
}
