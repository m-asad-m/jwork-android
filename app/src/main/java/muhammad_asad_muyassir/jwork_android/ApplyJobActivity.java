package muhammad_asad_muyassir.jwork_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
 * Class ApplyJobActivity adalah activity pada saat ingin melakukan apply job
 *
 * @author Muhammad As'ad Muyassir
 * @version 27-06-2021
 */
public class ApplyJobActivity extends AppCompatActivity {
    private int jobseekerID;
    private int jobID;
    private String jobName;
    private String jobCategory;
    private int jobFee;
    private int bonus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_job);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        jobseekerID = getIntent().getIntExtra("extraJobseekerId", 0);
        jobID = getIntent().getIntExtra("extraJobId", 0);
        jobName = getIntent().getStringExtra("extraJobName");
        jobCategory = getIntent().getStringExtra("extraJobCategory");
        jobFee = getIntent().getIntExtra("extraJobFee", 0);

        TextView tvJobName = findViewById(R.id.job_name);
        TextView tvJobCategory = findViewById(R.id.job_category);
        TextView tvJobFee = findViewById(R.id.job_fee);
        RadioGroup rbGroup = findViewById(R.id.radioGroup);
        final RadioButton rbEWallet = findViewById(R.id.ewallet);
        final RadioButton rbBank = findViewById(R.id.bank);
        final EditText etReferralCode = findViewById(R.id.referral_code);
        final TextView tvTotalFee = findViewById(R.id.total_fee);
        final Button btnCount = findViewById(R.id.btnCount);
        final Button btnApply = findViewById(R.id.btnApply);
        final LinearLayout containerReferralCode = findViewById(R.id.containerReferralCode);

        containerReferralCode.setVisibility(View.GONE);
        btnApply.setEnabled(false);
        tvJobName.setText(jobName);
        tvJobCategory.setText(jobCategory);
        tvJobFee.setText(String.valueOf(jobFee));
        tvTotalFee.setText(String.valueOf(0));

        rbGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (rbEWallet.isChecked()) {
                    containerReferralCode.setVisibility(View.VISIBLE);
                } else if(rbBank.isChecked()) {
                    containerReferralCode.setVisibility(View.GONE);
                }
            }
        });

        btnCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rbEWallet.isChecked()) {
                    if (etReferralCode.getText().length() != 0) {
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject.getBoolean("active") && jsonObject.getInt("minTotalFee") < jobFee) {
                                        bonus = jsonObject.getInt("extraFee");
                                        tvTotalFee.setText(String.valueOf(jobFee + bonus));
                                        Toast.makeText(ApplyJobActivity.this, "Referral Code Found", Toast.LENGTH_LONG).show();
                                    } else {
                                        tvTotalFee.setText(String.valueOf(jobFee));
                                        Toast.makeText(ApplyJobActivity.this, "Your Referral Code is inactive or your fee is lower than the minimum total fee", Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    tvTotalFee.setText(String.valueOf(jobFee));
                                    Toast.makeText(ApplyJobActivity.this, "Referral Code not Found", Toast.LENGTH_LONG).show();
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
                                        tvTotalFee.setText(String.valueOf(jobFee));
                                        Toast.makeText(ApplyJobActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                    }
                                } catch (UnsupportedEncodingException | JSONException e) {
                                    Toast.makeText(ApplyJobActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        };

                        BonusRequest bonusRequest = new BonusRequest(etReferralCode.getText().toString(), responseListener, errorListener);
                        RequestQueue queue = Volley.newRequestQueue(ApplyJobActivity.this);
                        queue.add(bonusRequest);
                    } else {
                        tvTotalFee.setText(String.valueOf(jobFee));
                    }
                } else if (rbBank.isChecked()) {
                    tvTotalFee.setText(String.valueOf(jobFee));
                } else {
                    Toast.makeText(ApplyJobActivity.this, "Please select payment method", Toast.LENGTH_LONG).show();
                    return;
                }

                rbEWallet.setEnabled(false);
                rbBank.setEnabled(false);
                etReferralCode.setEnabled(false);
                btnCount.setEnabled(false);
                btnApply.setEnabled(true);
            }
        });

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> responseListener = new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("true")) {
                            Toast.makeText(ApplyJobActivity.this, "Job successfully applied", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(ApplyJobActivity.this, "Job failed to apply", Toast.LENGTH_LONG).show();
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
                                if(jsonObject.getString("message").matches("(?s).*Ongoing Invoice.*already exists.*")) {
                                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ApplyJobActivity.this);
                                    LayoutInflater inflater = getLayoutInflater();

                                    dialogBuilder.setCancelable(false);
                                    dialogBuilder.setView(inflater.inflate(R.layout.modal, null))
                                            .setPositiveButton("ok", null);

                                    AlertDialog alert = dialogBuilder.create();
                                    alert.getWindow().setBackgroundDrawableResource(R.drawable.rounded_bg);
                                    alert.show();

                                    TextView tvModalText = alert.findViewById(R.id.modal_text);
                                    ImageView ivModalImg = alert.findViewById(R.id.modal_img);

                                    tvModalText.setText(R.string.ongoingInvoiceAlreadyExists);
                                    ivModalImg.setImageResource(R.drawable.reject_pic);
                                } else {
                                    Toast.makeText(ApplyJobActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(ApplyJobActivity.this, "Error code: "+ error.networkResponse.statusCode, Toast.LENGTH_LONG).show();
                            }
                        } catch (UnsupportedEncodingException | JSONException e) {
                            Toast.makeText(ApplyJobActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                };

                if (rbEWallet.isChecked())
                {
                    ApplyJobRequest applyJobRequest = new ApplyJobRequest(String.valueOf(jobID), String.valueOf(jobseekerID), etReferralCode.getText().toString(), responseListener, errorListener);
                    RequestQueue queue = Volley.newRequestQueue(ApplyJobActivity.this);
                    queue.add(applyJobRequest);
                }
                else if(rbBank.isChecked())
                {
                    ApplyJobRequest applyJobRequest = new ApplyJobRequest(String.valueOf(jobID), String.valueOf(jobseekerID), responseListener, errorListener);
                    RequestQueue queue = Volley.newRequestQueue(ApplyJobActivity.this);
                    queue.add(applyJobRequest);
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}