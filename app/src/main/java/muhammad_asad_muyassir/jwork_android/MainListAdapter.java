package muhammad_asad_muyassir.jwork_android;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class MainListAdapter adalah adapter yang
 * menampilkan seluruh perekrut dan jobnya
 *
 * @author Muhammad As'ad Muyassir
 * @version 27-06-2021
 */
public class MainListAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private ArrayList<Recruiter> _listDataHeader;
    private HashMap<Recruiter, ArrayList<Job>> _listDataChild;

    public MainListAdapter(Context context, ArrayList<Recruiter> listDataHeader, HashMap<Recruiter, ArrayList<Job>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Job getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final Job job = getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.layout_job, null);
        }

        TextView tvListChildName = (TextView) convertView.findViewById(R.id.lblListItemName);
        TextView tvListChildCategory = (TextView) convertView.findViewById(R.id.lblListItemCategory);

        tvListChildName.setText(job.getName());
        tvListChildCategory.setText(job.getCategory());
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Recruiter getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        Recruiter recruiter = getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.layout_recruiter, null);
        }

        TextView tvListHeaderName = (TextView) convertView.findViewById(R.id.lblListHeaderName);
        TextView tvListHeaderEmail = (TextView) convertView.findViewById(R.id.lblListHeaderEmail);
        ImageView ivListHeaderImg = (ImageView) convertView.findViewById(R.id.lblListHeaderImg);

        tvListHeaderName.setText(recruiter.getName());
        tvListHeaderEmail.setText(recruiter.getEmail());

        ShowImage showImage = new ShowImage(ivListHeaderImg, "recruiter", recruiter.getId() + ".jpg", R.drawable.dummy_avatar);
        showImage.show();

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
