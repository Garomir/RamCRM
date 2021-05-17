package ru.ramich.ramcrm.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ru.ramich.ramcrm.R;
import ru.ramich.ramcrm.model.Client;
import ru.ramich.ramcrm.model.DaoClients;

public class SelectClientActivity extends AppCompatActivity {

    ListView lvClientSelect;
    DaoClients daoClients;
    List<Client> clients = new ArrayList<>();
    ClientsAdapter clientsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_client);

        daoClients = new DaoClients(this);
        daoClients.open();

        lvClientSelect = findViewById(R.id.lvClientsSelect);
        lvClientSelect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Client client = (Client) clientsAdapter.getItem(position);
                Intent intent = new Intent();
                intent.putExtra("name", client.getName());
                intent.putExtra("phone", client.getPhone());
                setResult(RESULT_OK, intent);
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
    }
}