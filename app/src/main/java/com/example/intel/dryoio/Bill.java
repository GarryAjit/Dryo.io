package com.example.intel.dryoio;

public class Bill {
    private String laundryVendorUsername;
    private String studentUsername;
    private int tshirtQuantity;
    private int shirtQuantity;
    private int pantQuantity;
    private int hankyQuantity;
    private int underwearQuantity;
    private int towelQuantity;
    private int blanketQuantity;
    private int socksQuantity;
    private int totalAmount;
    private String currentDate;
    private String dueDate;
    private boolean paid;

    public Bill() {
    }

    public String getLaundryVendorUsername() {
        return laundryVendorUsername;
    }

    public void setLaundryVendorUsername(String laundryVendorUsername) {
        this.laundryVendorUsername = laundryVendorUsername;
    }

    public String getStudentUsername() {
        return studentUsername;
    }

    public void setStudentUsername(String studentUsername) {
        this.studentUsername = studentUsername;
    }

    public int getTshirtQuantity() {
        return tshirtQuantity;
    }

    public void setTshirtQuantity(int tshirtQuantity) {
        this.tshirtQuantity = tshirtQuantity;
    }

    public int getShirtQuantity() {
        return shirtQuantity;
    }

    public void setShirtQuantity(int shirtQuantity) {
        this.shirtQuantity = shirtQuantity;
    }

    public int getPantQuantity() {
        return pantQuantity;
    }

    public void setPantQuantity(int pantQuantity) {
        this.pantQuantity = pantQuantity;
    }

    public int getHankyQuantity() {
        return hankyQuantity;
    }

    public void setHankyQuantity(int hankyQuantity) {
        this.hankyQuantity = hankyQuantity;
    }

    public int getUnderwearQuantity() {
        return underwearQuantity;
    }

    public void setUnderwearQuantity(int underwearQuantity) {
        this.underwearQuantity = underwearQuantity;
    }

    public int getTowelQuantity() {
        return towelQuantity;
    }

    public void setTowelQuantity(int towelQuantity) {
        this.towelQuantity = towelQuantity;
    }

    public int getBlanketQuantity() {
        return blanketQuantity;
    }

    public void setBlanketQuantity(int blanketQuantity) {
        this.blanketQuantity = blanketQuantity;
    }

    public int getSocksQuantity() {
        return socksQuantity;
    }

    public void setSocksQuantity(int socksQuantity) {
        this.socksQuantity = socksQuantity;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
