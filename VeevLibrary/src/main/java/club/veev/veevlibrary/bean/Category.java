package club.veev.veevlibrary.bean;

import club.veev.veevlibrary.utils.WTime;

/**
 * Created by Veev on 2017/10/11
 * Tel:         18365264930
 * QQ:          2355738466
 * Email:       wei.wang@wuliangroup.com
 * Function:    Category
 */
public class Category {
    private int id;
    /**
     * 名称
     */
    private String name;
    /**
     * 描述
     */
    private String desc;
    /**
     * 单位
     */
    private String unit;
    /**
     * 创建时间
     */
    private long createdAt;

    /**
     * 更新时间
     */
    private long updatedAt;

    public Category(int id, String name, String desc, String unit) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.unit = unit;
    }

    public Category(int id, String name, String desc, String unit, long createdAt, long updatedAt) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.unit = unit;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Category{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", desc='").append(desc).append('\'');
        sb.append(", unit='").append(unit).append('\'');
        sb.append(", createdAt=").append(WTime.getShowTime(createdAt));
        sb.append(", updatedAt=").append(WTime.getShowTime(updatedAt));
        sb.append('}');
        return sb.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }
}
