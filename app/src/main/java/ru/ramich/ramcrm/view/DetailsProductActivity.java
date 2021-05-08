package ru.ramich.ramcrm.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ru.ramich.ramcrm.R;
import ru.ramich.ramcrm.model.DaoProducts;
import ru.ramich.ramcrm.model.DbHelper;
import ru.ramich.ramcrm.model.Product;

public class DetailsProductActivity extends AppCompatActivity {

    EditText etNameProduct, etCostProduct;
    private int productID;
    private String productName;
    private int productCost;
    DaoProducts daoProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_product);

        daoProducts = new DaoProducts(this);
        daoProducts.open();

        etCostProduct = findViewById(R.id.etCostProduct);
        etNameProduct = findViewById(R.id.etNameProduct);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            productID = extras.getInt("prodID");
            productName = extras.getString("prodName");
            productCost = extras.getInt("prodCost");

            etNameProduct.setText(productName);
            etCostProduct.setText(String.valueOf(productCost));
        }
    }

    public void onSave(View view) {
        if (etNameProduct.getText().length() == 0 || etCostProduct.getText().length() == 0){
            Toast.makeText(this, "Поля не могут быть пустыми!", Toast.LENGTH_LONG).show();
        } else {
            Product product = new Product(
                    productID,
                    etNameProduct.getText().toString(),
                    Integer.parseInt(String.valueOf(etCostProduct.getText())));
            daoProducts.updateProduct(product);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        daoProducts.close();
    }
}