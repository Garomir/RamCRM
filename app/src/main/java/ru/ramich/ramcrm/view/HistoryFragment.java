package ru.ramich.ramcrm.view;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ru.ramich.ramcrm.R;
import ru.ramich.ramcrm.model.DaoOrders;
import ru.ramich.ramcrm.model.Order;
import ru.ramich.ramcrm.model.Product;

public class HistoryFragment extends Fragment {

    Button btnSetDate;
    TextView tvDate;
    Calendar calendar = Calendar.getInstance();
    ListView lvOrders;
    List<Order> orders = new ArrayList<>();
    DaoOrders daoOrders;
    OrdersAdapter ordersAdapter;
    TextView tvSumma;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        tvSumma = view.findViewById(R.id.tvSumma);
        daoOrders = new DaoOrders(getContext());
        daoOrders.open();

        tvDate = view.findViewById(R.id.tvDate);
        btnSetDate = view.findViewById(R.id.btnSetDate);
        btnSetDate.setOnClickListener(v -> {
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
        });

        lvOrders = view.findViewById(R.id.lvOrders);

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

    public void fillListView(List<Order> ourList){
        ordersAdapter = new OrdersAdapter(ourList);
        lvOrders.setAdapter(ordersAdapter);
    }

    public void getSumma(List<Order> fewOrders){
        int summa = 0;
        for (Order o: fewOrders) {
            summa += o.getProductCost();
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
    }
}