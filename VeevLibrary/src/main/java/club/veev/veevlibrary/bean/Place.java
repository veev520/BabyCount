package club.veev.veevlibrary.bean;

import club.veev.veevlibrary.utils.TimeUtil;

/**
 * Created by Veev on 2017/10/19
 * Tel:         18365264930
 * QQ:          2355738466
 * Email:       wei.wang@wuliangroup.com
 * Function:    表 地点
 */
public class Place {

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
     * 位置
     */
    private String location;

    /**
     * 创建时间
     */
    private long createdAt;

    /**
     * 更新时间
     */
    private long updatedAt;

    public Place(int id, String name, String desc, String location, long createdAt, long updatedAt) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.location = location;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Place{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", desc='").append(desc).append('\'');
        sb.append(", location='").append(location).append('\'');
        sb.append(", createdAt=").append(TimeUtil.getShowTime(createdAt));
        sb.append(", updatedAt=").append(TimeUtil.getShowTime(updatedAt));
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
