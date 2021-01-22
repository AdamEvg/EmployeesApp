package com.adamevg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import com.adamevg.adapters.EmployeeAdapter;
import com.adamevg.pojo.Employee;
import com.adamevg.viewmodel.EmployeeViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EmployeeAdapter employeeAdapter;
    private EmployeeViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!hasConnection(this)) {
            Toast.makeText(this, "Отсутствует интернет соединение", Toast.LENGTH_SHORT).show();
        }

        recyclerView = findViewById(R.id.recycler_view_employees__activity_main);
        employeeAdapter = new EmployeeAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(employeeAdapter);
        employeeAdapter.setEmployees(new ArrayList<>());

        viewModel = ViewModelProviders.of(this).get(EmployeeViewModel.class);
        viewModel.getEmployees().observe(this, employees -> employeeAdapter.setEmployees(employees));

        viewModel.getErrors().observe(this, throwable -> {
            if (throwable != null) {
                Toast.makeText(MainActivity.this, "Error" + throwable, Toast.LENGTH_SHORT).show();
                viewModel.clearErrors();
            }
        });
        viewModel.loadData();

        employeeAdapter.setOnEmployeeClickListener(position -> {
            Employee employee = employeeAdapter.getEmployees().get(position);
            Intent intent = new Intent(MainActivity.this, EmployeeDetailActivity.class);
            intent.putExtra("ID", employee.getEmployeeId());
            startActivity(intent);
        });


    }


    public static boolean hasConnection(final Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        return false;
    }

}