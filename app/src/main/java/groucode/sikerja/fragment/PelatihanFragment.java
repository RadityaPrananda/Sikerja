package groucode.sikerja.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import groucode.sikerja.CardAdapterPelatihan;
import groucode.sikerja.R;
import groucode.sikerja.app.AppConfig;
import groucode.sikerja.helper.RequestHandler;
import groucode.sikerja.helper.SQLiteHandler;

/**
 * Created by ASUS on 06/12/2017.
 */

public class PelatihanFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SQLiteHandler db;

    private AppConfig appConfig;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

//    private PelatihanFragment.OnFragmentInteractionListener mListener;

    public PelatihanFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PelatihanFragment newInstance(String param1, String param2) {
        PelatihanFragment fragment = new PelatihanFragment();
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
        View view = inflater.inflate(R.layout.fragment_pelatihan, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext().getApplicationContext());

        recyclerView.setLayoutManager(layoutManager);

        db = new SQLiteHandler(getActivity().getApplicationContext());

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);

        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        get();
                                        swipeRefreshLayout.setRefreshing(false);
                                    }
                                }
        );

        get();

        return view;
    }

    public void onRefresh() {
        get();
    }

    private void get(){
        swipeRefreshLayout.setRefreshing(true);
//        HashMap<String, String> user = db.getUserDetails();
//        final String nikUser = user.get("nik");
        class Get extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getContext(),"Memperbaharui Data!","Tunggu...",false,false);

                loading.setCancelable(true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                parseJSON(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(AppConfig.URL_GET_PELATIHAN);
                return s;
            }
        }
        Get ge = new Get();
        ge.execute();
        swipeRefreshLayout.setRefreshing(false);
    }


    private void parseJSON(String json){
        try{

            JSONObject jsonObject = new JSONObject(json);
            JSONArray array = jsonObject.getJSONArray("result");

            appConfig = new AppConfig(array.length());

            for (int i=0; i<array.length(); i++){
                JSONObject j = array.getJSONObject(i);
                AppConfig.id[i] = getId(j);
                AppConfig.namapelatihan[i] = getNamapelatihan(j);
                AppConfig.penyelenggara[i] = getPenyelenggara(j);
                AppConfig.waktu[i] = getWaktu(j);
                AppConfig.tempat[i] = getTempat(j);
                AppConfig.kuota[i] = getKuota(j);
            }

            Log.d("Two Fragment", String.valueOf(array.length()));

            adapter = new CardAdapterPelatihan(getContext().getApplicationContext(), AppConfig.id, AppConfig.namapelatihan, AppConfig.penyelenggara, AppConfig.waktu, AppConfig.tempat, AppConfig.kuota);
            recyclerView.setAdapter(adapter);



        }catch (JSONException e){
            e.printStackTrace();
        }

    }

    private String getId(JSONObject j){
        String id = null;
        try {
            id = j.getString(AppConfig.TAG_ID);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return id;
    }

    private String getNamapelatihan(JSONObject j){
        String namapelatihan = null;
        try {
            namapelatihan = j.getString(AppConfig.TAG_NAMAPELATIHAN);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return namapelatihan;
    }

    private String getPenyelenggara(JSONObject j){
        String penyelenggara = null;
        try {
            penyelenggara = j.getString(AppConfig.TAG_PENYELENGGARA);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return penyelenggara;
    }

    private String getWaktu(JSONObject j){
        String waktu = null;
        try{
            waktu = j.getString(AppConfig.TAG_WAKTU);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return waktu;
    }

    private String getTempat(JSONObject j){
        String tempat = null;
        try{
            tempat = j.getString(AppConfig.TAG_TEMPAT);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return tempat;
    }

    private String getKuota(JSONObject j){
        String kuota = null;
        try{
            kuota = j.getString(AppConfig.TAG_KUOTA);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return kuota;
    }

}
