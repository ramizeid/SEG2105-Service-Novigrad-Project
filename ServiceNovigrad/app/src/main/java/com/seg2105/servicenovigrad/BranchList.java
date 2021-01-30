package com.seg2105.servicenovigrad;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class BranchList extends ArrayAdapter<Branch> {
    private Activity context;
    List<Branch> branches;

    public BranchList(Activity context, List<Branch> branches) {
        super(context, R.layout.service_list_layout, branches);
        this.context = context;
        this.branches = branches;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.service_list_layout, null, true);

        TextView serviceNameTextView = (TextView) listViewItem.findViewById(R.id.serviceNameTextView);

        Branch branch = branches.get(position);
        serviceNameTextView.setText(branch.getBranchName());
        return listViewItem;
    }
}
