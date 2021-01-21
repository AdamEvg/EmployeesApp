package com.adamevg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.adamevg.adapters.EmployeeAdapter;
import com.adamevg.api.ApiFactory;
import com.adamevg.api.ApiService;
import com.adamevg.pojo.Employee;
import com.adamevg.pojo.EmployeeResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EmployeeAdapter employeeAdapter;
    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view_employees__activity_main);
        employeeAdapter = new EmployeeAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(employeeAdapter);
        employeeAdapter.setEmployees(new ArrayList<>());

        compositeDisposable = new CompositeDisposable();

        ApiFactory apiFactory = ApiFactory.getInstance();
        ApiService apiService = apiFactory.getApiService();
        Disposable disposable = apiService.getEmployees()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<EmployeeResponse>() {
                    @Override
                    public void accept(EmployeeResponse employeeResponse) throws Exception {
                        employeeAdapter.setEmployees(employeeResponse.getEmployeeResponse());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
        compositeDisposable.add(disposable);
//        employeeAdapter.setOnEmployeeClickListener(position -> {
//            Employee employee2 = new Employee();
//            employee2 = employeeAdapter.getEmployees().get(position);
//            Intent intent = new Intent(MainActivity.this,EmployeeDetailActivity.class);
//            intent.putExtra("NAME",employee2.getFName());
//            startActivity(intent);
//        });
    }

    @Override
    protected void onDestroy() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
        super.onDestroy();
    }
}