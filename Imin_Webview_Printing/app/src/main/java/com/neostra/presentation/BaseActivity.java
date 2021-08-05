package com.neostra.presentation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * @author njmsir
 * Created by Administrator on 2018/11/24.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        navigationClick(getNavigation());
        onActivityCreate();
    }

    private void navigationClick(@Nullable View navigation) {
        if (null != navigation) {
            navigation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    protected abstract View getNavigation();

    public abstract int getLayoutId();

    public abstract void onActivityCreate();

}
