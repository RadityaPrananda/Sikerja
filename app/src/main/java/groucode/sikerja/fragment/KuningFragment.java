package groucode.sikerja.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import groucode.sikerja.R;
import groucode.sikerja.activity.LoginActivity;
import groucode.sikerja.activity.MainActivity;
import groucode.sikerja.activity.RegisterActivity;
import groucode.sikerja.app.AppConfig;
import groucode.sikerja.app.AppController;
import groucode.sikerja.helper.SQLiteHandler;

public class KuningFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EditText inputNama, inputNik, inputGelardepan, inputGelarbelakang, inputTempatlahir, inputTanggallahir, inputJk, inputAgama, inputTinggibadan, inputBeratbadan, inputNomorhp, inputKecamatan, inputAlamat, inputKeterampilan, inputPendidikan, inputJurusan, inputInstitusi, inputLamastudi, inputTahunlulus, inputNilai, hasilJk;
    private Button registerKartu;
    private SQLiteHandler db;
    private RadioGroup groupJK;
    private ProgressDialog pDialog;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public KuningFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static KuningFragment newInstance(String param1, String param2) {
        KuningFragment fragment = new KuningFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_kuning, container, false);

        inputNik = (EditText) view.findViewById(R.id.nik);
        inputNama = (EditText) view.findViewById(R.id.nama);
        inputGelardepan = (EditText) view.findViewById(R.id.gelardepan);
        inputGelarbelakang = (EditText) view.findViewById(R.id.gelarbelakang);
        inputTempatlahir = (EditText) view.findViewById(R.id.tempatlahir);
        inputTanggallahir = (EditText) view.findViewById(R.id.tanggallahir);
        inputJk = (EditText) view.findViewById(R.id.jk);
        inputAgama = (EditText) view.findViewById(R.id.agama);
        inputTinggibadan = (EditText) view.findViewById(R.id.tinggibadan);
        inputBeratbadan = (EditText) view.findViewById(R.id.beratbadan);
        inputNomorhp = (EditText) view.findViewById(R.id.hp);
        inputKecamatan = (EditText) view.findViewById(R.id.kecamatan);
        inputAlamat = (EditText) view.findViewById(R.id.alamat);
        inputKeterampilan = (EditText) view.findViewById(R.id.keterampilan);
        inputPendidikan = (EditText) view.findViewById(R.id.pendidikan);
        inputJurusan = (EditText) view.findViewById(R.id.jurusan);
        inputInstitusi = (EditText) view.findViewById(R.id.institusi);
        inputLamastudi = (EditText) view.findViewById(R.id.lamastudi);
        inputTahunlulus = (EditText) view.findViewById(R.id.tahunlulus);
        inputNilai = (EditText) view.findViewById(R.id.nilai);
        groupJK = (RadioGroup) view.findViewById(R.id.groupjk);

        db = new SQLiteHandler(getContext().getApplicationContext());
        pDialog = new ProgressDialog(getContext());
        pDialog.setCancelable(false);

        view.findViewById(R.id.pria).setOnClickListener(this);
        view.findViewById(R.id.wanita).setOnClickListener(this);

        registerKartu = (Button) view.findViewById(R.id.simpan);

        HashMap<String, String> user = db.getUserDetails();
        String nama = user.get("nama");
        String nik = user.get("nik");
        String kecamatan = user.get("kecamatan");

        inputNama.setText(nama);
        inputNik.setText(nik);
        inputKecamatan.setText(kecamatan);

        registerKartu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String nik = inputNik.getText().toString();
                String nama = inputNama.getText().toString();
                String gelardepan = inputGelardepan.getText().toString();
                String gelarbelakang = inputGelarbelakang.getText().toString();
                String tempatlahir = inputTempatlahir.getText().toString();
                String tanggallahir = inputTanggallahir.getText().toString();
                String jk = inputJk.getText().toString();
                String agama = inputAgama.getText().toString();
                String tinggibadan = inputTinggibadan.getText().toString();
                String beratbadan = inputBeratbadan.getText().toString();
                String hp = inputNomorhp.getText().toString();
                String kecamatan = inputKecamatan.getText().toString();
                String alamat = inputAlamat.getText().toString();
                String keterampilan = inputKeterampilan.getText().toString();
                String pendidikan = inputPendidikan.getText().toString();
                String jurusan = inputJurusan.getText().toString();
                String institusi = inputInstitusi.getText().toString();
                String lamastudi = inputLamastudi.getText().toString();
                String tahunlulus = inputTahunlulus.getText().toString();
                String nilai = inputNilai.getText().toString();

                if (!nik.isEmpty() && !nama.isEmpty() && !tempatlahir.isEmpty() && !tanggallahir.isEmpty() && !jk.isEmpty() && !agama.isEmpty() && !tinggibadan.isEmpty() && !beratbadan.isEmpty() && !pendidikan.isEmpty() && !institusi.isEmpty() && !nilai.isEmpty()) {
                    registerUser(nik, nama, gelardepan, gelarbelakang, tempatlahir, tanggallahir, jk, agama, tinggibadan, beratbadan, hp, kecamatan, alamat, keterampilan, pendidikan, jurusan, institusi, lamastudi, tahunlulus, nilai);
                } else {
                    Toast.makeText(getContext().getApplicationContext(), "Silahkan masukkan data diri anda!", Toast.LENGTH_LONG).show();
                }
            }
        });





        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event




    private void registerUser(final String nik, final String nama, final String gelardepan, final String gelarbelakang, final String tempatlahir, final String tanggallahir, final String jk, final String agama, final String tinggibadan,  final String beratbadan,  final String hp, final String kecamatan, final String alamat, final String keterampilan,  final String pendidikan,  final String jurusan,  final String institusi,  final String lamastudi,  final String tahunlulus,  final String nilai){
        String tag_string_req = "req_register";
        pDialog.setMessage("Mendaftarkan ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_REGISTER_KARTU, new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {

                        Toast.makeText(getContext().getApplicationContext(), "Data Anda Telah Berhasil di Simpan! Silahkan Datang Langsung Untuk Mencetak Kartu", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(
                                getContext().getApplicationContext(),
                                MainActivity.class);
                        startActivity(intent);
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getContext().getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Eror Saat Menyimpan Data: " + error.getMessage());
                Toast.makeText(getContext().getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("nik", nik);
                params.put("nama", nama);
                params.put("gelardepan", gelardepan);
                params.put("gelarbelakang", gelarbelakang);
                params.put("tempatlahir", tempatlahir);
                params.put("tanggallahir", tanggallahir);
                params.put("jk", jk);
                params.put("agama", agama);
                params.put("tinggibadan", tinggibadan);
                params.put("beratbadan", beratbadan);
                params.put("hp", hp);
                params.put("kecamatan", kecamatan);
                params.put("alamat", alamat);
                params.put("keterampilan", keterampilan);
                params.put("pendidikanterakhir", pendidikan);
                params.put("jurusan", jurusan);
                params.put("institusi", institusi);
                params.put("lamastudi", lamastudi);
                params.put("tahunlulus", tahunlulus);
                params.put("nilai", nilai);

                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog(){
        if(!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog(){
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

//    public void onRadioButtonClicked(View v) {
//
//        EditText hasiljk = (EditText) v.findViewById(R.id.jk);
//        RadioGroup groupjk = (RadioGroup) v.findViewById(R.id.groupjk);
//
//        int idPil = groupjk.getCheckedRadioButtonId();
//
//        String s;
//        if (idPil==R.id.pria){
//            s = "pria";
//        } else {
//            s = "wanita";
//        }
//
//        hasiljk.setText(s);
//
//    }

    public void onClick(View view) {




        int idPil = groupJK.getCheckedRadioButtonId();

        String s;
        if (idPil==R.id.pria){
            s = "pria";
        } else {
            s = "wanita";
        }

        inputJk.setText(s);
    }
}
