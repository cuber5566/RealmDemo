package tw.com.ezpay.twq.realmdemo.models;

import io.realm.RealmObject;

/**
 * Created by cuber on 2015/7/24.
 */
public class RealmPerson extends RealmObject {

    private String name;

    private String title;

    /* getter & setter */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
