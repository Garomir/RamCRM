package ru.ramich.ramcrm.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ru.ramich.ramcrm.R;
import ru.ramich.ramcrm.model.DaoOrders;
import ru.ramich.ramcrm.model.DaoProducts;
import ru.ramich.ramcrm.model.Product;

public class ShopFragment extends Fragment {

    View view;
    DaoProducts daoProducts;
    DaoOrders daoOrders;
    GridView gvProducts;
    ProductsAdapter adapter;
    List<Product> products = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shop, container, false);

        daoProducts = new DaoProducts(getContext());
        daoProducts.open();

        daoOrders = new DaoOrders(getContext());
        daoOrders.open();

        gvProducts = view.findViewById(R.id.gvProducts);
        registerForContextMenu(gvProducts);
        gvProducts.setOnItemClickListener((parent, view, position, id) -> {
            Toast.makeText(getContext(), "" + adapter.getItemId(position), Toast.LENGTH_LONG).show();
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getAllProducts();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.shop_option_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_add_product:
                Intent intent = new Intent(getContext(), DetailsProductActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getAllProducts() {
        products.clear();
        products = daoProducts.getAllProducts();
        fillListView(products);
    }

    public void fillListView(List<Product> ourList) {
        adapter = new ProductsAdapter(ourList);
        gvProducts.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        daoProducts.close();
        daoOrders.close();
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE, R.id.make_order, Menu.NONE, R.string.make_order);
        menu.add(Menu.NONE, R.id.details_product, Menu.NONE, R.string.details_product);
        menu.add(Menu.NONE, R.id.delete_product, Menu.NONE, R.string.delete_product);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Product product = (Product) adapter.getItem(acmi.position);
        switch (item.getItemId()) {
            case R.id.make_order:
                Intent intent = new Intent(getContext(), SelectClientActivity.class);
                intent.putExtra("productName", product.getName());
                intent.putExtra("productCost", product.getCost());
                startActivity(intent);
                return true;
            case R.id.details_product:
                Intent intent2 = new Intent(getContext(), DetailsProductActivity.class);
                intent2.putExtra("prodID", product.getId());
                intent2.putExtra("prodName", product.getName());
                intent2.putExtra("prodCost", product.getCost());
                intent2.putExtra("prodImage", product.getImagePath());
                startActivity(intent2);
                return true;
            case R.id.delete_product:
                daoProducts.deleteProduct(product.getId());
                getAllProducts();
                Toast.makeText(getContext(), "Удаляем продукт!", Toast.LENGTH_LONG).show();
                return true;
        }
        return super.onContextItemSelected(item);
    }
}