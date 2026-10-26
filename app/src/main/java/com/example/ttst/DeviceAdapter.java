package com.example.ttst;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 器件列表适配器：把 {@link MicroDevice} 数据绑定到 item_device 布局上。
 */
public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder> {

    private final List<MicroDevice> devices = new ArrayList<>();

    public DeviceAdapter(List<MicroDevice> deviceList) {
        if (deviceList != null) {
            devices.addAll(deviceList);
        }
    }

    @NonNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_device, parent, false);
        return new DeviceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceViewHolder holder, int position) {
        MicroDevice device = devices.get(position);
        holder.nameText.setText(device.getName());
        holder.typeText.setText(device.getType());
        holder.usageText.setText(device.getUsage());
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    /**
     * 列表项视图持有者，缓存 item 中的三个文本控件。
     */
    static class DeviceViewHolder extends RecyclerView.ViewHolder {

        final TextView nameText;
        final TextView typeText;
        final TextView usageText;

        DeviceViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.tv_device_name);
            typeText = itemView.findViewById(R.id.tv_device_type);
            usageText = itemView.findViewById(R.id.tv_device_usage);
        }
    }
}