package club.veev.veevlibrary.bean;

import club.veev.veevlibrary.utils.WTime;

/**
 * Created by Veev on 2017/10/18
 * Tel:         18365264930
 * QQ:          2355738466
 * Email:       wei.wang@wuliangroup.com
 * Function:    表 人物
 */
public class Person {
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
     * 头像
     */
    private String avatar;
    /**
     * 创建时间
     */
    private long createdAt;

    /**
     * 更新时间
     */
    private long updatedAt;

    public Person(int id, String name, String desc, String avatar, long createdAt, long updatedAt) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.avatar = avatar;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Person{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", desc='").append(desc).append('\'');
        sb.append(", avatar='").append(avatar).append('\'');
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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
