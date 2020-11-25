package com.teamhub.notiget.model.screentime;

import android.graphics.drawable.Drawable;

public class ItemApplication implements Comparable{
    private String packageName;
    private long usageTime;

    public ItemApplication(String packageName, long usageTime) {
        this.packageName = packageName;
        this.usageTime = usageTime;
    }


    public String getPackageName() {
        return packageName;
    }

    public long getUsageTime() {
        return usageTime;
    }


    @Override
    public int compareTo(Object o) {
        //사용 시간별 정렬을 위해
        return (int) (((ItemApplication)o).getUsageTime() - getUsageTime());
    }
}
