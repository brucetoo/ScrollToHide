package com.bruce.scrolltohide;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;


public class MainActivity extends ActionBarActivity {

    private Toolbar mToolbar;
    private ImageButton mFabButton;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeRed);
        setContentView(R.layout.activity_main);
        
        initToolBar();
        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        final RecyclerAdapter adapter = new RecyclerAdapter(createItemList());
        mRecyclerView.setAdapter(adapter);
        /*
            recyclerview-animators 包含了增加删除的动画
            
            LandingAnimator
            
            ScaleInAnimator, ScaleInTopAnimator, ScaleInBottomAnimator
            ScaleInLeftAnimator, ScaleInRightAnimator
            
            FadeInAnimator, FadeInDownAnimator, FadeInUpAnimator
            FadeInLeftAnimator, FadeInRightAnimator
            
            FlipInTopXAnimator, FlipInBottomXAnimator
            FlipInLeftYAnimator, FlipInRightYAnimator
            
            SlideInLeftAnimator, SlideInRightAnimator, OvershootInLeftAnimator, OvershootInRightAnimator
            SlideInUpAnimator, SlideInDownAnimator
         */
        mRecyclerView.setItemAnimator(new SlideInDownAnimator());
        mRecyclerView.setOnScrollListener(new ScrollHideListener() {
            @Override
            public void onHide() {
                hideViews();
            }

            @Override
            public void onShow() {
               showViews();
            }
        });
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,mRecyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                adapter.remove(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                adapter.add(position,"new item");
            }
        }));
    }

    private void initToolBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mFabButton = (ImageButton) findViewById(R.id.fabButton);
        setSupportActionBar(mToolbar);
        setTitle(R.string.app_name);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.color_primary_green_dark));
    }

    private List<String> createItemList() {
        List<String> itemList = new ArrayList<>();
        for(int i=0;i<20;i++) {
            itemList.add("Item "+i);
        }
        return itemList;
    }


    private void hideViews() {
        //toolBar向上消失 为 负
        mToolbar.animate().translationY(-mToolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));

        //获取FAB的位移，向下 为正
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mFabButton.getLayoutParams();
        int fabBottomMargin = lp.bottomMargin;
        mFabButton.animate().translationY(mFabButton.getHeight()+fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
    }

    private void showViews() {
        mToolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
        mFabButton.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
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
