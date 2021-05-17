package ru.ramich.ramcrm.view;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ru.ramich.ramcrm.R;
import ru.ramich.ramcrm.model.Client;

public class ClientsAdapter extends BaseAdapter {

    private List<Client> clients;

    public ClientsAdapter(List<Client> clients) {
        super();
        this.clients = clients;
    }

    @Override
    public int getCount() {
        return clients.size();
    }

    @Override
    public Object getItem(int position) {
        return clients.get(position);
    }

    @Override
    public long getItemId(int position) {
        return clients.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) v = View.inflate(parent.getContext(), R.layout.list_item_client, null);
        TextView txt1 = v.findViewById(R.id.tvClientName);
        TextView txt2 = v.findViewById(R.id.tvClientPhone);
        txt1.setText(clients.get(position).getName());
        txt2.setText(clients.get(position).getPhone());
        return v;
    }
}
