package com.example.ttst;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * 参数列表适配器：把「参数名称 + 参数值」逐行绑定到 item_parameter 布局。
 */
public class ParameterAdapter extends RecyclerView.Adapter<ParameterAdapter.ParameterViewHolder> {

    private final List<ParameterItem> items;

    public ParameterAdapter(List<ParameterItem> items) {
        this.items = items;
    }

    /** 一行参数数据：左侧名称，右侧数值。 */
    public static class ParameterItem {

        private final String label;
        private final String value;

        public ParameterItem(String label, String value) {
            this.label = label;
            this.value = value;
        }
    }

    @NonNull
    @Override
    public ParameterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_parameter, parent, false);
        return new ParameterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParameterViewHolder holder, int position) {
        ParameterItem item = items.get(position);
        holder.tvLabel.setText(item.label);
        holder.tvValue.setText(item.value);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    /** 缓存一行参数中的名称与数值控件。 */
    static class ParameterViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvLabel;
        private final TextView tvValue;

        ParameterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLabel = itemView.findViewById(R.id.tv_parameter_label);
            tvValue = itemView.findViewById(R.id.tv_parameter_value);
        }
    }
}