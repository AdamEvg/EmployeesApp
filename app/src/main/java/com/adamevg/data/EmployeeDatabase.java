package com.adamevg.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.adamevg.pojo.Employee;

@Database(entities = {Employee.class}, version = 1, exportSchema = false)
public abstract class EmployeeDatabase extends RoomDatabase {
    private static final String DB_NAME = "employees.db";
    private static EmployeeDatabase employeeDatabase;
    private static final Object LOCK = new Object();

    private static EmployeeDatabase getInstance(Context context) {
        synchronized (LOCK){
            if (employeeDatabase == null) {
                employeeDatabase = Room
                        .databaseBuilder(context, EmployeeDatabase.class, DB_NAME)
                        .build();
            }
            return employeeDatabase;
        }
    }

    public abstract EmployeeDao getEmployeeDao();
}
