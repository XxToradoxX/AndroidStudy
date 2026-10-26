package com.example.ttst;

/**
 * 微电子器件实体类：描述列表中一条器件信息。
 *
 * <p>包含器件名称、器件类型和简要用途三个字段，供 RecyclerView 列表展示。</p>
 */
public class MicroDevice {

    /** 器件名称，例如“二极管”。 */
    private final String name;

    /** 器件类型，例如“光电器件”。 */
    private final String type;

    /** 器件简要用途。 */
    private final String usage;

    public MicroDevice(String name, String type, String usage) {
        this.name = name;
        this.type = type;
        this.usage = usage;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getUsage() {
        return usage;
    }
}