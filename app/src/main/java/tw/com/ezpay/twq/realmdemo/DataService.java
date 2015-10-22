package tw.com.ezpay.twq.realmdemo;

import java.util.List;

import rx.Observable;
import tw.com.ezpay.twq.realmdemo.models.Person;

public interface DataService {

    Observable<Person> addPerson(String name, String title);
    Observable<List<Person>> listPerson();
}
