package com.example.pancerpuger;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class registerk extends AppCompatActivity {

    EditText nama, username ,password, email, number;
    Button login,daftar;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerk);

        nama = (EditText) findViewById(R.id.namak);
        username = (EditText) findViewById(R.id.usernamek);
        password = (EditText) findViewById(R.id.passwordk);
        email = (EditText) findViewById(R.id.emailk);
        number = (EditText) findViewById(R.id.numberk);
        login = (Button) findViewById(R.id.btnloginr);
        daftar = (Button) findViewById(R.id.btndaftarr);
        progressDialog = new ProgressDialog(registerk.this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(registerk.this, Login.class);
                startActivity(loginIntent);
            }
        });

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sNama = nama.getText().toString();
                String sUsername = username.getText().toString();
                String sPassword = password.getText().toString();
                String sEmail = email.getText().toString();
                String sNumber = number.getText().toString();

                if (sPassword.equals(sPassword) && !sPassword.equals("")) {
                    CreateDataToServer(sNama, sUsername, sEmail, sPassword, sNumber);
                    Intent loginIntent = new Intent(registerk.this, Login.class);
                    startActivity(loginIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "Cek kembali", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void CreateDataToServer(final String nama, final String username, final String email, final String password,final String number) {
        if (checkNetworkConnection()) {
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.SERVER_REGISTER_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String resp = jsonObject.getString("server_response");
                                if (response.equals("[{\"status\":\"OK\"}]")) {
                                    Toast.makeText(getApplicationContext(), "Registrasi Berhasil", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), resp, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("nama", String.valueOf(nama));
                    params.put("username", username);
                    params.put("password", password);
                    params.put("email", email);
                    params.put("number", String.valueOf(number));
                    return params;
                }
            };

            volleyconnection.getInstance(registerk.this).addToRequestQue(stringRequest);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.cancel();
                }
            },2000);
        } else {
            Toast.makeText(getApplicationContext(), "Tidak ada koneksi internet", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}