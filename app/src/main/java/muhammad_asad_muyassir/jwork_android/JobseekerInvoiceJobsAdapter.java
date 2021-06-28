package muhammad_asad_muyassir.jwork_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Class JobseekerInvoiceJobsAdapter adalah adapter yang
 * menampilkan seluruh job dari sebuah invoice
 *
 * @author Muhammad As'ad Muyassir
 * @version 27-06-2021
 */
public class JobseekerInvoiceJobsAdapter extends RecyclerView.Adapter<JobseekerInvoiceJobsAdapter.ListViewHolder> {
    Context context;
    ArrayList<String> listJobName;
    ArrayList<Integer> listJobFee;

    public JobseekerInvoiceJobsAdapter(Context context, ArrayList<String> listJobName, ArrayList<Integer> listJobFee) {
        this.context = context;
        this.listJobName = listJobName;
        this.listJobFee = listJobFee;
    }

    @NonNull
    @Override
    public JobseekerInvoiceJobsAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_jobs, parent, false);
        return new JobseekerInvoiceJobsAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobseekerInvoiceJobsAdapter.ListViewHolder holder, int position) {
        holder.tvJobName.setText(listJobName.get(position));
        holder.tvJobFee.setText(String.valueOf(listJobFee.get(position)));
    }

    @Override
    public int getItemCount() {
        return listJobFee.size();
    }

    static class ListViewHolder extends RecyclerView.ViewHolder {
        TextView tvJobName;
        TextView tvJobFee;

        ListViewHolder(View view) {
            super(view);

            tvJobName = view.findViewById(R.id.job_name);
            tvJobFee = view.findViewById(R.id.job_fee);
        }
    }
}
