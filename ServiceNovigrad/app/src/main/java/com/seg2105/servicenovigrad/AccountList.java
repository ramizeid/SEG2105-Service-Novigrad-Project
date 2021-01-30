package com.seg2105.servicenovigrad;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AccountList extends ArrayAdapter<User> {

    private Activity context;
    List<User> users;

    public AccountList(Activity context, List<User> users) {
        super(context, R.layout.account_list_layout, users);
        this.context = context;
        this.users = users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.account_list_layout, null, true);

        TextView accountNameTextView = (TextView) listViewItem.findViewById(R.id.accountNameTextView);
        TextView accountBranchTextView = (TextView) listViewItem.findViewById(R.id.accountBranchTextView);

        User user = users.get(position);
        String username = user.getUsername();
        String userBranch = user.getBranch();

        // Formatting branch names to make them look nicer
        if (userBranch.toLowerCase().equals("employee")) {
            userBranch = "Employee";
        } else if (userBranch.toLowerCase().equals("customer")) {
            userBranch = "Customer";
        } else if (userBranch.toLowerCase().equals("admin")) {
            userBranch = "Admin";
        }

        accountNameTextView.setText(username);
        accountBranchTextView.setText(userBranch + " Account");
        return listViewItem;
    }
}
