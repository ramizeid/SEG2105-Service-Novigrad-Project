package com.seg2105.servicenovigrad;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class ServiceListCheckboxes extends ArrayAdapter<Service> {
    private Activity context;
    List<Service> services;

    public ServiceListCheckboxes(Activity context, List<Service> services) {
        super(context, R.layout.service_checklist_layout, services);
        this.context = context;
        this.services = services;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.service_checklist_layout, null, true);

        CheckBox checkBox = (CheckBox) listViewItem.findViewById(R.id.checkBoxSelectService);
        TextView serviceNameTextView = (TextView) listViewItem.findViewById(R.id.serviceName);

        Service service = services.get(position);
        serviceNameTextView.setText(service.getServiceName());
        return listViewItem;
    }
}
