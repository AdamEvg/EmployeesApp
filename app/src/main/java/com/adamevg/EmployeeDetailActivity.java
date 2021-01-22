package com.adamevg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.adamevg.pojo.Employee;
import com.adamevg.pojo.Specialty;
import com.adamevg.viewmodel.EmployeeViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EmployeeDetailActivity extends AppCompatActivity {
    private long employeeId;
    private EmployeeViewModel employeeViewModel;
    private Employee employee;

    private ImageView ivAvatar;
    private TextView tvSpecialty;
    private TextView tvBirthday;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        final Intent intent = getIntent();
        if (intent != null && intent.hasExtra("ID")) {
            employeeId = intent.getLongExtra("ID", -1);
        } else {
            finish();
        }

        employeeViewModel = ViewModelProviders.of(this).get(EmployeeViewModel.class);
        employee = employeeViewModel.getEmployeeById(employeeId);

        toolbar = findViewById(R.id.toolbar__activity_employee);
        toolbar.setTitle(employee.getFirstName());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        ivAvatar = findViewById(R.id.image_view_avatar__activity_employee);
        tvSpecialty = findViewById(R.id.specialty__activity_employee);
        tvBirthday = findViewById(R.id.birthday__activity_employee);



        initViews();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        tvSpecialty.setText(getSpecialty(employee.getSpecialty()));
        tvBirthday.setText(employee.getBirthday());
        setImage();
    }

    private void setImage() {
        if (employee.getAvatarUrl()==null||employee.getAvatarUrl().isEmpty()) {
            ivAvatar.setImageResource(R.drawable.avatar);
        } else {
            Picasso.get().load(employee.getAvatarUrl()).placeholder(R.drawable.avatar).into(ivAvatar);
        }
    }

    private String getSpecialty(List<Specialty> specialtyList) {
        StringBuilder returnSpecialty = new StringBuilder();
        for (int i = 0; i < specialtyList.size(); i++) {
            if (i != specialtyList.size() - 1) {
                returnSpecialty.append(specialtyList.get(i).getName()).append("\n");
            } else {
                returnSpecialty.append(specialtyList.get(i).getName());
            }
        }


        return returnSpecialty.toString();
    }

}