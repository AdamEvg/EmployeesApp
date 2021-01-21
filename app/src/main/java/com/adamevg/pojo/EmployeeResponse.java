package com.adamevg.pojo;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeeResponse {
    @SerializedName("response")
    @Expose
    private List<Employee> response = null;

    public List<Employee> getEmployeeResponse() {
        return response;
    }

    public void setResponse(List<Employee> response) {
        this.response = response;
    }
}
