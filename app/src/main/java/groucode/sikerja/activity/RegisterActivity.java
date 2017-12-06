package groucode.sikerja.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
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
import groucode.sikerja.app.AppConfig;
import groucode.sikerja.app.AppController;
import groucode.sikerja.helper.SQLiteHandler;
import groucode.sikerja.helper.SessionManager;

/**
 * Created by User on 17/11/2017.
 */

public class RegisterActivity extends Activity {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button btnRegister;
    private Button btnLinkLogin;
    private Spinner inputKecamatan;
    private EditText inputNik;
    private EditText inputNama;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    CheckBox seepassword;

    public void onCreate(Bundle sevedInstanceState) {
        super.onCreate(sevedInstanceState);
        setContentView(R.layout.activity_register);

        inputNik = (EditText) findViewById(R.id.nik);
        inputNama = (EditText) findViewById(R.id.nama);
        inputKecamatan = (Spinner) findViewById(R.id.kecamatan);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLinkLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);
        btnRegister = (Button) findViewById(R.id.register);
        seepassword =(CheckBox) findViewById(R.id.Seepassword);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        session = new SessionManager(getApplicationContext());

        db = new SQLiteHandler(getApplicationContext());

        seepassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // checkbox status is changed from uncheck to checked.
                if (!isChecked) {
                    // show password
                    inputPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    inputPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(RegisterActivity.this,
                    MainActivity.class);
            startActivity(intent);
            finish();
        }

        btnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String nik = inputNik.getText().toString();
                String nama = inputNama.getText().toString();
                String kecamatan = inputKecamatan.getSelectedItem().toString();
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();

                if (!nik.isEmpty() && !nama.isEmpty() && !kecamatan.equals("Pilih Kecamatan") && !email.isEmpty() &&  !password.isEmpty()) {
                    registerUser(nik, nama, kecamatan, email, password);
                    } else {
                    Toast.makeText(getApplicationContext(), "Silahkan masukkan data diri anda!", Toast.LENGTH_LONG).show();
                    }
            }
        });

        btnLinkLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }


        });




    }

    private void registerUser(final String nik, final String nama, final String kecamatan, final String email, final String password){
        String tag_string_req = "req_register";
        pDialog.setMessage("Mendaftarkan ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_REGISTER, new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {

                        Toast.makeText(getApplicationContext(), "Anda Berhasil Didaftarkan. Silahkan Login Sekarang!", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(
                                RegisterActivity.this,
                                LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Eror Saat Mendaftarkan: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
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
                params.put("kecamatan", kecamatan);
                params.put("email", email);
                params.put("password", password);

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

}
