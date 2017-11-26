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

public class LoginActivity extends Activity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    Button loginbtn, registerbtn;
    private EditText inputUsername;
    private EditText inputPassword;
    private Spinner spinnerLevel;
    private ProgressDialog pDialog;
    private SQLiteHandler db;
    private SessionManager session;
    private CheckBox seepassword;

    public void onCreate(Bundle sevedInstanceState) {
        super.onCreate(sevedInstanceState);
        setContentView(R.layout.activity_login);

        loginbtn = (Button)findViewById(R.id.Login);
        registerbtn = (Button)findViewById(R.id.btnLinkToRegisterScreen);

        inputUsername = (EditText) findViewById(R.id.nik);
        inputPassword =  (EditText) findViewById(R.id.password);
        seepassword = (CheckBox) findViewById(R.id.Seepassword);

        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

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
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nik = inputUsername.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (!nik.isEmpty() && !password.isEmpty()) {
                    // login user

//                    if (nip.equals("admin") && password.equals("12345")){
//                        Toast.makeText(LoginActivity.this, "Anda Berhasil Login!", Toast.LENGTH_LONG).show();
//                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                        startActivity(intent);
//                        finish();
//                    } else {
//                        Toast.makeText(LoginActivity.this, "Username dan Password Salah!", Toast.LENGTH_LONG).show();
//                    }
                    checkLogin(nik, password);

                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Masukkan username dan Password anda!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });







    }

    private void checkLogin(final String nik, final String password){
        String tag_string_req = "req_login";
        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error)  {
                        session.setLogin(true);
                        Toast.makeText(getApplicationContext(), "Anda Berhasil Login!", Toast.LENGTH_LONG).show();

                        String nik = jObj.getString("nik");
                        JSONObject user = jObj.getJSONObject("user");
                        String nama = user.getString("nama");
                        String email = user.getString("email");
                        String token = user.getString("token");

                        db.addUser(nik, nama, email, token);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Masalah Pada Jaringan: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener(){

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }

        }){
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("nik", nik);
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
