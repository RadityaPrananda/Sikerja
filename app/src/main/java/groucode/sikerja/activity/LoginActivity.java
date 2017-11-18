package groucode.sikerja.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import groucode.sikerja.R;

/**
 * Created by User on 17/11/2017.
 */

public class LoginActivity extends Activity {

    Button loginbtn, registerbtn;

    public void onCreate(Bundle sevedInstanceState) {
        super.onCreate(sevedInstanceState);
        setContentView(R.layout.activity_login);

        loginbtn = (Button)findViewById(R.id.Login);
        registerbtn = (Button)findViewById(R.id.btnLinkToRegisterScreen);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
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

}
