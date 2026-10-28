package com.example.ttst;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

/**
 * 第二个页面：从 Intent 中取出芯片型号与工艺节点，并在文本控件上完整展示。
 */
public class ChipDetailActivity extends AppCompatActivity {

    /** 未接收到工艺节点时的默认值。 */
    private static final int INVALID_PROCESS_NODE = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chip_detail);

        setupToolbar();
        showReceivedData();
    }

    /**
     * 配置顶部工具栏，并显示返回箭头。
     */
    private void setupToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * 接收 Intent 传递的数据，并展示到对应的文本控件上。
     */
    private void showReceivedData() {
        Intent intent = getIntent();
        String chipModel = intent.getStringExtra(MainActivity.EXTRA_CHIP_MODEL);
        int processNode = intent.getIntExtra(MainActivity.EXTRA_PROCESS_NODE,
                INVALID_PROCESS_NODE);

        TextView tvChipModel = findViewById(R.id.tv_chip_model);
        TextView tvProcessNode = findViewById(R.id.tv_process_node);
        TextView tvSummary = findViewById(R.id.tv_summary);

        boolean hasChipModel = !TextUtils.isEmpty(chipModel);
        boolean hasProcessNode = processNode != INVALID_PROCESS_NODE;

        // 分别展示 String 类型的芯片型号与 int 类型的工艺节点
        tvChipModel.setText(hasChipModel
                ? chipModel : getString(R.string.value_not_received));
        tvProcessNode.setText(hasProcessNode
                ? getString(R.string.value_process_node, processNode)
                : getString(R.string.value_not_received));

        // 将两项数据组合成一句完整描述，便于核对跨页面传递效果
        if (hasChipModel && hasProcessNode) {
            tvSummary.setText(getString(R.string.chip_summary, chipModel,
                    getString(R.string.value_process_node, processNode)));
        } else {
            tvSummary.setText(R.string.summary_incomplete);
        }
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