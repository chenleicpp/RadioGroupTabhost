package com.demo.tabhostlike;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.demo.tabhostlike.fragment.AppFragment;
import com.demo.tabhostlike.fragment.DocFragment;
import com.demo.tabhostlike.fragment.HomeFragment;
import com.demo.tabhostlike.fragment.MoreFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends FragmentActivity {

    @InjectView(R.id.tab_menu)
    RadioGroup mRadioGroup;

    List<Fragment> mListFragments;

    FragmentTabAdapter mFragmentTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initView();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ButterKnife.reset(this);
    }

    private void initView() {
        mListFragments = new ArrayList<Fragment>();
        mListFragments.add(new HomeFragment());
        mListFragments.add(new AppFragment());
        mListFragments.add(new DocFragment());
        mListFragments.add(new MoreFragment());

        mFragmentTab = new FragmentTabAdapter(this,mListFragments,R.id.tab_content,mRadioGroup);
        mFragmentTab.setOnRgsExtraCheckedChangedListener(new FragmentTabAdapter.OnRgsExtraCheckedChangedListener() {
            @Override
            public void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index) {
                Toast.makeText(MainActivity.this,"checkedId:"+checkedId+",index:"+index,Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
