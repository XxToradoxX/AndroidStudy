package com.example.ttst;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

/**
 * 参数参考页 Fragment。
 *
 * <p>展示二极管的两项关键参数参考值：正向压降 V_F 与最大整流电流 I_F(max)。
 * 参考数据由 {@link DiodeParameterRepository} 统一提供，页面按行动态填充。</p>
 */
public class DeviceParameterFragment extends Fragment {

    public DeviceParameterFragment() {
        super(R.layout.fragment_device_parameter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 正向压降参考值：硅管、锗管、肖特基与 LED
        bindParameterRows(view.findViewById(R.id.container_forward_voltage),
                DiodeParameterRepository.forwardVoltageItems());

        // 最大整流电流参考值：常见整流二极管的额定电流
        bindParameterRows(view.findViewById(R.id.container_max_current),
                DiodeParameterRepository.maxRectifiedCurrentItems());
    }

    /** 把参数列表逐行填充到容器中。 */
    private void bindParameterRows(LinearLayout container, List<ParameterItem> items) {
        container.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        for (ParameterItem item : items) {
            View row = inflater.inflate(R.layout.item_parameter, container, false);
            ((TextView) row.findViewById(R.id.tv_parameter_name)).setText(item.getName());
            ((TextView) row.findViewById(R.id.tv_parameter_value)).setText(item.getValue());
            ((TextView) row.findViewById(R.id.tv_parameter_note)).setText(item.getNote());
            container.addView(row);
        }
    }
}