package club.veev.veevlibrary.bean;

import club.veev.veevlibrary.utils.TimeUtil;

/**
 * Created by Veev on 2017/10/15
 * Tel:         18365264930
 * QQ:          2355738466
 * Email:       wei.wang@wuliangroup.com
 * Function:    Record
 */
public class Record {
    private int id;

    /**
     * 分类
     */
    private Category category;
    /**
     * 名称
     */
    private String title;
    /**
     * 数据
     */
    private float value;
    /**
     * 描述
     */
    private String desc;
    /**
     * 单位
     */
    private String unit;
    /**
     * 位置
     */
    private Place place;
    /**
     * 目标人物
     */
    private Person target;
    /**
     * 作用人物
     */
    private Person source;

    /**
     * 记录时间
     */
    private long time;

    /**
     * 创建时间
     */
    private long createdAt;

    /**
     * 更新时间
     */
    private long updatedAt;

    public Record(int id, Category category, String title, float value, String desc, String unit,
                  Place location, Person target, Person source, long time, long createdAt, long updatedAt) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.value = value;
        this.desc = desc;
        this.unit = unit;
        this.place = location;
        this.target = target;
        this.source = source;
        this.time = time;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Record{");
        sb.append("id=").append(id);
        sb.append(", category=").append(category);
        sb.append(", title='").append(title).append('\'');
        sb.append(", value=").append(value);
        sb.append(", desc='").append(desc).append('\'');
        sb.append(", unit='").append(unit).append('\'');
        sb.append(", place='").append(place).append('\'');
        sb.append(", target='").append(target).append('\'');
        sb.append(", source='").append(source).append('\'');
        sb.append(", time=").append(time);
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getValue() {
        return value;
    }

    public String getValueStr() {
        return value + "";
    }

    public void setValue(float value) {
        this.value = value;
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

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
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

    public Person getTarget() {
        return target;
    }

    public void setTarget(Person target) {
        this.target = target;
    }

    public Person getSource() {
        return source;
    }

    public void setSource(Person source) {
        this.source = source;
    }
}
