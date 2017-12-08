package groucode.sikerja.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import groucode.sikerja.R;
import groucode.sikerja.app.AppConfig;
import groucode.sikerja.helper.RequestHandler;

/**
 * Created by ASUS on 08/12/2017.
 */

public class DetailPelatihanActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private String JSON_STRING;
    private SwipeRefreshLayout swipeRefreshLayout;

    private TextView txtnamapelatihan, txtpenyelenggara, txtbidang, txtwaktu, txttempat, txtkuota, txtcaramendaftar, txtbiaya, txtfasilitas;
    private ImageView imgLogoPelatihan;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailpelatihan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtnamapelatihan = (TextView) findViewById(R.id.nama_pelatihan);
        txtpenyelenggara = (TextView) findViewById(R.id.penyelenggara);
        txtbidang = (TextView) findViewById(R.id.bidang);
        txtwaktu = (TextView) findViewById(R.id.waktu);
        txttempat= (TextView) findViewById(R.id.tempat);
        txtkuota = (TextView) findViewById(R.id.kuota);
        txtcaramendaftar = (TextView) findViewById(R.id.daftar);
        txtbiaya = (TextView) findViewById(R.id.biaya);
        txtfasilitas = (TextView) findViewById(R.id.fasilitas);

        imgLogoPelatihan = (ImageView) findViewById(R.id.logopelatihan);



        Intent intent = getIntent();
        final String id = intent.getStringExtra(AppConfig.TAG_ID);


        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        getData(id);
                                        swipeRefreshLayout.setRefreshing(false);
                                    }
                                }
        );



        getData(id);

    }

    public void onRefresh() {

        Intent intent = getIntent();

        final String id = intent.getStringExtra(AppConfig.TAG_ID);
        getData(id);
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

    private void getData(final String id){
        swipeRefreshLayout.setRefreshing(true);
        class GetLowongan extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailPelatihanActivity.this,"Mempersiapkan Data...","tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showPelatihan(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(AppConfig.URL_GETDetailPelatihan, id);
                return s;
            }
        }
        GetLowongan gl = new GetLowongan();
        gl.execute();
        swipeRefreshLayout.setRefreshing(false);
    }

    private void showPelatihan(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray("result");
            JSONObject jo = result.getJSONObject(0);
            String id = jo.getString(AppConfig.TAG_ID);
            String namapelatihan = jo.getString(AppConfig.TAG_NAMAPELATIHAN);
            String logopelatihan = jo.getString(AppConfig.TAG_LOGOPELATIHAN);
            String bidang = jo.getString(AppConfig.TAG_BIDANG);
            String penyelenggara = jo.getString(AppConfig.TAG_PENYELENGGARA);
            String waktu = jo.getString(AppConfig.TAG_WAKTU);
            String tempat = jo.getString(AppConfig.TAG_TEMPAT);
            String kuota = jo.getString(AppConfig.TAG_KUOTA);
            String caramendaftar = jo.getString(AppConfig.TAG_CARAMENDAFTAR);
            String biaya = jo.getString(AppConfig.TAG_BIAYA);
            String fasilitas = jo.getString(AppConfig.TAG_FASILITAS);



            txtnamapelatihan.setText(namapelatihan);
            txtpenyelenggara.setText(penyelenggara);
            txtbidang.setText(bidang);
            txtwaktu.setText(waktu);
            txttempat.setText(tempat);
            txtkuota.setText(kuota);
            txtcaramendaftar.setText(caramendaftar);
            txtbiaya.setText(biaya);
            txtfasilitas.setText(fasilitas);

            Glide.with(this).load(logopelatihan)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgLogoPelatihan);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
