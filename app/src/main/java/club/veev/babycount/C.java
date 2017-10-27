package club.veev.babycount;

/**
 * Created by Veev on 2017/10/25.
 * QQ: 384506557
 * Fun: 常量
 */
public interface C {
    interface event {
        String CATEGORY_CHANGED = "club.veev.babycount.event.CATEGORY_CHANGED";     // 分类
        String RECORD_CHANGED = "club.veev.babycount.event.RECORD_CHANGED";         // 记录
        String PLACE_CHANGED = "club.veev.babycount.event.PLACE_CHANGED";           // 地点
        String PERSON_CHANGED = "club.veev.babycount.event.PERSON_CHANGED";         // 人物
    }

    interface key {
        String RECORD_ID = "club.veev.babycount.key.RECORD_ID";
        String PLACE_ID = "club.veev.babycount.key.PLACE_ID";
    }
}
