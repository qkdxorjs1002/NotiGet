package com.teamhub.notiget.ui.widget.screentime;

import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.provider.Settings;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.teamhub.notiget.R;
import com.teamhub.notiget.SettingScreenTimeActivity;
import com.teamhub.notiget.adapter.ApplicationListAdapter;
import com.teamhub.notiget.model.main.Widget;
import com.teamhub.notiget.model.screentime.ItemApplication;
import com.teamhub.notiget.ui.widget.base.BaseFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.app.AppOpsManager.OPSTR_GET_USAGE_STATS;
import static androidx.core.app.AppOpsManagerCompat.MODE_ALLOWED;

public class ScreenTimeFragment extends BaseFragment {

    private ScreenTimeViewModel viewModel;
    private View root;
    private ListView listView;

    public static ScreenTimeFragment newInstance() {
        return new ScreenTimeFragment();
    }

    public static final Widget.OnSettingClickListener settingClickListener = context -> {
        context.startActivity(new Intent(context, SettingScreenTimeActivity.class));
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(ScreenTimeViewModel.class);
        root = inflater.inflate(R.layout.widget_screentime, container, false);


        ArrayList<ItemApplication> items = new ArrayList<>();//스크린 타임 표시를 위한 요소들 저장
        checkForPermission();//앱별 사용 시간 가져오기 위한 권한 체크

        SharedPreferences pref = getActivity().getSharedPreferences("checkedApps", Context.MODE_PRIVATE);

        if (!pref.getString("checkedApps", "").equals("")) {
            String apps[] = pref.getString("checkedApps", "").split(",");//보여주기로 한 앱 리스트 가져옴

            for (UsageStats data : getAppUsageStats()) {
                //앱별 사용시간 데이터를 가져오고, apps안에 해당 앱의 패키지명이 있다면 표시하기 위해 items에 시간과 함께 추가함.
                for (String app : apps) {
                    if (app.equals(data.getPackageName())) {
                        items.add(new ItemApplication(data.getPackageName(), data.getTotalTimeInForeground()));
                        break;
                    }
                }
            }

            //사용 시간이 0인 앱은 가져오지 않을 때도 있기 때문에 추가되어지지 않은 앱들을 추가하여 줌.
            for (String app : apps) {
                boolean flag = true;
                for (ItemApplication item : items) {
                    if (item.getPackageName().equals(app)) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    items.add(new ItemApplication(app, 0));
                }
            }

            Collections.sort(items);//사용 시간 기준 내림차순으로 정렬
        }


        //리스트뷰에 적용
        ApplicationListAdapter adapter = new ApplicationListAdapter(items, getContext());
        listView.setAdapter(adapter);

        //item 갯수 * item 하나당 height로 listview의 height를 지정해줌.
        ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
        layoutParams.height = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 41, getResources().getDisplayMetrics()) * items.size());
        listView.setLayoutParams(layoutParams);
        return root;
    }

    private void checkForPermission(){
        //권한 확인
        AppOpsManager appOps = (AppOpsManager) getActivity().getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(OPSTR_GET_USAGE_STATS, Process.myUid(), getContext().getPackageName());

        if(mode != MODE_ALLOWED)//허용되어있지 않다면
        {
            //권한 확인을 위한 다이얼로그 띄움
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("권한 확인").setMessage("스크린 타임 사용을 위해서는 사용정보 접근 허용 권한이 필요합니다.\n"+getString(R.string.app_name) + " > 사용 추적 허용을 체크해주세요.");
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
                    dialogInterface.dismiss();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    private List<UsageStats> getAppUsageStats() {
        //오늘의 시작 시간 (0시 0분 0초 0밀리세컨드초) 구함
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        //오늘 사용한 앱들의 사용 시간 가져옴
        UsageStatsManager usageStatsManager = (UsageStatsManager) getActivity().getSystemService(Context.USAGE_STATS_SERVICE);
        List<UsageStats> queryUsageStats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, cal.getTimeInMillis(), System.currentTimeMillis());
        return queryUsageStats;
    }

}