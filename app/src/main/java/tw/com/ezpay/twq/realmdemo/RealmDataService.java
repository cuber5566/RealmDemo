package tw.com.ezpay.twq.realmdemo;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import tw.com.ezpay.twq.realmdemo.models.Person;
import tw.com.ezpay.twq.realmdemo.models.RealmPerson;
import tw.com.ezpay.twq.realmdemo.rx.RealmObservable;

public class RealmDataService implements DataService {

    private final Context context;

    public RealmDataService(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public Observable<Person> addPerson(String name, String title) {
        return RealmObservable.object(context, realm -> realm.copyToRealm(setupRealmPerson(name, title))).map(this::map);
    }

    @Override
    public Observable<List<Person>> listPerson() {
        return RealmObservable.results(context, realm -> realm.where(RealmPerson.class).findAll())
                .map(persons -> {
                    final List<Person> personList = new ArrayList<>(persons.size());
                    for (RealmPerson item : persons) personList.add(map(item));
                    return personList;
                });
    }

    private Person map(RealmPerson realmPerson) {
        final String title = realmPerson.getTitle();
        final String name = realmPerson.getName();
        return new Person(title, name);
    }

    private RealmPerson setupRealmPerson(String name, String title) {
        RealmPerson realmPerson = new RealmPerson();
        realmPerson.setName(name);
        realmPerson.setTitle(title);
        return realmPerson;
    }
}
