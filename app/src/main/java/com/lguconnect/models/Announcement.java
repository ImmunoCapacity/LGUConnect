package com.lguconnect.models;

import java.io.Serializable;

public class Announcement implements Serializable {
    public enum Status { PUBLISHED, DRAFT, EXPIRED }

    private String id;
    private String title;
    private String body;
    private String category;
    private String categoryColor;
    private String imageUrl;
    private boolean isEmergency;
    private long postedAtMillis;
    private String postedBy;
    private boolean unread;
    private Status status;

    public Announcement() {}

    public Announcement(String id, String title, String body, String category,
                        String categoryColor, boolean isEmergency,
                        long postedAtMillis, String postedBy, Status status) {
        this.id = id; this.title = title; this.body = body;
        this.category = category; this.categoryColor = categoryColor;
        this.isEmergency = isEmergency; this.postedAtMillis = postedAtMillis;
        this.postedBy = postedBy; this.unread = true; this.status = status;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String t) { this.title = t; }
    public String getBody() { return body; }
    public void setBody(String b) { this.body = b; }
    public String getCategory() { return category; }
    public void setCategory(String c) { this.category = c; }
    public String getCategoryColor() { return categoryColor; }
    public void setCategoryColor(String c) { this.categoryColor = c; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String u) { this.imageUrl = u; }
    public boolean isEmergency() { return isEmergency; }
    public void setEmergency(boolean e) { this.isEmergency = e; }
    public long getPostedAtMillis() { return postedAtMillis; }
    public void setPostedAtMillis(long t) { this.postedAtMillis = t; }
    public String getPostedBy() { return postedBy; }
    public void setPostedBy(String p) { this.postedBy = p; }
    public boolean isUnread() { return unread; }
    public void setUnread(boolean u) { this.unread = u; }
    public Status getStatus() { return status == null ? Status.PUBLISHED : status; }
    public void setStatus(Status s) { this.status = s; }
}
