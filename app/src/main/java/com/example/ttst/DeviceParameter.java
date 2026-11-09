package com.example.ttst;

/**
 * 微电子器件参数实体类。
 *
 * <p>保存一次登记提交的全部字段，供录入页与参数列表页共同使用。</p>
 */
public class DeviceParameter {

    /** 器件型号，例如 MOSFET-A1。 */
    private final String deviceModel;

    /** 晶圆批次，例如 LOT-2026-001。 */
    private final String waferLot;

    /** 阈值电压，单位 V。 */
    private final double thresholdVoltage;

    /** 漏电流，单位 nA。 */
    private final double leakageCurrent;

    /** 测试日期，格式 yyyy-MM-dd。 */
    private final String testDate;

    public DeviceParameter(String deviceModel, String waferLot,
                           double thresholdVoltage, double leakageCurrent,
                           String testDate) {
        this.deviceModel = deviceModel;
        this.waferLot = waferLot;
        this.thresholdVoltage = thresholdVoltage;
        this.leakageCurrent = leakageCurrent;
        this.testDate = testDate;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public String getWaferLot() {
        return waferLot;
    }

    public double getThresholdVoltage() {
        return thresholdVoltage;
    }

    public double getLeakageCurrent() {
        return leakageCurrent;
    }

    public String getTestDate() {
        return testDate;
    }
}