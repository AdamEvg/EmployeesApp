package com.adamevg.api;

import com.adamevg.pojo.EmployeeResponse;

import io.reactivex.Observable;

import retrofit2.http.GET;

public interface ApiService {
    @GET("testTask.json")
    Observable<EmployeeResponse> getEmployees();
}
