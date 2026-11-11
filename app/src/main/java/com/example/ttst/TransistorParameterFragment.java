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
 * 三极管参数页 Fragment。
 *
 * <p>展示电流放大倍数、发射结压降等导通参数，以及最大集电极电流、击穿电压等
 * 极限参数，数据来自 {@link TransistorParameterRepository}。</p>
 */
public class TransistorParameterFragment extends Fragment {

    public TransistorParameterFragment() {
        super(R.layout.fragment_device_parameter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindParameterRows(view.findViewById(R.id.container_current_gain),
                TransistorParameterRepository.currentGainItems());
        bindParameterRows(view.findViewById(R.id.container_limits),
                TransistorParameterRepository.limitItems());
    }

    /** 把一组参数逐行填充到容器中。 */
    private void bindParameterRows(LinearLayout container, List<ParameterItem> items) {
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        for (ParameterItem item : items) {
            View row = inflater.inflate(R.layout.item_parameter, container, false);
            TextView name = row.findViewById(R.id.tv_parameter_name);
            TextView value = row.findViewById(R.id.tv_parameter_value);
            TextView note = row.findViewById(R.id.tv_parameter_note);

            name.setText(item.getName());
            value.setText(item.getValue());
            note.setText(item.getNote());

            // item_parameter 自带 8dp 上边距，直接添加即可保持行间距
            container.addView(row);
        }
    }
}