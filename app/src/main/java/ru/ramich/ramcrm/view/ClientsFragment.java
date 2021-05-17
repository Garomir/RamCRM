package ru.ramich.ramcrm.view;

import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ru.ramich.ramcrm.R;
import ru.ramich.ramcrm.model.Client;
import ru.ramich.ramcrm.model.DaoClients;
import ru.ramich.ramcrm.model.Product;

public class ClientsFragment extends Fragment {

    DaoClients daoClients;
    ClientsAdapter clientsAdapter;
    List<Client> clients = new ArrayList<>();
    ListView lvClients;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clients, container, false);

        daoClients = new DaoClients(getContext());
        daoClients.open();

        lvClients = view.findViewById(R.id.lvClients);
        registerForContextMenu(lvClients);

        return view;
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
        lvClients.setAdapter(clientsAdapter);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.clients_option_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_add_client:
                //------------------------
                showDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE, R.id.delete_client, Menu.NONE, R.string.delete_order);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.delete_client:
                daoClients.deleteClient((int) clientsAdapter.getItemId(acmi.position));
                getAllClients();
                Toast.makeText(getContext(), "Удаляем клиента!", Toast.LENGTH_LONG).show();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void showDialog() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogView = inflater.inflate(R.layout.dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);

        final EditText etName = dialogView.findViewById(R.id.etName);
        final EditText etPhone = dialogView.findViewById(R.id.etPhone);

        builder
                .setPositiveButton("Add", (dialog, which) -> {
                    //Можно передавать строку в активити по нажатию
                    String someName = etName.getText().toString();
                    String somePhone = etPhone.getText().toString();
                    if (someName.length() == 0 || somePhone.length() == 0) {
                        Toast.makeText(getContext(), "Enter the fields!", Toast.LENGTH_SHORT).show();
                    } else {
                        if (daoClients.addClient(new Client(someName, somePhone))) {
                            getAllClients();
                            Toast.makeText(getContext(), "Client is added!", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> Toast.makeText(getContext(), "Cancel", Toast.LENGTH_SHORT).show());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        daoClients.close();
    }
}