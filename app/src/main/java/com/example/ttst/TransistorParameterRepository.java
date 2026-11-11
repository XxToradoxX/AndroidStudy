package com.example.ttst;

import java.util.Arrays;
import java.util.List;

/**
 * 三极管参数参考值数据源。
 *
 * <p>数值为常见器件的典型参考范围，实际参数随型号、温度与工作电流变化，
 * 选型与设计时以器件手册为准。</p>
 */
public final class TransistorParameterRepository {

    private TransistorParameterRepository() {
        // 纯数据工具类，禁止实例化
    }

    /** 电流放大倍数与导通压降参考值。 */
    public static List<ParameterItem> currentGainItems() {
        return Arrays.asList(
                new ParameterItem("小信号管 2N3904", "100 ~ 300", "hFE 分档，I_C = 10 mA 时典型 200"),
                new ParameterItem("小信号管 9014", "100 ~ 600", "同型号按 β 分档，实测离散较大"),
                new ParameterItem("功率管 TIP41C", "15 ~ 75", "大电流下 β 明显下降，需更大基极驱动"),
                new ParameterItem("达林顿管 TIP122", "≥ 1000", "内部两只三极管复合，β 极高"),
                new ParameterItem("发射结压降 V_BE(on)", "0.6 ~ 0.7 V", "硅管导通时；锗管约 0.2 ~ 0.3 V"),
                new ParameterItem("饱和压降 V_CE(sat)", "0.1 ~ 0.3 V", "小信号管深度饱和；功率管可达 1 V 以上"));
    }

    /** 极限参数参考值。 */
    public static List<ParameterItem> limitItems() {
        return Arrays.asList(
                new ParameterItem("最大集电极电流 I_C(max)", "200 mA / 6 A", "2N3904 / TIP41C"),
                new ParameterItem("击穿电压 V_CEO", "40 V / 100 V", "2N3904 / TIP41C，基极开路时 C-E 耐压"),
                new ParameterItem("最大耗散功率 P_D", "625 mW / 65 W", "2N3904 无散热 / TIP41C 加散热器"),
                new ParameterItem("特征频率 f_T", "300 MHz / 3 MHz", "2N3904 高频小信号 / TIP41C 低频功率"));
    }
}