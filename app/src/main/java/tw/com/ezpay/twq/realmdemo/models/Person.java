package tw.com.ezpay.twq.realmdemo.models;

public class Person {

    private String name;

    private String title;

    public Person(String name, String title) {
        this.name = name;
        this.title = title;
    }

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
