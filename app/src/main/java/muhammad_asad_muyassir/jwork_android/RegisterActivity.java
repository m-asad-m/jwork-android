package muhammad_asad_muyassir.jwork_android;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import static java.lang.Integer.parseInt;

/**
 * Class LoginActivity adalah activity pada saat
 * ingin melakukan pendaftaran
 *
 * @author Muhammad As'ad Muyassir
 * @version 27-06-2021
 */
public class RegisterActivity extends AppCompatActivity
{
    private byte[] byteStream = null;
    private String contentType;
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ImageView ivPhotoProfile = findViewById(R.id.ivPhotoProfile);

        final EditText etName = findViewById(R.id.etName);
        final EditText etEmail = findViewById(R.id.etEmail);
        final EditText etPassword = findViewById(R.id.etPassword);
        Button btnRegister = findViewById(R.id.btnRegister);

        TextInputLayout containerName = findViewById(R.id.containerName);
        TextInputLayout containerEmail = findViewById(R.id.containerEmail);
        TextInputLayout containerPassword = findViewById(R.id.containerPassword);

        ActivityResultLauncher<Intent> galleryResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        try {
                            assert data != null;
                            InputStream inputStream = getContentResolver().openInputStream(data.getData());
                            String contentType = getContentResolver().getType(data.getData());
                            byte[] byteStream = new byte[inputStream.available()];
                            int bytesRead = inputStream.read(byteStream);
                            while(bytesRead != -1) {
                                bytesRead = inputStream.read(byteStream);
                            }
                            inputStream.close();
                            ivPhotoProfile.setImageURI(data.getData());
                            this.byteStream = byteStream;
                            this.contentType = contentType;
                        } catch (FileNotFoundException e) {
                            System.out.println(e.getMessage());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

        ivPhotoProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                galleryResultLauncher.launch(intent);
            }
        });

        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().isEmpty()) {
                    containerName.setError("Name can't be empty");
                    containerName.setErrorEnabled(true);
                } else {
                    containerName.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().matches("^[\\w&*~](\\.?[\\w&*~]+)*@([a-zA-Z0-9][\\w&*~-]+)(\\.?[a-zA-Z0-9]+)*$")) {
                    containerEmail.setError("Please enter a valid email address");
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
                if(!s.toString().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{6,}$")) {
                    containerPassword.setError("Password must be\n- at least 6 characters\n- including number\n- including lower and upper case character");
                    containerPassword.setErrorEnabled(true);
                } else {
                    containerPassword.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if(name.isEmpty() && email.isEmpty() && password.isEmpty()) {
                    containerName.setError("Name can't be empty");
                    containerName.setErrorEnabled(true);
                    containerEmail.setError("Email can't be empty");
                    containerEmail.setErrorEnabled(true);
                    containerPassword.setError("Password can't be empty");
                    containerPassword.setErrorEnabled(true);
                }

                if(containerName.getError() == null && containerEmail.getError() == null && containerPassword.getError() == null) {
                    Response.Listener<String> responseListener = new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            if(!response.equals("0")) {
                                id = parseInt(response);
                                if(byteStream != null) {
                                    uploadImage();
                                } else {
                                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(RegisterActivity.this);
                                    LayoutInflater inflater = getLayoutInflater();

                                    dialogBuilder.setCancelable(false);
                                    dialogBuilder.setView(inflater.inflate(R.layout.modal, null))
                                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    finish();
                                                }
                                            });

                                    AlertDialog alert = dialogBuilder.create();
                                    alert.getWindow().setBackgroundDrawableResource(R.drawable.rounded_bg);
                                    alert.show();

                                    TextView tvModalText = alert.findViewById(R.id.modal_text);
                                    ImageView ivModalImg = alert.findViewById(R.id.modal_img);

                                    tvModalText.setText(R.string.registerSuccess);
                                    ivModalImg.setImageResource(R.drawable.accept_pic);
                                }
                            } else {
                                Toast.makeText(RegisterActivity.this, "Register Failed", Toast.LENGTH_LONG).show();
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
                                    if(jsonObject.getString("message").matches("(?s).*Email.*already exists.*")) {
                                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(RegisterActivity.this);
                                        LayoutInflater inflater = getLayoutInflater();

                                        dialogBuilder.setCancelable(false);
                                        dialogBuilder.setView(inflater.inflate(R.layout.modal, null))
                                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        finish();
                                                    }
                                                })
                                                .setNegativeButton("try again", null);

                                        AlertDialog alert = dialogBuilder.create();
                                        alert.getWindow().setBackgroundDrawableResource(R.drawable.rounded_bg);
                                        alert.show();

                                        TextView tvModalText = alert.findViewById(R.id.modal_text);
                                        ImageView ivModalImg = alert.findViewById(R.id.modal_img);

                                        tvModalText.setText(R.string.emailAlreadyExists);
                                        ivModalImg.setImageResource(R.drawable.reject_pic);
                                    } else {
                                        Toast.makeText(RegisterActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Error code: "+ error.networkResponse.statusCode, Toast.LENGTH_LONG).show();
                                }
                            } catch (UnsupportedEncodingException | JSONException e) {
                                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    };

                    RegisterRequest registerRequest = new RegisterRequest(name, email, password, responseListener, errorListener);
                    RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                    queue.add(registerRequest);
                } else {
                    Toast.makeText(RegisterActivity.this, "Please complete the requirements", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void uploadImage() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("true")) {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(RegisterActivity.this);
                    LayoutInflater inflater = getLayoutInflater();

                    dialogBuilder.setCancelable(false);
                    dialogBuilder.setView(inflater.inflate(R.layout.modal, null))
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    finish();
                                }
                            });

                    AlertDialog alert = dialogBuilder.create();
                    alert.getWindow().setBackgroundDrawableResource(R.drawable.rounded_bg);
                    alert.show();

                    TextView tvModalText = alert.findViewById(R.id.modal_text);
                    ImageView ivModalImg = alert.findViewById(R.id.modal_img);

                    tvModalText.setText(R.string.registerSuccess);
                    ivModalImg.setImageResource(R.drawable.accept_pic);
                } else {
                    Toast.makeText(RegisterActivity.this, "Upload image failed", Toast.LENGTH_LONG).show();
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
                    System.out.println(jsonObject.getString("message"));
                    Toast.makeText(RegisterActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                } catch (UnsupportedEncodingException | JSONException e) {
                    System.out.println(e.getMessage());
                    Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        };

        UploadImageRequest uploadImageRequest = new UploadImageRequest("profile", id+".jpg", byteStream, contentType, responseListener, errorListener);
        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
        queue.add(uploadImageRequest);
    }
}