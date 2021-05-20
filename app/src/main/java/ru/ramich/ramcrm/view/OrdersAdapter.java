package ru.ramich.ramcrm.view;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ru.ramich.ramcrm.R;
import ru.ramich.ramcrm.model.Client;
import ru.ramich.ramcrm.model.Order;
import ru.ramich.ramcrm.model.Product;

public class OrdersAdapter extends BaseAdapter {

    private List<Order> orders;
    private List<Product> products;
    private List<Client> clients;

    public OrdersAdapter(List<Order> orders, List<Product> products, List<Client> clients) {
        super();
        this.orders = orders;
        this.products = products;
        this.clients = clients;
    }

    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Object getItem(int position) {
        return orders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return orders.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) v = View.inflate(parent.getContext(), R.layout.list_item_order, null);
        TextView txt1 = v.findViewById(R.id.tvProductName);
        TextView txt2 = v.findViewById(R.id.tvProductCost);
        TextView txt3 = v.findViewById(R.id.tvClientNameOrd);
        TextView txt4 = v.findViewById(R.id.tvClientPhoneOrd);
        TextView txt5 = v.findViewById(R.id.tvCreationDate);
        txt1.setText(products.get(position).getName());
        txt2.setText(products.get(position).getCost() + " рублей");
        txt3.setText(clients.get(position).getName());
        txt4.setText(clients.get(position).getPhone());
        txt5.setText(orders.get(position).getCreationDate());
        return v;
    }
}
