package com.example.ttst;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

/**
 * 单 Activity 宿主页。
 *
 * <p>只负责三件事：托管 {@link Fragment} 容器、维护底部导航的选中状态、
 * 在「器件介绍」与「参数参考」两个 Fragment 之间切换。页面内容全部由 Fragment
 * 承担，Activity 不再持有业务布局。</p>
 */
public class MainActivity extends AppCompatActivity {

    /** 屏幕旋转等重建场景下保存当前选中的导航项。 */
    private static final String STATE_SELECTED_ITEM = "state_selected_item";

    /** Fragment 的标签，用于复用已创建的实例。 */
    private static final String TAG_INTRO = "fragment_intro";
    private static final String TAG_PARAMETER = "fragment_parameter";

    private MaterialToolbar toolbar;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        applyWindowInsets(findViewById(R.id.root_main));

        toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        // 点击底部导航项时切换 Fragment，返回 true 表示选中态由导航栏自己维护
        bottomNavigationView.setOnItemSelectedListener(item -> {
            selectTab(item.getItemId());
            return true;
        });

        // 首次进入默认展示介绍页；重建时恢复上次选中的页面
        int selectedItemId = savedInstanceState == null
                ? R.id.nav_intro
                : savedInstanceState.getInt(STATE_SELECTED_ITEM, R.id.nav_intro);
        if (bottomNavigationView.getSelectedItemId() == selectedItemId) {
            selectTab(selectedItemId);
        } else {
            // setSelectedItemId 会回调上面的监听器完成切换
            bottomNavigationView.setSelectedItemId(selectedItemId);
        }
    }

    /**
     * 切换到参数页。
     *
     * <p>供器件介绍页的「查看参数参考值」按钮调用，让两个 Fragment 通过宿主
     * Activity 通信，而不是彼此直接引用。</p>
     */
    public void openParameterTab() {
        bottomNavigationView.setSelectedItemId(R.id.nav_parameters);
    }

    /** 根据导航项切换 Fragment，并同步工具栏标题。 */
    private void selectTab(int itemId) {
        boolean parameterTab = itemId == R.id.nav_parameters;
        showFragment(parameterTab ? TAG_PARAMETER : TAG_INTRO,
                parameterTab ? new DeviceParameterFragment() : new DeviceIntroFragment());
        toolbar.setTitle(parameterTab ? R.string.title_parameter : R.string.title_intro);
    }

    /**
     * 显示指定标签的 Fragment，并隐藏其余的 Fragment。
     *
     * <p>已创建过的 Fragment 会被复用（show / hide），避免每次切换都重建视图，
     * 从而保留各自的滚动位置。</p>
     */
    private void showFragment(String tag, Fragment target) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction().setReorderingAllowed(true);

        List<Fragment> fragments = manager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment.isAdded() && !tag.equals(fragment.getTag())) {
                transaction.hide(fragment);
            }
        }

        Fragment current = manager.findFragmentByTag(tag);
        if (current == null) {
            transaction.add(R.id.fragment_container, target, tag);
        } else {
            transaction.show(current);
        }
        // 同步提交，保证连续点击底部导航时不会重复添加同一个 Fragment
        transaction.commitNow();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_ITEM, bottomNavigationView.getSelectedItemId());
    }

    /** 让内容避开状态栏与系统导航栏，适配全面屏和手势导航。 */
    private void applyWindowInsets(View root) {
        ViewCompat.setOnApplyWindowInsetsListener(root, (view, windowInsets) -> {
            Insets bars = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return windowInsets;
        });
    }
}