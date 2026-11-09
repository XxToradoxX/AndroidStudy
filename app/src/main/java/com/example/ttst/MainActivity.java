package com.example.ttst;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * 器件参数录入页。
 *
 * <p>收集器件型号、晶圆批次、阈值电压、漏电流与测试日期，完成非空校验、
 * 数值范围校验和日期格式校验；校验失败通过 Toast 提示，校验通过后携带
 * 器件参数跳转到参数列表页。</p>
 */
public class MainActivity extends AppCompatActivity {

    /** 测试日期格式。 */
    private static final String DATE_PATTERN = "yyyy-MM-dd";

    private TextInputLayout tilDeviceModel;
    private TextInputLayout tilWaferLot;
    private TextInputLayout tilThresholdVoltage;
    private TextInputLayout tilLeakageCurrent;
    private TextInputLayout tilTestDate;

    private TextInputEditText etDeviceModel;
    private TextInputEditText etWaferLot;
    private TextInputEditText etThresholdVoltage;
    private TextInputEditText etLeakageCurrent;
    private TextInputEditText etTestDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialToolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        bindViews();

        // 点击日期输入框右侧的日历图标，弹出日期选择器
        tilTestDate.setEndIconOnClickListener(v -> showDatePicker());

        // 需求：点击提交保存按钮，先校验再跳转
        findViewById(R.id.btn_submit).setOnClickListener(v -> submitParameter());
    }

    /** 绑定布局中的输入框与输入容器。 */
    private void bindViews() {
        tilDeviceModel = findViewById(R.id.til_device_model);
        tilWaferLot = findViewById(R.id.til_wafer_lot);
        tilThresholdVoltage = findViewById(R.id.til_threshold_voltage);
        tilLeakageCurrent = findViewById(R.id.til_leakage_current);
        tilTestDate = findViewById(R.id.til_test_date);

        etDeviceModel = findViewById(R.id.et_device_model);
        etWaferLot = findViewById(R.id.et_wafer_lot);
        etThresholdVoltage = findViewById(R.id.et_threshold_voltage);
        etLeakageCurrent = findViewById(R.id.et_leakage_current);
        etTestDate = findViewById(R.id.et_test_date);
    }

    /**
     * 读取并校验全部输入，校验通过后跳转到参数列表页。
     *
     * <p>校验顺序：文本字段非空 → 数值字段非空且为大于 0 的有限数值 →
     * 日期非空且格式正确。任何一步失败都会弹出 Toast 并终止跳转。</p>
     */
    private void submitParameter() {
        String deviceModel = textOf(etDeviceModel);
        String waferLot = textOf(etWaferLot);
        String thresholdText = textOf(etThresholdVoltage);
        String leakageText = textOf(etLeakageCurrent);
        String testDate = textOf(etTestDate);

        // 1. 非空校验：器件型号
        if (!validateRequired(deviceModel, tilDeviceModel, etDeviceModel,
                R.string.error_device_model_empty, R.string.toast_device_model_empty)) {
            return;
        }

        // 2. 非空校验：晶圆批次
        if (!validateRequired(waferLot, tilWaferLot, etWaferLot,
                R.string.error_wafer_lot_empty, R.string.toast_wafer_lot_empty)) {
            return;
        }

        // 3. 非空校验：阈值电压
        if (!validateRequired(thresholdText, tilThresholdVoltage, etThresholdVoltage,
                R.string.error_threshold_voltage_empty,
                R.string.toast_threshold_voltage_empty)) {
            return;
        }

        // 4. 范围校验：阈值电压必须是大于 0 的有限数值
        Double thresholdVoltage = parsePositiveDouble(thresholdText);
        if (thresholdVoltage == null) {
            showFieldError(tilThresholdVoltage, etThresholdVoltage,
                    R.string.error_threshold_voltage_invalid,
                    R.string.toast_threshold_voltage_invalid);
            return;
        }
        tilThresholdVoltage.setError(null);

        // 5. 非空校验：漏电流
        if (!validateRequired(leakageText, tilLeakageCurrent, etLeakageCurrent,
                R.string.error_leakage_current_empty,
                R.string.toast_leakage_current_empty)) {
            return;
        }

        // 6. 范围校验：漏电流必须是大于 0 的有限数值
        Double leakageCurrent = parsePositiveDouble(leakageText);
        if (leakageCurrent == null) {
            showFieldError(tilLeakageCurrent, etLeakageCurrent,
                    R.string.error_leakage_current_invalid,
                    R.string.toast_leakage_current_invalid);
            return;
        }
        tilLeakageCurrent.setError(null);

        // 7. 日期非空与格式校验
        if (!validateRequired(testDate, tilTestDate, etTestDate,
                R.string.error_test_date_empty, R.string.toast_test_date_empty)) {
            return;
        }
        if (!isValidDate(testDate)) {
            showFieldError(tilTestDate, etTestDate,
                    R.string.error_test_date_invalid,
                    R.string.toast_test_date_invalid);
            return;
        }
        tilTestDate.setError(null);

        // 8. 校验通过：封装参数并跳转到参数列表页
        DeviceParameter parameter = new DeviceParameter(
                deviceModel, waferLot, thresholdVoltage, leakageCurrent, testDate);
        startActivity(ParameterListActivity.createIntent(this, parameter));
    }

    /**
     * 非空校验。
     *
     * @return 输入非空返回 true；为空时显示错误、弹出 Toast 并返回 false。
     */
    private boolean validateRequired(String value, TextInputLayout inputLayout,
                                     TextInputEditText editText,
                                     int errorRes, int toastRes) {
        if (TextUtils.isEmpty(value)) {
            showFieldError(inputLayout, editText, errorRes, toastRes);
            return false;
        }
        inputLayout.setError(null);
        return true;
    }

    /**
     * 把字符串解析为合法正数（大于 0 且有限）。
     *
     * @return 解析成功返回数值；格式错误、NaN、无穷大或非正数返回 null。
     */
    private Double parsePositiveDouble(String raw) {
        try {
            double value = Double.parseDouble(raw);
            if (!Double.isFinite(value) || value <= 0.0) {
                return null;
            }
            return value;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /** 在输入框下方显示错误，同时弹出 Toast 提示，并把焦点移回该输入框。 */
    private void showFieldError(TextInputLayout inputLayout, TextInputEditText editText,
                                int errorRes, int toastRes) {
        inputLayout.setError(getString(errorRes));
        editText.requestFocus();
        Toast.makeText(this, toastRes, Toast.LENGTH_SHORT).show();
    }

    /** 判断日期是否为合法的 yyyy-MM-dd 格式。 */
    private boolean isValidDate(String raw) {
        if (!raw.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return false;
        }
        SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN, Locale.US);
        format.setLenient(false);
        try {
            format.parse(raw);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /** 弹出日期选择器，选中后把日期写回测试日期输入框。 */
    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    String date = String.format(Locale.US, "%04d-%02d-%02d",
                            year, month + 1, dayOfMonth);
                    etTestDate.setText(date);
                    etTestDate.setSelection(date.length());
                    tilTestDate.setError(null);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    /** 读取输入框文本并去除首尾空格；空内容返回空字符串。 */
    private String textOf(TextInputEditText editText) {
        return editText.getText() == null ? "" : editText.getText().toString().trim();
    }
}