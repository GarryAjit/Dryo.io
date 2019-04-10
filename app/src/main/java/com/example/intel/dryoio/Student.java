package com.example.intel.dryoio;

import java.util.ArrayList;

public class Student {

    private String Name;
    private String MobileNo;
    private int BlockNo;
    private int RoomNo;
    private String Username;
    private String Password;
    private double balance;

    public Student() {

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public int getBlockNo() {
        return BlockNo;
    }

    public void setBlockNo(int blockNo) {
        BlockNo = blockNo;
    }

    public int getRoomNo() {
        return RoomNo;
    }

    public void setRoomNo(int roomNo) {
        RoomNo = roomNo;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
