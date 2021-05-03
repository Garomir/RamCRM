package ru.ramich.ramcrm.view;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ru.ramich.ramcrm.R;

public class HistoryFragment extends Fragment {

    Button btnSetDate;
    TextView tvDate;
    Calendar calendar = Calendar.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        tvDate = view.findViewById(R.id.tvDate);
        btnSetDate = view.findViewById(R.id.btnSetDate);
        btnSetDate.setOnClickListener(v -> {
            DatePickerDialog.OnDateSetListener dateSetListener = (view1, year, month, dayOfMonth) -> {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                setTextView();
            };
            DatePickerDialog dateDialog = new DatePickerDialog(getContext(), dateSetListener,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            dateDialog.show();
        });

        return view;
    }

    public void setTextView(){
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date(calendar.getTimeInMillis());
        tvDate.setText(sdf.format(date));
    }
}