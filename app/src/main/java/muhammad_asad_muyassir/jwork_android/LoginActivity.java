package muhammad_asad_muyassir.jwork_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Class LoginActivity adalah activity pada saat
 * ingin melakukan login
 *
 * @author Muhammad As'ad Muyassir
 * @version 27-06-2021
 */
public class LoginActivity extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPrefManager = new SharedPrefManager(this);

        final EditText etEmail = findViewById(R.id.etEmail);
        final EditText etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView tvRegister = findViewById(R.id.tvRegister);
        TextInputLayout containerEmail = findViewById(R.id.containerEmail);
        TextInputLayout containerPassword = findViewById(R.id.containerPassword);

        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().isEmpty()) {
                    containerEmail.setError("Email can't be empty");
                    containerEmail.setErrorEnabled(true);
                } else {
                    containerEmail.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().isEmpty()) {
                    containerPassword.setError("Password can't be empty");
                    containerPassword.setErrorEnabled(true);
                } else {
                    containerPassword.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()) {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int id = jsonObject.getInt("id");
                                sharedPrefManager.setSPInt(SharedPrefManager.SP_ID, id);
                                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("extraJobseekerId", id);
                                startActivity(intent);
                                finish();
                            } catch (JSONException e) {
                                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    };

                    Response.ErrorListener errorListener = new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            try {
                                String json = new String(error.networkResponse.data, HttpHeaderParser.parseCharset(error.networkResponse.headers));
                                JSONObject jsonObject = new JSONObject(json);
                                if(!jsonObject.getString("message").isEmpty()) {
                                    if(jsonObject.getString("message").matches("(?s).*Jobseeker.*not found.*")) {
                                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LoginActivity.this);
                                        LayoutInflater inflater = getLayoutInflater();

                                        dialogBuilder.setCancelable(false);
                                        dialogBuilder.setView(inflater.inflate(R.layout.modal, null))
                                                .setPositiveButton("ok", null);

                                        AlertDialog alert = dialogBuilder.create();
                                        alert.getWindow().setBackgroundDrawableResource(R.drawable.rounded_bg);
                                        alert.show();

                                        TextView tvModalText = alert.findViewById(R.id.modal_text);
                                        ImageView ivModalImg = alert.findViewById(R.id.modal_img);

                                        tvModalText.setText(R.string.wrongEmailPassword);
                                        ivModalImg.setImageResource(R.drawable.reject_pic);
                                    } else {
                                        Toast.makeText(LoginActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(LoginActivity.this, "Error code: "+ error.networkResponse.statusCode, Toast.LENGTH_LONG).show();
                                }
                            } catch (UnsupportedEncodingException | JSONException e) {
                                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    };

                    LoginRequest loginRequest = new LoginRequest(email, password, responseListener, errorListener);
                    RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                    queue.add(loginRequest);
                } else {
                    if (email.isEmpty()) {
                        containerEmail.setError("Email can't be empty");
                        containerEmail.setErrorEnabled(true);
                    }

                    if (password.isEmpty()) {
                        containerPassword.setError("Password can't be empty");
                        containerPassword.setErrorEnabled(true);
                    }
                }
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}