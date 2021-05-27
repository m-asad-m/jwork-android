package MuhammadAsadMuyassir.jwork_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Recruiter> listRecruiter = new ArrayList<>();
    private ArrayList<Job> jobIdList = new ArrayList<>();
    private HashMap<Recruiter, ArrayList<Job>> childMapping = new HashMap<>();

    protected void refreshList() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    if(jsonResponse != null)
                    {
                        for(int i = 0; i < jsonResponse.length(); i++)
                        {
                            JSONObject job = jsonResponse.getJSONObject(i);
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

                            listRecruiter.add(recruiter1);
                            jobIdList.add(job1);
                        }

                        for(Recruiter rec: listRecruiter)
                        {
                            ArrayList<Job> temp = new ArrayList<>();
                            for(Job job: jobIdList)
                            {
                                if(job.getRecruiter().getEmail().equals(rec.getName()) || job.getRecruiter().getEmail().equals(rec.getEmail()) || job.getRecruiter().getPhoneNumber().equals(rec.getPhoneNumber()))
                                {
                                    temp.add(job);
                                }
                            }
                            childMapping.put(rec, temp);
                        }
                    }
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
        refreshList();

        Toast.makeText(this, "UDAH REFRESH", Toast.LENGTH_LONG).show();

        ExpandableListView listView = findViewById(R.id.lvExp);
        MainListAdapter listAdapter = new MainListAdapter(this, listRecruiter, childMapping);
        listView.setAdapter(listAdapter);
    }
}