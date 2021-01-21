package com.adamevg.pojo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "employees")
public class Employee {
    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    @PrimaryKey(autoGenerate = true)
    private long employeeId;

    @ColumnInfo(name = "first_name")
    @SerializedName("f_name")
    @Expose
    private String firstName;

    @ColumnInfo(name = "last_name")
    @SerializedName("l_name")
    @Expose
    private String lastName;

    @SerializedName("birthday")
    @Expose
    private String birthday;

    @ColumnInfo(name = "avatar_url")
    @SerializedName("avatr_url")
    @Expose
    private String avatarUrl;

//    @SerializedName("specialty")
//    @Expose
//    private List<Specialty> specialty = null;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

//    public List<Specialty> getSpecialty() {
//        return specialty;
//    }
//
//    public void setSpecialty(List<Specialty> specialty) {
//        this.specialty = specialty;
//    }
}
