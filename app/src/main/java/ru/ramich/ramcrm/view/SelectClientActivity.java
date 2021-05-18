package ru.ramich.ramcrm.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import ru.ramich.ramcrm.R;
import ru.ramich.ramcrm.model.Client;
import ru.ramich.ramcrm.model.DaoClients;
import ru.ramich.ramcrm.model.DaoOrders;
import ru.ramich.ramcrm.model.Order;

public class SelectClientActivity extends AppCompatActivity {

    ListView lvClientSelect;
    DaoClients daoClients;
    DaoOrders daoOrders;
    List<Client> clients = new ArrayList<>();
    ClientsAdapter clientsAdapter;
    String productName;
    int productCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_client);

        daoClients = new DaoClients(this);
        daoClients.open();

        daoOrders = new DaoOrders(this);
        daoOrders.open();

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            productName = extras.getString("name");
            productCost = extras.getInt("cost");
        }

        lvClientSelect = findViewById(R.id.lvClientsSelect);
        lvClientSelect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Client client = (Client) clientsAdapter.getItem(position);
                String currentDateTime = new SimpleDateFormat("dd.MM.yyyy")
                        .format(System.currentTimeMillis());
                daoOrders.addOrder(new Order(productName, productCost, client.getName(), client.getPhone(), currentDateTime));
                Toast.makeText(getApplicationContext(), "Заказ успешно выполнен!", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getAllClients();
    }

    public void getAllClients() {
        clients.clear();
        clients = daoClients.getAllClients();
        fillListView(clients);
    }

    public void fillListView(List<Client> ourList) {
        clientsAdapter = new ClientsAdapter(ourList);
        lvClientSelect.setAdapter(clientsAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        daoClients.close();
        daoOrders.close();
    }
}