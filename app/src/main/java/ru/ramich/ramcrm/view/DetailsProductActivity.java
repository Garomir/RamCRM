package ru.ramich.ramcrm.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import ru.ramich.ramcrm.R;
import ru.ramich.ramcrm.model.DaoProducts;
import ru.ramich.ramcrm.model.Product;

public class DetailsProductActivity extends AppCompatActivity {

    EditText etNameProduct, etCostProduct;
    ImageView ivProduct;
    private int productID;
    private String productName;
    private String productImagePath = "";
    private int productCost;
    DaoProducts daoProducts;
    Bitmap bitmap = null;

    static final int GALLERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_product);

        daoProducts = new DaoProducts(this);
        daoProducts.open();

        etCostProduct = findViewById(R.id.etCostProduct);
        etNameProduct = findViewById(R.id.etNameProduct);
        ivProduct = findViewById(R.id.ivProduct);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            productID = extras.getInt("prodID");
            productName = extras.getString("prodName");
            productImagePath = extras.getString("prodImage");
            productCost = extras.getInt("prodCost");

            etNameProduct.setText(productName);
            etCostProduct.setText(String.valueOf(productCost));
            ivProduct.setImageBitmap(getProductImage());
        }
    }

    public void onSave(View view) {
        if (etNameProduct.getText().length() == 0 || etCostProduct.getText().length() == 0){
            Toast.makeText(this, "Поля не могут быть пустыми!", Toast.LENGTH_LONG).show();
        } else {
            if (productID > 0){
                updateProductPicture(productID, bitmap);
                Product product = new Product(
                        productID,
                        etNameProduct.getText().toString(),
                        Integer.parseInt(String.valueOf(etCostProduct.getText())),
                        productImagePath);
                daoProducts.updateProduct(product);
                finish();
            } else {
                updateProductPicture(productID, bitmap);
                Product product = new Product(
                        etNameProduct.getText().toString(),
                        Integer.parseInt(String.valueOf(etCostProduct.getText())),
                        productImagePath);
                daoProducts.addProduct(product);
                finish();
            }
        }
    }

    public void updateProductPicture(int productID, Bitmap picture) {
        // Saves the new picture to the internal storage with the unique identifier of the product as
        // the name. That way, there will never be two report pictures with the same name.
        File internalStorage = this.getDir("ReportPictures", Context.MODE_PRIVATE);
        File productFilePath = new File(internalStorage, System.currentTimeMillis() + ".png");
        productImagePath = productFilePath.toString();

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(productFilePath);
            picture.compress(Bitmap.CompressFormat.PNG, 100 /*quality*/, fos);
            fos.close();
        }
        catch (Exception ex) {
            Log.i("DATABASE", "Problem updating picture", ex);
            productImagePath = "";
        }
    }

    public Bitmap getProductImage() {
        if (productImagePath == null || productImagePath.length() == 0)
            return (null);

        return (BitmapFactory.decodeFile(productImagePath));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        daoProducts.close();
    }

    public void onSetImage(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case GALLERY_REQUEST:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ivProduct.setImageBitmap(bitmap);
                }
        }
    }
}