package com.example.ttst;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

/**
 * 器件信息展示页面：顶部为标题与简要说明，下方用 RecyclerView 展示器件列表。
 */
public class DeviceListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);

        setupToolbar();
        setupDeviceList();
    }

    /**
     * 配置顶部工具栏，并显示返回箭头。
     */
    private void setupToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar_device_list);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * 绑定模拟数据到 RecyclerView，并刷新器件数量文本。
     */
    private void setupDeviceList() {
        List<MicroDevice> devices = DeviceRepository.loadDevices();

        TextView countText = findViewById(R.id.tv_device_count);
        countText.setText(getString(R.string.device_count, devices.size()));

        RecyclerView recyclerView = findViewById(R.id.rv_devices);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new DeviceAdapter(devices));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // 点击左上角返回箭头时关闭当前页面
            getOnBackPressedDispatcher().onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}