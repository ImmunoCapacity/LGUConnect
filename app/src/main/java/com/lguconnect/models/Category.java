package com.lguconnect.models;

public class Category {
    private String id;
    private String name;
    private String color;
    private int iconRes;

    public Category() {}
    public Category(String id, String name, String color, int iconRes) {
        this.id = id; this.name = name; this.color = color; this.iconRes = iconRes;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String n) { this.name = n; }
    public String getColor() { return color; }
    public void setColor(String c) { this.color = c; }
    public int getIconRes() { return iconRes; }
    public void setIconRes(int r) { this.iconRes = r; }
}
