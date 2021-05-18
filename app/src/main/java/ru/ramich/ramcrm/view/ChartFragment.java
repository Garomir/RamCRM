package ru.ramich.ramcrm.view;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ru.ramich.ramcrm.R;
import ru.ramich.ramcrm.model.DaoOrders;
import ru.ramich.ramcrm.model.Order;

public class ChartFragment extends Fragment {

    DaoOrders daoOrders;
    List<Order> orders = new ArrayList<>();
    List<Integer> weeklyOrders = new ArrayList<>();
    Calendar c;
    BarChart barChart;
    List<BarEntry> entries = new ArrayList<>();
    List<IBarDataSet> dataSets = new ArrayList<>();
    Legend legend;
    int[] legendColors = {Color.YELLOW, Color.GRAY, Color.RED, Color.BLACK, Color.BLUE, Color.GREEN, Color.MAGENTA};
    String[] nameLabels = new String[7];

    int entryIndex = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart, container, false);

        barChart = view.findViewById(R.id.bar_chart);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(false);
        legend = barChart.getLegend();
        legend.setEnabled(true);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false);

        daoOrders = new DaoOrders(getContext());
        daoOrders.open();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getAllOrders();
        getWeeklyOrders();
    }

    public void getAllOrders(){
        orders.clear();
        orders = daoOrders.getAllOrders();
    }

    public void getWeeklyOrders(){
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        int labelIndex = 0;
        for (int i = 0; i >= -6; i--) {
            int summaByDay = 0;
            c = Calendar.getInstance();
            c.add(Calendar.DATE, i);
            Date date = c.getTime();
            String str = format.format(date);
            nameLabels[labelIndex++] = str;
            for (Order o: orders) {
                if (o.getCreationDate().equals(str)){
                    summaByDay += o.getProductCost();
                }
            }
            entries.add(new BarEntry(entryIndex, summaByDay));
            entryIndex++;
        }
        setLegendLabels();
        BarDataSet barDataSet = new BarDataSet(entries, "Доход за неделю");
        barDataSet.setColors(legendColors);
        dataSets.add(barDataSet);
        BarData barData = new BarData(dataSets);
        barChart.setFitBars(true);
        barChart.animateY(2000);
        barChart.setData(barData);
    }

    public void setLegendLabels(){
        LegendEntry[] legendEntries = new LegendEntry[7];
        for (int i = 0; i<legendEntries.length; i++){
            LegendEntry entry = new LegendEntry();
            entry.formColor = legendColors[i];
            entry.label = nameLabels[i];
            legendEntries[i] = entry;
        }
        legend.setCustom(legendEntries);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        daoOrders.close();
    }
}