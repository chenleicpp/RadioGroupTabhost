package com.demo.tabhostlike;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.RadioGroup;

import java.util.List;

/**
 * Created by Administrator on 2015/5/6.
 */
public class FragmentTabAdapter implements RadioGroup.OnCheckedChangeListener {

    private FragmentActivity fragmentActivity;
    private List<Fragment> fragments;
    private int contentId;
    private RadioGroup rgp;

    private int currentTab = 0;

    private OnRgsExtraCheckedChangedListener onRgsExtraCheckedChangedListener;

    public FragmentTabAdapter(FragmentActivity activity,List<Fragment> fragments,int contentId,RadioGroup rgp){
        this.fragmentActivity = activity;
        this.fragments = fragments;
        this.contentId = contentId;
        this.rgp = rgp;

        //show first
        FragmentTransaction ft = fragmentActivity.getSupportFragmentManager().beginTransaction();
        ft.add(contentId,fragments.get(0));
        ft.commit();

        rgp.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
        for (int i=0;i<radioGroup.getChildCount();i++){
            if (radioGroup.getChildAt(i).getId() == checkId){
                //click fragment
                Fragment fragment = fragments.get(i);
                FragmentTransaction ft = obtainFragmentTransaction(i);
                //pause current
                getCurrentFragment().onPause();
                if (fragment.isAdded()){
                    fragment.onResume();
                }else {
                    ft.add(contentId,fragment);
                }
                showTab(i);
                ft.commit();

                if(null != onRgsExtraCheckedChangedListener){
                    onRgsExtraCheckedChangedListener.OnRgsExtraCheckedChanged(radioGroup, checkId, i);
                }
            }
        }
    }

    private void showTab(int index){
        for (int i = 0;i<fragments.size();i++){
            Fragment fragment = fragments.get(i);
            FragmentTransaction ft = obtainFragmentTransaction(index);
            if (index == i){
                ft.show(fragment);
            }else {
                ft.hide(fragment);
            }
            ft.commit();
        }
        currentTab = index;
    }

    private FragmentTransaction obtainFragmentTransaction(int index){
        FragmentTransaction ft = fragmentActivity.getSupportFragmentManager().beginTransaction();
        if(index > currentTab){
            ft.setCustomAnimations(R.anim.push_left_in, R.anim.push_left_out);
        }else{
            ft.setCustomAnimations(R.anim.push_right_in, R.anim.push_right_out);
        }
        return ft;
    }

    private Fragment getCurrentFragment(){
        return fragments.get(currentTab);
    }

    public interface OnRgsExtraCheckedChangedListener{
        void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index);
    }

    public void setOnRgsExtraCheckedChangedListener(OnRgsExtraCheckedChangedListener onRgsExtraCheckedChangedListener) {
        this.onRgsExtraCheckedChangedListener = onRgsExtraCheckedChangedListener;
    }
}
