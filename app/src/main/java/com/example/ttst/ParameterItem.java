package com.example.ttst;

/**
 * 参数页的一行参考数据：参数名称 + 参考值 + 补充说明。
 *
 * <p>不可变对象，便于在页面之间安全地共享与复用。</p>
 */
public final class ParameterItem {

    private final String name;
    private final String value;
    private final String note;

    public ParameterItem(String name, String value, String note) {
        this.name = name;
        this.value = value;
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getNote() {
        return note;
    }
}