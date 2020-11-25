package com.teamhub.notiget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.teamhub.notiget.adapter.SetApplicationListAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SettingScreenTimeActivity extends AppCompatActivity {
    SetApplicationListAdapter adapter;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    PackageManager pm;
    List<ApplicationInfo> intalledApps;
    long lastSearchResultStartTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_screen_time);


        pm = getPackageManager();
        intalledApps = getPackageManager().getInstalledApplications(0);

        //체크한 앱 목록 가져옴
        pref = getSharedPreferences("checkedApps", MODE_PRIVATE);
        editor = pref.edit();

        ArrayList<String> checkedApps = new ArrayList<>();
        if (!pref.getString("checkedApps", "").equals("")) {
            for (String app : pref.getString("checkedApps", "").split(",")) {
                checkedApps.add(app);
            }
        }

        ((EditText) findViewById(R.id.et_search)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //검색기능
                new AsyncTask() {
                    long startTime;
                    String search;

                    @Override
                    protected void onPreExecute() {
                        //쓰레드 시작 시간 저장
                        startTime = Calendar.getInstance().getTimeInMillis();
                        search = charSequence.toString().toLowerCase();
                        super.onPreExecute();
                    }

                    @Override
                    protected Object doInBackground(Object[] objects) {
                        if (search.equals("")) {
                            //검색 창에 아무것도 넣지 않았을 경우 모두 검색
                            adapter = new SetApplicationListAdapter(intalledApps, checkedApps, getApplicationContext());
                        } else {
                            List<ApplicationInfo> apps = new ArrayList<>();//검색한 내용에 해당하는 앱 이름을 가진 앱들을 저장하기 위한 변수
                            for (ApplicationInfo app : intalledApps) {
                                try {
                                    if (pm.getApplicationLabel(pm.getApplicationInfo(app.packageName, 0)).toString().toLowerCase().contains(search.toString())) {
                                        //검색한 내용과 앱 이름이 같다면 apps에 추가
                                        apps.add(app);
                                    }
                                } catch (PackageManager.NameNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                            adapter = new SetApplicationListAdapter(apps, checkedApps, getApplicationContext());
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        //쓰레드가 여러개 실행되어 검색 결과 출력 순서가 꼬이지 않도록 시작 시간을 저장하고,
                        // 시작 시간이 마지막으로 업데이트 한 결과값의 시작 시간보다 뒤에 요청한 검색일 경우에만 검색 결과 적용
                        if (startTime > lastSearchResultStartTime) {
                            lastSearchResultStartTime = startTime;
                            ((ListView) findViewById(R.id.lv_applist)).setAdapter(adapter);
                        }
                        super.onPostExecute(o);
                    }
                }.execute();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //리스트뷰에 적용
        adapter = new SetApplicationListAdapter(intalledApps, checkedApps, getApplicationContext());
        ((ListView) findViewById(R.id.lv_applist)).setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        //뒤로 가기 버튼을 눌렀을 때 저장.
        editor.putString("checkedApps", adapter.getCheckedAppsToString());
        editor.commit();
        Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_SHORT).show();
        finish();
    }
}