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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import groucode.sikerja.CardAdapterLowongan;
import groucode.sikerja.R;
import groucode.sikerja.app.AppConfig;
import groucode.sikerja.helper.RequestHandler;
import groucode.sikerja.helper.SQLiteHandler;

public class LowonganFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

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

//    private OnFragmentInteractionListener mListener;

    public LowonganFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     */
    // TODO: Rename and change types and number of parameters
    public static LowonganFragment newInstance(String param1, String param2) {
        LowonganFragment fragment = new LowonganFragment();
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

        View view = inflater.inflate(R.layout.fragment_lowongan, container, false);

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

//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == android.R.id.home) {
//            // finish the activity
//            onBackPressed();
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
////        if (context instanceof OnFragmentInteractionListener) {
////            mListener = (OnFragmentInteractionListener) context;
////        } else {
////            throw new RuntimeException(context.toString()
////                    + " must implement OnFragmentInteractionListener");
////        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }

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
                String s = rh.sendGetRequest(AppConfig.URL_GET_LOWONGAN);
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
                AppConfig.bataswaktu[i] = getBataswaktu(j);
                AppConfig.logoperusahaan[i] = getLogoperusahaan(j);
                AppConfig.namaperusahaan[i] = getNamaperusahaan(j);
                AppConfig.jabatan[i] = getJabatan(j);
                AppConfig.lokasi[i] = getLokasi(j);
            }

            Log.d("Two Fragment", String.valueOf(array.length()));

            adapter = new CardAdapterLowongan(getContext().getApplicationContext(), AppConfig.id, AppConfig.bataswaktu, AppConfig.logoperusahaan, AppConfig.namaperusahaan, AppConfig.jabatan, AppConfig.lokasi);
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

    private String getBataswaktu(JSONObject j){
        String bataswaktu = null;
        try {
            bataswaktu = j.getString(AppConfig.TAG_BATASWAKTU);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return bataswaktu;
    }

    private String getLogoperusahaan(JSONObject j){
        String logoperusahaan = null;
        try {
            logoperusahaan = j.getString(AppConfig.TAG_LOGOPERUSAHAAN);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return logoperusahaan;
    }

    private String getNamaperusahaan(JSONObject j){
        String namaperusahaan = null;
        try{
            namaperusahaan = j.getString(AppConfig.TAG_NAMAPERUSAHAAN);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return namaperusahaan;
    }

    private String getJabatan(JSONObject j){
        String jabatan = null;
        try{
            jabatan = j.getString(AppConfig.TAG_JABATAN);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return jabatan;
    }

    private String getLokasi(JSONObject j){
        String lokasi = null;
        try{
            lokasi = j.getString(AppConfig.TAG_LOKASI);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return lokasi;
    }

}
