package com.example.ttst;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * 三极管介绍页 Fragment。
 *
 * <p>以图文结合的方式展示三极管的基础信息：电路符号、基本介绍、三个电极、
 * 核心特性、常见类型与工作状态。页面底部的按钮通过宿主 Activity 切换到参数页。</p>
 */
public class TransistorIntroFragment extends Fragment {

    /** 由 Fragment 自己提供布局资源，省去重写 onCreateView。 */
    public TransistorIntroFragment() {
        super(R.layout.fragment_device_intro);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 点击后交给宿主 Activity 切换底部导航，两个 Fragment 之间不直接互相引用
        view.findViewById(R.id.btn_open_parameters).setOnClickListener(v -> {
            if (requireActivity() instanceof MainActivity) {
                ((MainActivity) requireActivity()).openParameterTab();
            }
        });
    }
}