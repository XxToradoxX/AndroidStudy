package com.example.ttst;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    // 上下文菜单项 id
    private static final int MENU_COPY_VOLTAGE = 1;
    private static final int MENU_CLEAR_SAMPLE = 2;

    private TextView tvVoltage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvVoltage = findViewById(R.id.tv_voltage);
        // 给显示实测电压的 TextView 注册上下文菜单（长按触发）
        registerForContextMenu(tvVoltage);
    }

    // ==================== 选项菜单 ====================

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_device_params) {
            showDeviceParamsDialog();
            return true;
        } else if (item.getItemId() == R.id.action_export_log) {
            Toast.makeText(this, "测试日志已暂存", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // 弹窗展示 MOS 管典型参数
    private void showDeviceParamsDialog() {
        new AlertDialog.Builder(this)
                .setTitle("MOS 管典型参数")
                .setMessage("Vgs（栅源电压）：4.5 V\n"
                        + "Id（漏极电流）：1.2 A\n"
                        + "Vds（漏源电压）：30 V")
                .setPositiveButton("确定", null)
                .show();
    }

    // ==================== 上下文菜单 ====================

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.tv_voltage) {
            menu.setHeaderTitle("电压采样操作");
            menu.add(0, MENU_COPY_VOLTAGE, 1, "复制电压值").setIcon(R.drawable.ic_copy);
            menu.add(0, MENU_CLEAR_SAMPLE, 2, "清零本次采样").setIcon(R.drawable.ic_clear);
            // 默认上下文菜单不显示图标，通过反射打开图标开关
            showContextMenuIcons(menu);
        }
    }

    private void showContextMenuIcons(ContextMenu menu) {
        try {
            Method method = menu.getClass().getMethod("setOptionalIconsVisible", boolean.class);
            method.setAccessible(true);
            method.invoke(menu, true);
        } catch (Exception ignored) {
            // 反射失败时菜单仍可用，只是不显示图标
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case MENU_COPY_VOLTAGE:
                String voltage = tvVoltage.getText().toString().trim();
                if (voltage.isEmpty()) {
                    Toast.makeText(this, "当前无采样数据", Toast.LENGTH_SHORT).show();
                } else {
                    ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    cm.setPrimaryClip(ClipData.newPlainText("voltage", voltage));
                    Toast.makeText(this, "已复制：" + voltage, Toast.LENGTH_SHORT).show();
                }
                return true;
            case MENU_CLEAR_SAMPLE:
                tvVoltage.setText("");
                Toast.makeText(this, "本次采样已清零", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
