package groucode.sikerja.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import groucode.sikerja.R;
import groucode.sikerja.app.AppConfig;

/**
 * Created by ASUS on 08/12/2017.
 */

public class DetailPelatihanActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private String JSON_STRING;
    private SwipeRefreshLayout swipeRefreshLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailpelatihan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


//        Intent intent = getIntent();
//        final String id = intent.getStringExtra(AppConfig.TAG_ID);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

//                                        getData(id);
                                        swipeRefreshLayout.setRefreshing(false);
                                    }
                                }
        );



//        getData(id);

    }

    public void onRefresh() {

//        Intent intent = getIntent();
//
//        final String id = intent.getStringExtra(AppConfig.TAG_ID);
//        getData(id);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            // finish the activity
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
