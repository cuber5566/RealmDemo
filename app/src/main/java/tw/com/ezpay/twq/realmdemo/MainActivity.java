package tw.com.ezpay.twq.realmdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;


public class MainActivity extends AppCompatActivity implements Handler.Callback {

    private static final int MSG_GET_REALM = 0x10;
    private static final int MSG_SAVE_REALM = 0x11;
    private static final int MSG_SAVE_REALM_LIST = 0x12;
    private static final int MSG_DISPLAY = 0x13;

    private static final int MSG_SAVE_100000 = 0x99;

    private Handler uiHandler;

    private Realm realm;

    private ListView listView;
    private TextView count;

    private RealmResults<Person> persons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        uiHandler = new Handler(getMainLooper(), this);
        listView = (ListView) findViewById(R.id.listView);
        count = (TextView) findViewById(R.id.count);


        /* Realm */
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name("Realm")
                        // The provided key must be 64 bytes
                .encryptionKey((
                        "1234567890" +
                                "1234567890" +
                                "1234567890" +
                                "1234567890" +
                                "1234567890" +
                                "1234567890" +
                                "1234"
                ).getBytes())
                .build();

        realm = Realm.getInstance(realmConfiguration);
    }

    @Override
    public boolean handleMessage(Message msg) {

        switch (msg.what) {


            case MSG_GET_REALM:

                persons = realm.where(Person.class)
//                        .equalTo("name", "Rick")
//                        .findAllSorted("id")
                        .findAll();

                uiHandler.sendEmptyMessage(MSG_DISPLAY);

                break;

            case MSG_SAVE_REALM:

                realm.beginTransaction();

                Person person = realm.createObject(Person.class);
                person.setId(persons.size());
                person.setName("Rick");
                person.setTitle("神之Android開發者");
                person.setIsMarry(true);
                person.setIsGod(true);

                realm.commitTransaction();

                /* may be U don't want to save now*/
                //realm.cancelTransaction();

                break;

            case MSG_SAVE_REALM_LIST:

                realm.beginTransaction();

                /* if U have primary key, realm can update by it */
                realm.copyToRealmOrUpdate(persons);

                /* just save */
                realm.copyToRealm(persons);

                realm.commitTransaction();

                break;

            case MSG_DISPLAY:

                listView.setAdapter(new MyAdapter(persons));
                break;

            case MSG_SAVE_100000:

                new MaterialDialog.Builder(MainActivity.this)
                        .content("Realm Save...")
                        .progress(true, 0)
                        .show();

                for (int i = 0; i < 1_000; i++) {

                    uiHandler.sendEmptyMessage(MSG_SAVE_REALM);
                }

                break;
        }

        return false;
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
            textView.setText(String.format("id = %d\nname =  %s\ntitle = %s", person.getId(), person.getName(), person.getTitle()));
            return textView;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_get:
                uiHandler.sendEmptyMessage(MSG_GET_REALM);
                break;

            case R.id.action_save:
                uiHandler.sendEmptyMessage(MSG_SAVE_REALM);
                break;

            case R.id.action_save_list:
                uiHandler.sendEmptyMessage(MSG_SAVE_REALM_LIST);
                break;

            case R.id.action_display:
                uiHandler.sendEmptyMessage(MSG_DISPLAY);

            case R.id.action_1_0000:
                uiHandler.sendEmptyMessage(MSG_SAVE_100000);

        }
        return super.onOptionsItemSelected(item);
    }
}
