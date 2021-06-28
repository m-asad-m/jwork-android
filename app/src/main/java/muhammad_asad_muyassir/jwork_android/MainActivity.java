package muhammad_asad_muyassir.jwork_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class MainActivity adalah activity yang menampilkan
 * list perekrut dengan jobnya
 *
 * @author Muhammad As'ad Muyassir
 * @version 27-06-2021
 */
public class MainActivity extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;

    private ArrayList<Recruiter> listRecruiter = new ArrayList<>();
    private ArrayList<Job> jobIdList = new ArrayList<>();
    private HashMap<Recruiter, ArrayList<Job>> childMapping = new HashMap<>();
    private int jobseekerID;

    protected void refreshList() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonObject = new JSONArray(response);
                    if(jsonObject != null)
                    {
                        for(int i = 0; i < jsonObject.length(); i++)
                        {
                            JSONObject job = jsonObject.getJSONObject(i);
                            JSONObject recruiter = job.getJSONObject("recruiter");
                            JSONObject location = recruiter.getJSONObject("location");

                            Location location1 = new Location(
                                    location.getString("province"),
                                    location.getString("city"),
                                    location.getString("description")
                            );
                            Recruiter recruiter1 = new Recruiter(
                                    recruiter.getInt("id"),
                                    recruiter.getString("name"),
                                    recruiter.getString("email"),
                                    recruiter.getString("phoneNumber"),
                                    location1
                            );
                            Job job1 = new Job(
                                    job.getInt("id"),
                                    job.getString("name"),
                                    recruiter1,
                                    job.getInt("fee"),
                                    job.getString("category")
                            );

                            if (listRecruiter.size() > 0)
                            {
                                boolean success = true;
                                for (Recruiter recruiter2 : listRecruiter)
                                {
                                    if (recruiter2.getId() == recruiter1.getId())
                                    {
                                        success = false;
                                        break;
                                    }
                                }
                                if (success)
                                {
                                    listRecruiter.add(recruiter1);
                                }
                            }
                            else
                            {
                                listRecruiter.add(recruiter1);
                            }

                            jobIdList.add(job1);
                        }

                        for(Recruiter rec: listRecruiter)
                        {
                            ArrayList<Job> temp = new ArrayList<>();
                            for(Job job: jobIdList)
                            {
                                if(job.getRecruiter().getId() == rec.getId())
                                {
                                    temp.add(job);
                                }
                            }
                            childMapping.put(rec, temp);
                        }
                    }

                    ExpandableListView listView = findViewById(R.id.elvMain);
                    MainListAdapter listAdapter = new MainListAdapter(MainActivity.this, listRecruiter, childMapping);
                    listView.setAdapter(listAdapter);
                    listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                        @Override
                        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                            Job selectedJob = childMapping.get(listRecruiter.get(groupPosition)).get(childPosition);
                            Intent intent = new Intent(MainActivity.this, ApplyJobActivity.class);
                            intent.putExtra("extraJobseekerId", jobseekerID);
                            intent.putExtra("extraJobId", selectedJob.getId());
                            intent.putExtra("extraJobName", selectedJob.getName());
                            intent.putExtra("extraJobCategory", selectedJob.getCategory());
                            intent.putExtra("extraJobFee", selectedJob.getFee());
                            startActivity(intent);
                            return true;
                        }
                    });
                } catch(JSONException e) {
                    Toast.makeText(MainActivity.this, "Data recruiter is NULL", Toast.LENGTH_LONG).show();
                }
            }
        };

        MenuRequest menuRequest = new MenuRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(menuRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.logout_button);

        jobseekerID = getIntent().getIntExtra("extraJobseekerId", 0);
        Button btnApplied = findViewById(R.id.btnApplied);
        refreshList();

        btnApplied.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, JobseekerInvoiceActivity.class);
                intent.putExtra("extraJobseekerId", jobseekerID);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_profile) {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            intent.putExtra("extraJobseekerId", jobseekerID);
            startActivity(intent);
        } else {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
            LayoutInflater inflater = getLayoutInflater();

            dialogBuilder.setCancelable(false);
            dialogBuilder.setView(inflater.inflate(R.layout.modal, null))
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            sharedPrefManager = new SharedPrefManager(MainActivity.this);
                            sharedPrefManager.setSPInt(SharedPrefManager.SP_ID, 0);
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setNegativeButton("cancel", null);

            AlertDialog alert = dialogBuilder.create();
            alert.getWindow().setBackgroundDrawableResource(R.drawable.rounded_bg);
            alert.show();

            TextView tvModalText = alert.findViewById(R.id.modal_text);
            ImageView ivModalImg = alert.findViewById(R.id.modal_img);

            tvModalText.setText(R.string.logoutQuestion);
            ivModalImg.setImageResource(R.drawable.shock_pic);
        }
        return true;
    }
}