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

    private final List<Order> orders;

    public OrdersAdapter(List<Order> orders) {
        super();
        this.orders = orders;
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
        txt1.setText(orders.get(position).getProductName());
        txt2.setText((orders.get(position).getProductCost()) + " рублей");
        txt3.setText(orders.get(position).getClientName());
        txt4.setText(orders.get(position).getClientPhone());
        txt5.setText(orders.get(position).getCreationDate());
        return v;
    }
}
