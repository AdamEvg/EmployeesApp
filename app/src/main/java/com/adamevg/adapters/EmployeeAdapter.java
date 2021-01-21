package com.adamevg.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adamevg.R;
import com.adamevg.pojo.Employee;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {
    private List<Employee> employees;
    private OnEmployeeClickListener onEmployeeClickListener;

    public interface OnEmployeeClickListener {
        void onEmployeeClick(int position);
    }

    public void setOnEmployeeClickListener(OnEmployeeClickListener onEmployeeClickListener) {
        this.onEmployeeClickListener = onEmployeeClickListener;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
        notifyDataSetChanged();
    }

    public OnEmployeeClickListener getOnEmployeeClickListener() {
        return onEmployeeClickListener;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_item, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee employee = employees.get(position);
        holder.textViewName.setText(employee.getFirstName());
        holder.textViewSurname.setText(employee.getLastName());

    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    class EmployeeViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;
        private TextView textViewSurname;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.name_textview__employee_item);
            textViewSurname = itemView.findViewById(R.id.surname_textview__employee_item);
            itemView.setOnClickListener(view->{
                if(onEmployeeClickListener!=null){
                    onEmployeeClickListener.onEmployeeClick(getAdapterPosition());
                }
            });
        }
    }
}
