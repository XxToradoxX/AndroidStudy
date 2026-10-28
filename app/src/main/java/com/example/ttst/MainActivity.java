package com.example.ttst;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/**
 * 第一个页面：录入芯片型号、选择工艺节点，并通过 Intent 将两项数据传递到详情页。
 */
public class MainActivity extends AppCompatActivity {

    /** Intent 中“芯片型号”的 key，对应 String 类型数据。 */
    public static final String EXTRA_CHIP_MODEL = "extra_chip_model";

    /** Intent 中“工艺节点”的 key，对应 int 类型数据，单位为 nm。 */
    public static final String EXTRA_PROCESS_NODE = "extra_process_node";

    /** 未选择工艺节点时的返回值。 */
    private static final int INVALID_PROCESS_NODE = -1;

    private TextInputLayout tilChipModel;
    private TextInputEditText etChipModel;
    private RadioGroup rgProcessNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialToolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        tilChipModel = findViewById(R.id.til_chip_model);
        etChipModel = findViewById(R.id.et_chip_model);
        rgProcessNode = findViewById(R.id.rg_process_node);

        // 需求：按钮点击事件——校验通过后跳转到第二个 Activity 并传递数据
        findViewById(R.id.btn_pass_data).setOnClickListener(v -> passDataToDetail());
    }

    /**
     * 读取输入内容，完成简单判空后通过 Intent 传递数据。
     */
    private void passDataToDetail() {
        String chipModel = etChipModel.getText() == null
                ? "" : etChipModel.getText().toString().trim();

        // 简单判空处理：芯片型号为空时提示用户，并中断跳转
        if (TextUtils.isEmpty(chipModel)) {
            tilChipModel.setError(getString(R.string.error_chip_model_empty));
            etChipModel.requestFocus();
            Toast.makeText(this, R.string.toast_chip_model_empty, Toast.LENGTH_SHORT).show();
            return;
        }
        tilChipModel.setError(null);

        int processNode = getSelectedProcessNode();
        if (processNode == INVALID_PROCESS_NODE) {
            Toast.makeText(this, R.string.toast_process_node_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        // 使用 putExtra() 传递 String 类型的芯片型号与 int 类型的工艺节点
        Intent intent = new Intent(MainActivity.this, ChipDetailActivity.class);
        intent.putExtra(EXTRA_CHIP_MODEL, chipModel);
        intent.putExtra(EXTRA_PROCESS_NODE, processNode);
        startActivity(intent);
    }

    /**
     * 将选中的单选按钮映射为对应的工艺节点数值（单位 nm）。
     *
     * @return 选中的工艺节点；未选择时返回 {@link #INVALID_PROCESS_NODE}。
     */
    private int getSelectedProcessNode() {
        int checkedId = rgProcessNode.getCheckedRadioButtonId();
        if (checkedId == R.id.rb_node_2) {
            return 2;
        } else if (checkedId == R.id.rb_node_3) {
            return 3;
        } else if (checkedId == R.id.rb_node_4) {
            return 4;
        } else if (checkedId == R.id.rb_node_5) {
            return 5;
        } else if (checkedId == R.id.rb_node_7) {
            return 7;
        }
        return INVALID_PROCESS_NODE;
    }
}