package ru.ramich.ramcrm.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import ru.ramich.ramcrm.R;
import ru.ramich.ramcrm.model.DaoOrders;
import ru.ramich.ramcrm.model.DaoProducts;
import ru.ramich.ramcrm.model.Order;
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
                showDialog();
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

    private void showDialog() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogView = inflater.inflate(R.layout.dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);

        final EditText etName = dialogView.findViewById(R.id.etName);
        final EditText etCost = dialogView.findViewById(R.id.etCost);

        builder
                .setPositiveButton("Add", (dialog, which) -> {
                    //Можно передавать строку в активити по нажатию
                    String someName = etName.getText().toString();
                    int someCost = Integer.parseInt(etCost.getText().toString());
                    if (someName.length() == 0) {
                        Toast.makeText(getContext(), "Enter the fields!", Toast.LENGTH_SHORT).show();
                    } else {
                        if (daoProducts.addProduct(new Product(someName, someCost))) {
                            getAllProducts();
                            Toast.makeText(getContext(), "Product is added!", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> Toast.makeText(getContext(), "Cancel", Toast.LENGTH_SHORT).show());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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
                String currentDateTime = new SimpleDateFormat("dd.MM.yyyy")
                        .format(System.currentTimeMillis());
                daoOrders.addOrder(new Order(product.getName(), product.getCost(), currentDateTime));
                Toast.makeText(getContext(), "Заказ успешно выполнен!", Toast.LENGTH_LONG).show();
                return true;
            case R.id.details_product:
                Intent intent = new Intent(getContext(), DetailsProductActivity.class);
                intent.putExtra("prodID", product.getId());
                intent.putExtra("prodName", product.getName());
                intent.putExtra("prodCost", product.getCost());
                intent.putExtra("prodImage", product.getImagePath());
                startActivity(intent);
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