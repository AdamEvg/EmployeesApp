package com.adamevg.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.adamevg.api.ApiFactory;
import com.adamevg.api.ApiService;
import com.adamevg.data.EmployeeDatabase;
import com.adamevg.pojo.Employee;;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class EmployeeViewModel extends AndroidViewModel {
    private CompositeDisposable compositeDisposable;
    private MutableLiveData<Throwable> errors;
    private static EmployeeDatabase employeeDatabase;
    private LiveData<List<Employee>> employees;

    public EmployeeViewModel(@NonNull Application application) {
        super(application);
        employeeDatabase = EmployeeDatabase.getInstance(application);
        employees = employeeDatabase.getEmployeeDao().getAllEmployees();
        errors = new MutableLiveData<>();
    }

    public LiveData<Throwable> getErrors() {
        return errors;
    }

    public void clearErrors(){
        errors.setValue(null);
    }

    public LiveData<List<Employee>> getEmployees() {
        return employees;
    }

    @SuppressWarnings("unchecked")
    public void insertEmployees(List<Employee> employees) {
        new InsertEmployeesTask().execute(employees);
    }

    private static class InsertEmployeesTask extends AsyncTask<List<Employee>, Void, Void> {

        @SafeVarargs
        @Override
        protected final Void doInBackground(List<Employee>... lists) {
            if (lists != null && lists.length > 0) {
                employeeDatabase.getEmployeeDao().insertEmployees(lists[0]);
            }
            return null;
        }
    }


    public void deleteAllEmployees() {
        new DeleteEmployeesTask().execute();
    }

    private static class DeleteEmployeesTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            employeeDatabase.getEmployeeDao().deleteAllEmployees();
            return null;
        }
    }

    public void loadData() {
        ApiFactory apiFactory = ApiFactory.getInstance();
        ApiService apiService = apiFactory.getApiService();
        compositeDisposable = new CompositeDisposable();
        Disposable disposable = apiService.getEmployees()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(employeeResponse -> {
                            deleteAllEmployees();
                            insertEmployees(employeeResponse.getEmployeeResponse());
                        },
                        throwable -> errors.setValue(throwable)
                );
        compositeDisposable.add(disposable);

    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }
}
