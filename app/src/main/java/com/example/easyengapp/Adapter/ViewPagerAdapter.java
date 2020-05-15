package com.example.easyengapp.Adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    static HashMap<Integer,Fragment> map = new HashMap<>();
    // list chứa danh sách fragment trong MainActivity: practice, achivement, profile
    private final List<Fragment> mListFragment = new ArrayList<>();
    // list chứa tên của các fragment
    private final  List<String> mListFragmentName = new ArrayList<>();

    public ViewPagerAdapter( FragmentManager fm) {
        super(fm);
    }
    public void addFragment(Fragment fragment,String title){
        mListFragment.add(fragment);
        mListFragmentName.add(title);
    }
    public String getPageTitle(int position){
        String title = "";
        switch (position){
            case 0:
                title = "LUYỆN TẬP";
                break;
            case 1:
                title = "THÀNH TÍCH";
                break;
            case 2:
                title = "HỒ SƠ";
                break;
        }
        return title;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mListFragment.get(position);
    }

    @Override
    public int getCount() {
        return mListFragment.size();
    }
    // khởi tạo
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container,position);
        map.put(position,fragment);
        return fragment;
    }
    // khi scroll các page bị đi ra ngoài sẽ bị destroy
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
        map.remove(position);
    }
    public static Fragment getFragment(int key){
        return map.get(key);
    }
}
