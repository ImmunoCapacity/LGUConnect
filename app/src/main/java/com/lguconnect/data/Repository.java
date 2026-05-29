package com.lguconnect.data;

import com.lguconnect.models.Announcement;
import com.lguconnect.models.Category;
import com.lguconnect.models.NotificationItem;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    private static Repository instance;
    private final List<Announcement> announcements = new ArrayList<>();
    private final List<Category> categories = new ArrayList<>();
    private final List<NotificationItem> notifications = new ArrayList<>();

    private Repository() {
        seed();
    }

    public static Repository get() {
        if (instance == null) instance = new Repository();
        return instance;
    }

    private void seed() {
        long now = System.currentTimeMillis();
        long min = 60_000L;

        announcements.add(new Announcement("1", "Heavy Rainfall Warning",
                "The Municipal Disaster Risk Reduction and Management Office (MDRRMO) reports that heavy to intense rains are expected in Balayan, Batangas due to the southwest monsoon. Residents are advised to stay indoors, avoid flooded areas, and monitor updates from LGU Connect.",
                "Weather", "#1E88E5", true, now - 2 * min, "LGU Balayan", Announcement.Status.PUBLISHED));
        announcements.add(new Announcement("2", "Class Suspension",
                "All levels of classes in public and private schools in Balayan are suspended effective July 1, 2024 due to inclement weather.",
                "Education", "#8E24AA", false, now - 60 * min, "LGU Balayan", Announcement.Status.PUBLISHED));
        announcements.add(new Announcement("3", "Vaccination Schedule",
                "Barangay vaccination drive on July 5–6, 2024. Free vaccines available at the Municipal Health Office.",
                "Health", "#388E3C", false, now - 3 * 60 * min, "LGU Balayan", Announcement.Status.PUBLISHED));
        announcements.add(new Announcement("4", "Balayan Town Fiesta 2024",
                "Join us for the grand celebration of Balayan Town Fiesta 2024. Schedule of activities and events will be posted soon.",
                "Events", "#F4511E", false, now - 24 * 60 * min, "LGU Balayan", Announcement.Status.DRAFT));
        announcements.add(new Announcement("5", "Power Interruption",
                "Scheduled power interruption on July 2, 2024 from 8AM to 5PM in Barangays 1–5.",
                "Public Advisory", "#607D8B", false, now - 48 * 60 * min, "LGU Balayan", Announcement.Status.PUBLISHED));

        categories.add(new Category("1", "Emergency", "#D32F2F", 0));
        categories.add(new Category("2", "Education", "#8E24AA", 0));
        categories.add(new Category("3", "Health", "#388E3C", 0));
        categories.add(new Category("4", "Events", "#F4511E", 0));
        categories.add(new Category("5", "Weather", "#1E88E5", 0));
        categories.add(new Category("6", "Public Advisory", "#607D8B", 0));

        notifications.add(new NotificationItem("n1", "Heavy Rainfall Warning",
                "Heavy rainfall warning in effect. Please stay safe.",
                NotificationItem.Type.EMERGENCY, now - 2 * min, true));
        notifications.add(new NotificationItem("n2", "Class Suspension",
                "All levels | July 1, 2024",
                NotificationItem.Type.GENERAL, now - 60 * min, true));
        notifications.add(new NotificationItem("n3", "Vaccination Schedule",
                "Barangay vaccination drive on July 5–6, 2024.",
                NotificationItem.Type.GENERAL, now - 3 * 60 * min, false));
        notifications.add(new NotificationItem("n4", "Community Event",
                "Balayan Town Fiesta 2024 Schedule of Activities",
                NotificationItem.Type.GENERAL, now - 24 * 60 * min, false));
        notifications.add(new NotificationItem("n5", "Power Interruption",
                "Scheduled power interruption on July 2, 2024.",
                NotificationItem.Type.GENERAL, now - 48 * 60 * min, false));
    }

    public List<Announcement> getAnnouncements() {
        return new ArrayList<>(announcements);
    }

    public List<Announcement> getPublishedAnnouncements() {
        List<Announcement> list = new ArrayList<>();
        for (Announcement a : announcements)
            if (a.getStatus() == Announcement.Status.PUBLISHED) list.add(a);
        return list;
    }

    public Announcement getAnnouncementById(String id) {
        for (Announcement a : announcements) if (a.getId().equals(id)) return a;
        return null;
    }

    public void addAnnouncement(Announcement a) {
        announcements.add(0, a);
    }

    public void updateAnnouncement(Announcement updated) {
        for (int i = 0; i < announcements.size(); i++) {
            if (announcements.get(i).getId().equals(updated.getId())) {
                announcements.set(i, updated);
                return;
            }
        }
    }

    public void deleteAnnouncement(String id) {
        announcements.removeIf(a -> a.getId().equals(id));
    }

    public List<Category> getCategories() {
        return new ArrayList<>(categories);
    }

    public void addCategory(Category c) {
        categories.add(c);
    }

    public void deleteCategory(String id) {
        categories.removeIf(c -> c.getId().equals(id));
    }

    public List<NotificationItem> getNotifications() {
        return new ArrayList<>(notifications);
    }

    public int getUnreadCount() {
        int count = 0;
        for (NotificationItem n : notifications) if (n.isUnread()) count++;
        return count;
    }

    public void markAllRead() {
        for (NotificationItem n : notifications) n.setUnread(false);
    }

    // Stats for admin dashboard
    public int getTotalAnnouncements() {
        return announcements.size();
    }

    public int getActiveResidents() {
        return 5432;
    }

    public int getEmergencyAlertsSent() {
        return 24;
    }

    public int getCategoryCount() {
        return categories.size();
    }
}
