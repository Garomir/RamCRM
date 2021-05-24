package ru.ramich.ramcrm.view;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ru.ramich.ramcrm.R;
import ru.ramich.ramcrm.model.Client;
import ru.ramich.ramcrm.model.DaoClients;
import ru.ramich.ramcrm.model.DaoOrders;
import ru.ramich.ramcrm.model.DaoProducts;
import ru.ramich.ramcrm.model.Order;
import ru.ramich.ramcrm.model.Product;

public class HistoryFragment extends Fragment {

    TextView tvDate;
    Calendar calendar = Calendar.getInstance();
    ListView lvOrders;
    List<Order> orders = new ArrayList<>();
    List<Product> products = new ArrayList<>();
    List<Client> clients = new ArrayList<>();
    DaoOrders daoOrders;
    DaoProducts daoProducts;
    DaoClients daoClients;
    OrdersAdapter ordersAdapter;
    TextView tvSumma;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        tvSumma = view.findViewById(R.id.tvSumma);
        daoOrders = new DaoOrders(getContext());
        daoOrders.open();

        daoProducts = new DaoProducts(getContext());
        daoProducts.open();
        products.clear();
        products = daoProducts.getAllProducts();

        daoClients = new DaoClients(getContext());
        daoClients.open();
        clients.clear();
        clients = daoClients.getAllClients();

        tvDate = view.findViewById(R.id.tvDate);

        lvOrders = view.findViewById(R.id.lvOrders);
        registerForContextMenu(lvOrders);
        lvOrders.setOnItemClickListener((parent, view1, position, id) -> {
            Toast.makeText(getContext(), "" + ordersAdapter.getItemId(position), Toast.LENGTH_LONG).show();
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getAllOrders();
        getSumma(orders);
    }

    public void getAllOrders(){
        orders.clear();
        orders = daoOrders.getAllOrders();
        fillListView(orders);
    }

    public void getOrdersByDate(){
        List<Order> ordersByDate = new ArrayList<>();
        for (Order o: orders) {
            if (o.getCreationDate().equals(getDateString())){
                ordersByDate.add(o);
            }
        }
        fillListView(ordersByDate);
        getSumma(ordersByDate);
    }

    public void fillListView(List<Order> orders){
        ordersAdapter = new OrdersAdapter(orders, products, clients);
        lvOrders.setAdapter(ordersAdapter);
    }

    public void getSumma(List<Order> fewOrders){
        int summa = 0;
        for (Order o: fewOrders) {
            for (Product p: products) {
                if (p.getId() == o.getProductId()){
                    summa += p.getCost();
                }
            }
            //summa += o.getProductCost();
        }
        tvSumma.setText(summa + " рублей");
    }

    public String getDateString(){
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date(calendar.getTimeInMillis());
        return sdf.format(date);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        daoOrders.close();
        daoProducts.close();
        daoClients.close();
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE, R.id.delete_order, Menu.NONE, R.string.delete_order);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.delete_order:
                daoOrders.deleteOrder((int) ordersAdapter.getItemId(acmi.position));
                getAllOrders();
                Toast.makeText(getContext(), "Удаляем продукт!", Toast.LENGTH_LONG).show();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.history_option_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_date:
                getDate();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getDate(){
        DatePickerDialog.OnDateSetListener dateSetListener = (view1, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            tvDate.setText(getDateString());
            getOrdersByDate();
        };
        DatePickerDialog dateDialog = new DatePickerDialog(getContext(), dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dateDialog.show();
    }
}