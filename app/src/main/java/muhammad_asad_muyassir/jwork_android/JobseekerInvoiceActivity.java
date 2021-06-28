package muhammad_asad_muyassir.jwork_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Class JobseekerInvoiceActivity adalah activity pada saat melihat seluruh invoice
 *
 * @author Muhammad As'ad Muyassir
 * @version 27-06-2021
 */
public class JobseekerInvoiceActivity extends AppCompatActivity {
    private ArrayList<Invoice> listInvoice = new ArrayList<>();
    int jobseekerID;

    protected void fetchJob() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonObject = new JSONArray(response);
                    if(jsonObject.length() > 0)
                    {
                        for(int i = 0; i < jsonObject.length(); i++)
                        {
                            JSONObject invoice = jsonObject.getJSONObject(i);
                            String jobseekerName = invoice.getJSONObject("jobseeker").getString("name");

                            JSONArray jobs = invoice.getJSONArray("jobs");
                            ArrayList<String> jobsName = new ArrayList<>();
                            ArrayList<Integer> jobsFee = new ArrayList<>();
                            for(int j = 0; j < jobs.length(); j++)
                            {
                                String jobName = jobs.getJSONObject(j).getString("name");
                                int jobFee = jobs.getJSONObject(j).getInt("fee");
                                jobsName.add(jobName);
                                jobsFee.add(jobFee);
                            }

                            Invoice invoice1 = new Invoice(
                                    invoice.getInt("id"),
                                    jobsName,
                                    jobsFee,
                                    invoice.getString("date"),
                                    invoice.getInt("totalFee"),
                                    jobseekerName,
                                    invoice.getString("paymentType"),
                                    invoice.getString("invoiceStatus")
                            );

                            if (invoice.getString("paymentType").equals("EwalletPayment")) {
                                if (!invoice.isNull("bonus")) {
                                    invoice1.setReferralCode(invoice.getJSONObject("bonus").getString("referralCode"));
                                }
                            }
                            listInvoice.add(invoice1);
                        }

                        RecyclerView rvInvoice = findViewById(R.id.rvInvoice);
                        rvInvoice.setHasFixedSize(true);

                        rvInvoice.setLayoutManager(new LinearLayoutManager(JobseekerInvoiceActivity.this));
                        JobseekerInvoiceAdapter listInvoiceAdapter = new JobseekerInvoiceAdapter(JobseekerInvoiceActivity.this, listInvoice);
                        rvInvoice.setAdapter(listInvoiceAdapter);

                        ProgressBar pbInvoice = findViewById(R.id.pbInvoice);
                        pbInvoice.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(JobseekerInvoiceActivity.this, "Data invoice is NULL", Toast.LENGTH_LONG).show();
                        finish();
                    }
                } catch(JSONException e) {
                    Toast.makeText(JobseekerInvoiceActivity.this, "Data invoice is NULL", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        };

        JobFetchRequest jobRequest = new JobFetchRequest(jobseekerID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(JobseekerInvoiceActivity.this);
        queue.add(jobRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobseeker_invoice);
        jobseekerID = getIntent().getIntExtra("extraJobseekerId", 0);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fetchJob();
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}