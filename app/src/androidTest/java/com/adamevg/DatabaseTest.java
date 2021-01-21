package com.adamevg;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.adamevg.data.EmployeeDao;
import com.adamevg.data.EmployeeDatabase;
import com.adamevg.pojo.Employee;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    private EmployeeDao employeeDao;
    private EmployeeDatabase employeeDatabase;

    @Before
    public void createDb(){
        Context context = ApplicationProvider.getApplicationContext();
        employeeDatabase = Room.inMemoryDatabaseBuilder(context,EmployeeDatabase.class)
                .allowMainThreadQueries()
                .build();
        employeeDao = employeeDatabase.getEmployeeDao();
    }

    @After
    public void closeDb() throws IOException{
        employeeDatabase.close();
    }

    @Test
    public void insertUserAndCompareQuantityOfEmployeesInTable() throws Exception{
        Employee employee = new Employee();
        employeeDao.insertEmployee(employee);
        int quantityOfEmployees = employeeDao.getQuantityOfEmployeesInTable();
        assertEquals(quantityOfEmployees,1);
    }
}
