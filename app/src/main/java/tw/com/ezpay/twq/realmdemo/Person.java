package tw.com.ezpay.twq.realmdemo;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by cuber on 2015/7/24.
 */
public class Person extends RealmObject {

    @PrimaryKey
    private int id;

    private String name;

    private String title;

    private boolean isMarry;

    @Ignore
    private boolean isGod;


    /* getter & setter */
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isMarry() {
        return isMarry;
    }

    public void setIsMarry(boolean isMarry) {
        this.isMarry = isMarry;
    }

    public boolean isGod() {
        return isGod;
    }

    public void setIsGod(boolean isGod) {
        this.isGod = isGod;
    }
}
