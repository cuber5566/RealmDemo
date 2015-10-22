package tw.com.ezpay.twq.realmdemo;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tw.com.ezpay.twq.realmdemo.models.Person;


public class MainActivity extends BaseActivity {

    private ListView listView;
    private TextView count;
    private RealmDataService realmDataService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        count = (TextView) findViewById(R.id.count);

        realmDataService = new RealmDataService(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_save:

                showProgressDialog();

                realmDataService.addPerson("NAME", "TITLE")
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .finallyDo(this::hideProgressDialog)
                        .subscribe(person -> Toast.makeText(this, "save success", Toast.LENGTH_SHORT).show(), this::showError);
                break;

            case R.id.action_display:

                showProgressDialog();

                realmDataService.listPerson()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .finallyDo(this::hideProgressDialog)
                        .subscribe(persons -> listView.setAdapter(new MyAdapter(persons)), this::showError);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private class MyAdapter extends BaseAdapter {

        List<Person> personList;

        public MyAdapter(List<Person> personList) {
            this.personList = personList;
        }

        @Override
        public int getCount() {
            count.setText(String.valueOf(personList.size()));
            return personList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Person person = personList.get(position);
            TextView textView = new TextView(MainActivity.this);
            textView.setTextSize(26);
            textView.setPadding(50, 50, 50, 50);
            textView.setText(String.format("name =  %s\ntitle = %s", person.getName(), person.getTitle()));
            return textView;
        }
    }
}
