package com.example.ttst;

import java.util.ArrayList;
import java.util.List;

/**
 * 模拟数据源：提供一批微电子器件信息，供器件列表页展示。
 *
 * <p>实际项目中数据可能来自网络接口或本地数据库，此处用固定数据模拟。</p>
 */
public final class DeviceRepository {

    private DeviceRepository() {
        // 工具类，禁止实例化
    }

    /**
     * 加载模拟的微电子器件列表。
     *
     * @return 包含多条器件信息的列表
     */
    public static List<MicroDevice> loadDevices() {
        List<MicroDevice> devices = new ArrayList<>();

        devices.add(new MicroDevice("二极管", "二极管（Diode）",
                "具有单向导电性，常用于整流、检波、限幅和电路保护。"));
        devices.add(new MicroDevice("发光二极管（LED）", "光电器件",
                "将电能直接转换为光能，用于指示灯、背光与显示。"));
        devices.add(new MicroDevice("稳压二极管", "二极管（Diode）",
                "工作在反向击穿区，为电路提供稳定的参考电压。"));
        devices.add(new MicroDevice("肖特基二极管", "二极管（Diode）",
                "正向压降低、开关速度快，常用于开关电源续流。"));
        devices.add(new MicroDevice("光电二极管", "光电器件",
                "把光信号转换为光电流，用于光通信、光电检测与遥控接收。"));
        devices.add(new MicroDevice("三极管（BJT）", "双极型晶体管",
                "电流控制电流，用于信号放大与开关电路。"));
        devices.add(new MicroDevice("MOS 管（MOSFET）", "场效应晶体管",
                "电压控制导通，用于数字逻辑、电源管理与功率开关。"));
        devices.add(new MicroDevice("结型场效应管（JFET）", "场效应晶体管",
                "输入阻抗高、噪声低，适合模拟前端的小信号放大。"));
        devices.add(new MicroDevice("IGBT", "复合功率器件",
                "兼具 MOS 驱动简单与 BJT 通流能力强的优点，用于电机驱动与逆变器。"));
        devices.add(new MicroDevice("晶闸管（SCR）", "功率器件",
                "可控单向导通，用于大功率整流、交流调压与过压保护。"));
        devices.add(new MicroDevice("变容二极管", "二极管（Diode）",
                "结电容随反向电压变化，用于调谐电路与锁相环。"));
        devices.add(new MicroDevice("CMOS 图像传感器", "光电器件",
                "将光信号转换为数字图像，是手机与相机成像的核心器件。"));

        return devices;
    }
}