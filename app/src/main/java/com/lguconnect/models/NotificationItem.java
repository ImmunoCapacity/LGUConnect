package com.lguconnect.models;

public class NotificationItem {
    public enum Type { EMERGENCY, GENERAL }

    private String id;
    private String title;
    private String body;
    private Type type;
    private long timeMillis;
    private boolean unread;

    public NotificationItem() {}
    public NotificationItem(String id, String title, String body, Type type, long timeMillis, boolean unread) {
        this.id = id; this.title = title; this.body = body;
        this.type = type; this.timeMillis = timeMillis; this.unread = unread;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getBody() { return body; }
    public Type getType() { return type; }
    public long getTimeMillis() { return timeMillis; }
    public boolean isUnread() { return unread; }
    public void setUnread(boolean u) { this.unread = u; }
}
