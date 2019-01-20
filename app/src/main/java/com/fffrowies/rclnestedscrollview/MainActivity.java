package com.fffrowies.rclnestedscrollview;

import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.fffrowies.rclnestedscrollview.Adapter.MyAdapter;
import com.fffrowies.rclnestedscrollview.Model.Model;

import java.util.ArrayList;
import java.util.List;

import edmt.dev.advancednestedscrollview.AdvancedNestedScrollView;
import edmt.dev.advancednestedscrollview.MaxHeightRecyclerView;

public class MainActivity extends AppCompatActivity {
    
    private boolean isShowingCardHeaderShadow;
    List<Model> modelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        generateModelList();

        final MaxHeightRecyclerView rv = (MaxHeightRecyclerView)findViewById(R.id.card_recycler_view);
        final LinearLayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setAdapter(new MyAdapter(this, modelList));
        rv.addItemDecoration(new DividerItemDecoration(this, lm.getOrientation()));

        final View cardHeaderShadow = findViewById(R.id.card_header_shadow);
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                boolean isRecyclerViewScrolledToTop = lm.findFirstVisibleItemPosition() == 0
                        && lm.findViewByPosition(0).getTop() == 0;

                if (!isRecyclerViewScrolledToTop && !isShowingCardHeaderShadow) {
                    isShowingCardHeaderShadow = true;
                    showOrHideView(cardHeaderShadow, isShowingCardHeaderShadow);
                }
                else {
                    isShowingCardHeaderShadow = false;
                    showOrHideView(cardHeaderShadow, isShowingCardHeaderShadow);
                }
            }
        });

        AdvancedNestedScrollView advancedNestedScrollView = (AdvancedNestedScrollView)findViewById(R.id.nested_scroll_view);
        advancedNestedScrollView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        advancedNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView nestedScrollView, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == 0 && oldScrollY > 0) {
                    //Reset the RecyclerView's scroll position each time the card return to its starting position
                    rv.scrollToPosition(0);
                    cardHeaderShadow.setAlpha(0f);
                    isShowingCardHeaderShadow = false;
                }
            }
        });
    }

    private void showOrHideView(View view, boolean isShow) {
        view.animate().alpha(isShow ? 1f:0f).setDuration(100).setInterpolator(new DecelerateInterpolator());
    }

    private void generateModelList() {
        modelList.add(new Model("https://imgs.mongabay.com/wp-content/uploads/sites/25/2017/08/07172039/vista_aerea_amazonas-768x512.jpg",
                "Amazonas, Brazil"));
        modelList.add(new Model("https://imgs.mongabay.com/wp-content/uploads/sites/25/2018/03/16181021/Puerto-de-Pucallpa-7-768x512.jpg",
                "Puerto de Pucallpa, Ecuador"));
        modelList.add(new Model("https://imgs.mongabay.com/wp-content/uploads/sites/25/2019/01/17134908/RS20032__oficinacacau_garimpo_aereas05-lpr-768x512.jpg",
                "Oficina Cacau Garimpo, Area 05"));
        modelList.add(new Model("https://imgs.mongabay.com/wp-content/uploads/sites/20/2018/11/20133837/Interdiction_6977149771-768x512.jpg",
                "Interdiction, Peru"));
        modelList.add(new Model("https://imgs.mongabay.com/wp-content/uploads/sites/25/2018/08/22201821/Oso-de-anteojos_Santiago-Molina-768x512.jpg",
                "Oso de Anteojos, Venezuela"));
        modelList.add(new Model("https://imgs.mongabay.com/wp-content/uploads/sites/25/2018/10/18162856/Mar-Tropical-de-Grau-2-Yuri-Hooker-768x512.jpg",
                "Mar Tropical de Grau, Brazil"));
        modelList.add(new Model("https://imgs.mongabay.com/wp-content/uploads/sites/25/2018/08/29213952/7F-768x512.jpg",
                "Ni la mas palida, Argentina"));
    }
}
