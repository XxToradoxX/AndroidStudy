package com.example.ttst;

import java.util.Arrays;
import java.util.List;

/**
 * 二极管参数参考值数据源。
 *
 * <p>数值为常见器件的典型参考范围，单位已在取值中标注；实际参数随型号、
 * 温度与工作电流变化，选型时以器件手册为准。</p>
 */
public final class DiodeParameterRepository {

    private DiodeParameterRepository() {
        // 纯数据工具类，禁止实例化
    }

    /** 正向压降 V_F 参考值。 */
    public static List<ParameterItem> forwardVoltageItems() {
        return Arrays.asList(
                new ParameterItem("硅整流二极管（Si）", "0.6 ~ 0.7 V", "如 1N4007，额定 1 A 时约 0.7 V"),
                new ParameterItem("锗二极管（Ge）", "0.2 ~ 0.3 V", "如 1N60，导通压降更低，反向漏电较大"),
                new ParameterItem("肖特基二极管", "0.3 ~ 0.45 V", "如 1N5819，低压降、反向恢复快"),
                new ParameterItem("发光二极管（LED）", "1.8 ~ 3.3 V", "颜色不同压降差异明显，红管最低、蓝白管最高"));
    }

    /** 最大整流电流 I_F(max) 参考值。 */
    public static List<ParameterItem> maxRectifiedCurrentItems() {
        return Arrays.asList(
                new ParameterItem("1N4148（小信号开关）", "300 mA", "玻璃封装，常用于信号检波与高速开关"),
                new ParameterItem("1N4007（通用整流）", "1 A", "1000 V 耐压，最常见的电源整流管"),
                new ParameterItem("1N5408（大电流整流）", "3 A", "1000 V 耐压，适合功率稍大的整流电路"));
    }
}