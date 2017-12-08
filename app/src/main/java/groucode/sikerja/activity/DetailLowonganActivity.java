package groucode.sikerja.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import groucode.sikerja.R;
import groucode.sikerja.app.AppConfig;
import groucode.sikerja.helper.RequestHandler;

/**
 * Created by ASUS on 07/12/2017.
 */

public class DetailLowonganActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private String JSON_STRING;
    private SwipeRefreshLayout swipeRefreshLayout;

    TextView txtbataswaktu, txtnamaperusahaan, txtjabatan, txtlokasi, txtdeskripsi, txtpersyaratan, txtcaradaftar, txtalamatkantor, txtwebsite, txtjumlahkaryawan, txtdeskripsiperusahaan;
    ImageView imgLogoPerusahaan;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detaillowongan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtbataswaktu = (TextView) findViewById(R.id.batas_waktu);
        txtnamaperusahaan = (TextView) findViewById(R.id.nama_perusahaan);
        txtjabatan = (TextView) findViewById(R.id.jabatan);
        txtlokasi = (TextView) findViewById(R.id.lokasi);
        txtdeskripsi = (TextView) findViewById(R.id.deskripsi);
        txtpersyaratan = (TextView) findViewById(R.id.persyaratan);
        txtcaradaftar = (TextView) findViewById(R.id.caradaftar);
        txtalamatkantor = (TextView) findViewById(R.id.lokasiperusahaan);
        txtwebsite = (TextView) findViewById(R.id.website);
        txtjumlahkaryawan = (TextView) findViewById(R.id.pegawai);
        txtdeskripsiperusahaan = (TextView) findViewById(R.id.deskripsiperusahaan);

        imgLogoPerusahaan = (ImageView) findViewById(R.id.logoperusahaan);


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
                loading = ProgressDialog.show(DetailLowonganActivity.this,"Mempersiapkan Data...","tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showLowongan(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(AppConfig.URL_GETDetailLowongan, id);
                return s;
            }
        }
        GetLowongan gl = new GetLowongan();
        gl.execute();
        swipeRefreshLayout.setRefreshing(false);
    }

    private void showLowongan(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray("result");
            JSONObject jo = result.getJSONObject(0);
            String id = jo.getString(AppConfig.TAG_ID);
            String bataswaktu = jo.getString(AppConfig.TAG_BATASWAKTU);
            String logoperusahaan = jo.getString(AppConfig.TAG_LOGOPERUSAHAAN);
            String namaperusahaan = jo.getString(AppConfig.TAG_NAMAPERUSAHAAN);
            String jabatan = jo.getString(AppConfig.TAG_JABATAN);
            String lokasi = jo.getString(AppConfig.TAG_LOKASI);
            String deskripsi = jo.getString(AppConfig.TAG_DESKRIPSI);
            String persyaratan = jo.getString(AppConfig.TAG_PERSYARATAN);
            String caradaftar = jo.getString(AppConfig.TAG_CARADAFTAR);
            String alamatkantor = jo.getString(AppConfig.TAG_ALAMATKANTOR);
            String website = jo.getString(AppConfig.TAG_WEBSITE);
            String jumlahkaryawan = jo.getString(AppConfig.TAG_JUMLAHKARYAWAN);
            String deskripsiperusahaan = jo.getString(AppConfig.TAG_DESKRIPSIPERUSAHAAN);

//            Toast.makeText(this, newnoreg_berkas, Toast.LENGTH_LONG).show();

            txtbataswaktu.setText(bataswaktu);
            txtnamaperusahaan.setText(namaperusahaan);
            txtjabatan.setText(jabatan);
            txtlokasi.setText(lokasi);
            txtdeskripsi.setText(deskripsi);
            txtpersyaratan.setText(persyaratan);
            txtcaradaftar.setText(caradaftar);
            txtalamatkantor.setText(alamatkantor);
            txtwebsite.setText(website);
            txtjumlahkaryawan.setText(jumlahkaryawan);
            txtdeskripsiperusahaan.setText(deskripsiperusahaan);

            Glide.with(this).load(logoperusahaan)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgLogoPerusahaan);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
