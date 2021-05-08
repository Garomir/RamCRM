package ru.ramich.ramcrm.view;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.ramich.ramcrm.R;
import ru.ramich.ramcrm.model.Product;

public class ProductsAdapter extends BaseAdapter {

    private List<Product> products;

    public ProductsAdapter(List<Product> products) {
        super();
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return products.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        //if (v == null) v = View.inflate(parent.getContext(), R.layout.list_item_product, null);
        if (v == null) v = View.inflate(parent.getContext(), R.layout.grid_item_product, null);
        //TextView txt1 = v.findViewById(R.id.tvNameProduct);
        //TextView txt2 = v.findViewById(R.id.tvCostProduct);
        TextView txt1 = v.findViewById(R.id.tvNameProductGrid);
        TextView txt2 = v.findViewById(R.id.tvCostProductGrid);
        txt1.setText(products.get(position).getName());
        txt2.setText(products.get(position).getCost() + " рублей");
        return v;
    }
}
