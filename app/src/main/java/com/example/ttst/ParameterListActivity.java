package com.example.ttst;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

/**
 * 参数列表页。
 *
 * <p>接收录入页通过 Intent 传递的五项器件参数，并以 RecyclerView 列表
 * 的形式逐项展示。</p>
 */
public class ParameterListActivity extends AppCompatActivity {

    private static final String EXTRA_DEVICE_MODEL = "extra_device_model";
    private static final String EXTRA_WAFER_LOT = "extra_wafer_lot";
    private static final String EXTRA_THRESHOLD_VOLTAGE = "extra_threshold_voltage";
    private static final String EXTRA_LEAKAGE_CURRENT = "extra_leakage_current";
    private static final String EXTRA_TEST_DATE = "extra_test_date";

    /** 数值型参数缺失时的默认值。 */
    private static final double DEFAULT_NUMBER = 0.0;

    /**
     * 构造跳转到本页面的 Intent，并把器件参数逐项写入 Intent。
     *
     * @param context   调用方上下文
     * @param parameter 已通过校验的器件参数
     * @return 可直接用于 startActivity() 的 Intent
     */
    public static Intent createIntent(Context context, DeviceParameter parameter) {
        Intent intent = new Intent(context, ParameterListActivity.class);
        intent.putExtra(EXTRA_DEVICE_MODEL, parameter.getDeviceModel());
        intent.putExtra(EXTRA_WAFER_LOT, parameter.getWaferLot());
        intent.putExtra(EXTRA_THRESHOLD_VOLTAGE, parameter.getThresholdVoltage());
        intent.putExtra(EXTRA_LEAKAGE_CURRENT, parameter.getLeakageCurrent());
        intent.putExtra(EXTRA_TEST_DATE, parameter.getTestDate());
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameter_list);

        setupToolbar();
        showParameterList();
    }

    /** 配置工具栏与左上角返回箭头。 */
    private void setupToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar_list);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    /** 从 Intent 取出五项参数，校验完整性后填充列表。 */
    private void showParameterList() {
        Intent intent = getIntent();
        DeviceParameter parameter = new DeviceParameter(
                readString(intent, EXTRA_DEVICE_MODEL),
                readString(intent, EXTRA_WAFER_LOT),
                intent.getDoubleExtra(EXTRA_THRESHOLD_VOLTAGE, DEFAULT_NUMBER),
                intent.getDoubleExtra(EXTRA_LEAKAGE_CURRENT, DEFAULT_NUMBER),
                readString(intent, EXTRA_TEST_DATE));

        // 防御性校验：参数缺失时给出提示并关闭页面，避免展示空白数据
        if (TextUtils.isEmpty(parameter.getDeviceModel())
                || TextUtils.isEmpty(parameter.getWaferLot())
                || parameter.getThresholdVoltage() <= 0.0
                || parameter.getLeakageCurrent() <= 0.0
                || TextUtils.isEmpty(parameter.getTestDate())) {
            Toast.makeText(this, R.string.toast_parameter_missing, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        TextView tvHeading = findViewById(R.id.tv_list_heading);
        tvHeading.setText(getString(R.string.list_heading_with_model,
                parameter.getDeviceModel()));

        List<ParameterAdapter.ParameterItem> items = new ArrayList<>();
        items.add(new ParameterAdapter.ParameterItem(
                getString(R.string.label_device_model), parameter.getDeviceModel()));
        items.add(new ParameterAdapter.ParameterItem(
                getString(R.string.label_wafer_lot), parameter.getWaferLot()));
        items.add(new ParameterAdapter.ParameterItem(
                getString(R.string.label_threshold_voltage),
                getString(R.string.value_voltage, parameter.getThresholdVoltage())));
        items.add(new ParameterAdapter.ParameterItem(
                getString(R.string.label_leakage_current),
                getString(R.string.value_current, parameter.getLeakageCurrent())));
        items.add(new ParameterAdapter.ParameterItem(
                getString(R.string.label_test_date), parameter.getTestDate()));

        RecyclerView recyclerView = findViewById(R.id.rv_parameters);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ParameterAdapter(items));

        // 返回录入页继续登记
        findViewById(R.id.btn_back_to_form).setOnClickListener(v -> finish());
    }

    /** 读取字符串型 Intent 数据，缺失时返回空字符串。 */
    private String readString(Intent intent, String key) {
        String value = intent.getStringExtra(key);
        return value == null ? "" : value;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getOnBackPressedDispatcher().onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}