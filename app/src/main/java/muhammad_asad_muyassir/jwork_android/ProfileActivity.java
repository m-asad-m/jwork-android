package muhammad_asad_muyassir.jwork_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Class ProfileActivity adalah activity yang menampilkan
 * profile pencari pekerjaan
 *
 * @author Muhammad As'ad Muyassir
 * @version 27-06-2021
 */
public class ProfileActivity extends AppCompatActivity {
    private int jobseekerID;

    protected void fetchJobseeker() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if(!response.isEmpty())
                    {
                        JSONObject jsonObject = new JSONObject(response);
                        ImageView imProfile = findViewById(R.id.imProfilePicture);
                        TextView tvName = findViewById(R.id.tvName);
                        TextView tvEmail = findViewById(R.id.tvEmail);
                        TextView tvJoinDate = findViewById(R.id.tvJoinDate);

                        ShowImage showImage = new ShowImage(imProfile, "profile", jobseekerID + ".jpg", R.drawable.dummy_avatar);
                        showImage.show();
                        tvName.setText(jsonObject.getString("name"));
                        tvEmail.setText(jsonObject.getString("email"));
                        tvJoinDate.setText(String.format("Join us from %s", jsonObject.getString("joinDate").substring(0, 10)));
                    } else {
                        Toast.makeText(ProfileActivity.this, "Can't find your data", Toast.LENGTH_LONG).show();
                        finish();
                    }
                } catch(JSONException e) {
                    System.out.println(e.getMessage());
                    Toast.makeText(ProfileActivity.this, "Can't find your data", Toast.LENGTH_LONG).show();
                    finish();
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
                        Toast.makeText(ProfileActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                        finish();
                    }
                } catch (UnsupportedEncodingException | JSONException e) {
                    Toast.makeText(ProfileActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        };

        ProfileRequest profileRequest = new ProfileRequest(jobseekerID, responseListener, errorListener);
        RequestQueue queue = Volley.newRequestQueue(ProfileActivity.this);
        queue.add(profileRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        jobseekerID = getIntent().getIntExtra("extraJobseekerId", 0);

        fetchJobseeker();
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}