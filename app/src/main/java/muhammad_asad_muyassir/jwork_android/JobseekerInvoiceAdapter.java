package muhammad_asad_muyassir.jwork_android;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Class JobseekerInvoiceAdapter adalah adapter yang
 * menampilkan seluruh invoice dari pencari pekerjaan
 *
 * @author Muhammad As'ad Muyassir
 * @version 27-06-2021
 */
public class JobseekerInvoiceAdapter extends RecyclerView.Adapter<JobseekerInvoiceAdapter.ListViewHolder> {
    Context context;
    ArrayList<Invoice> listInvoice;

    public JobseekerInvoiceAdapter(Context context, ArrayList<Invoice> listInvoice) {
        this.context = context;
        this.listInvoice = listInvoice;
    }

    private void changeStatus(View view, int id, String status) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("true")) {
                    Toast.makeText(context, "Job has been "+status, Toast.LENGTH_LONG).show();
                    ((Activity) view.getContext()).finish();
                } else {
                    Toast.makeText(context, "Job can't "+status, Toast.LENGTH_LONG).show();
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
                        Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (UnsupportedEncodingException | JSONException e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        };

        JobStatusRequest jobStatus = new JobStatusRequest(String.valueOf(listInvoice.get(0).getId()), status, responseListener, errorListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jobStatus);
    }

    @NonNull
    @Override
    public JobseekerInvoiceAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_invoice, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        Invoice invoice = listInvoice.get(position);

        holder.tvInvoiceID.setText(String.format("INVOICE ID: %d", invoice.getId()));
        holder.tvJobseekerName.setText(invoice.getJobseekerName());
        holder.tvInvoiceDate.setText(invoice.getDate());
        holder.tvPaymentType.setText(invoice.getPaymentType());
        holder.tvInvoiceStatus.setText(invoice.getInvoiceStatus());

        if (invoice.getPaymentType().equals("EwalletPayment") && !invoice.getReferralCode().equals("null")) {
            holder.tvReferralCode.setText(invoice.getReferralCode());
        } else {
            holder.containerReferralCode.setVisibility(View.GONE);
        }

        holder.rvJobs.setHasFixedSize(true);

        holder.rvJobs.setLayoutManager(new LinearLayoutManager(context));
        JobseekerInvoiceJobsAdapter listJobAdapter = new JobseekerInvoiceJobsAdapter(context, invoice.getJobsName(), invoice.getJobsFee());
        holder.rvJobs.setAdapter(listJobAdapter);
        holder.tvTotalFee.setText(String.valueOf(invoice.getTotalFee()));

        if (!invoice.getInvoiceStatus().equals("OnGoing")) {
            holder.containerButton.setVisibility(View.GONE);
        } else {
            holder.containerButton.setVisibility(View.VISIBLE);
            holder.btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeStatus(view, invoice.getId(), "Cancelled");
                }
            });

            holder.btnFinish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeStatus(view, invoice.getId(), "Finished");
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listInvoice.size();
    }

    static class ListViewHolder extends RecyclerView.ViewHolder {
        TextView tvInvoiceID;
        TextView tvJobseekerName;
        TextView tvInvoiceDate;
        TextView tvPaymentType;
        TextView tvInvoiceStatus;
        TextView tvReferralCode;
        RecyclerView rvJobs;
        TextView tvTotalFee;
        LinearLayout containerReferralCode;
        LinearLayout containerButton;
        Button btnCancel;
        Button btnFinish;

        ListViewHolder(View view) {
            super(view);

            tvInvoiceID = view.findViewById(R.id.invoice_id);
            tvJobseekerName = view.findViewById(R.id.jobseeker_name);
            tvInvoiceDate = view.findViewById(R.id.invoice_date);
            tvPaymentType = view.findViewById(R.id.payment_type);
            tvInvoiceStatus = view.findViewById(R.id.invoice_status);
            tvReferralCode = view.findViewById(R.id.referral_code);
            rvJobs = view.findViewById(R.id.rvJobs);
            tvTotalFee = view.findViewById(R.id.total_fee);
            containerReferralCode = view.findViewById(R.id.containerReferralCode);
            containerButton = view.findViewById(R.id.containerButton);
            btnCancel = view.findViewById(R.id.btnCancel);
            btnFinish = view.findViewById(R.id.btnFinish);
        }
    }
}