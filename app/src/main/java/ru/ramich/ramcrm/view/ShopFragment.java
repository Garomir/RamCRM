package ru.ramich.ramcrm.view;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ru.ramich.ramcrm.R;
import ru.ramich.ramcrm.model.DaoProducts;
import ru.ramich.ramcrm.model.Product;

public class ShopFragment extends Fragment {

    DaoProducts daoProducts;
    ListView lvProducts;
    ProductsAdapter adapter;
    Button btnAddProduct;
    List<Product> products = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);

        daoProducts = new DaoProducts(getContext());
        daoProducts.open();
        //daoProducts.createProducts();

        btnAddProduct = view.findViewById(R.id.btnAddProduct);
        btnAddProduct.setOnClickListener(v -> showDialog());

        lvProducts = view.findViewById(R.id.lvProducts);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getAllProducts();
    }

    public void getAllProducts(){
        products.clear();
        products = daoProducts.getAllProducts();
        fillListView(products);
    }

    public void fillListView(List<Product> ourList){
        adapter = new ProductsAdapter(ourList);
        lvProducts.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        daoProducts.close();
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
                    if (someName.length() == 0){
                        Toast.makeText(getContext(), "Enter the fields!", Toast.LENGTH_SHORT).show();
                    } else {
                        if (daoProducts.addProduct(new Product(someName, someCost))){
                            getAllProducts();
                            Toast.makeText(getContext(), "Product is added!", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> Toast.makeText(getContext(), "Cancel", Toast.LENGTH_SHORT).show());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}