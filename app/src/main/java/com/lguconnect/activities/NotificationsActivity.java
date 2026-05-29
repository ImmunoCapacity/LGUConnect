package com.lguconnect.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.lguconnect.R;
import com.lguconnect.adapters.NotificationAdapter;
import com.lguconnect.data.Repository;
import com.lguconnect.models.NotificationItem;

import java.util.ArrayList;
import java.util.List;

public class NotificationsActivity extends AppCompatActivity {
    private NotificationAdapter adapter;
    private String activeTab = "All";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        RecyclerView rv = findViewById(R.id.rvNotifications);
        TabLayout tabs  = findViewById(R.id.tabsNotif);
        View btnBack    = findViewById(R.id.btnBack);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);

        btnBack.setOnClickListener(v -> finish());

        adapter = new NotificationAdapter();
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        Repository.get().markAllRead();
        loadTab("All");

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            public void onTabSelected(TabLayout.Tab tab) {
                activeTab = tab.getText() != null ? tab.getText().toString() : "All";
                loadTab(activeTab);
            }
            public void onTabUnselected(TabLayout.Tab tab) {}
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        bottomNav.setSelectedItemId(R.id.nav_notifications);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_notifications) return true;
            if (id == R.id.nav_home) { finish(); return true; }
            if (id == R.id.nav_categories) {
                startActivity(new android.content.Intent(this, CategoriesActivity.class));
                return true;
            }
            if (id == R.id.nav_profile) {
                startActivity(new android.content.Intent(this, ProfileActivity.class));
                return true;
            }
            return false;
        });
    }

    private void loadTab(String tab) {
        List<NotificationItem> all = Repository.get().getNotifications();
        List<NotificationItem> result = new ArrayList<>();
        for (NotificationItem n : all) {
            if ("All".equals(tab)) result.add(n);
            else if ("Unread".equals(tab) && n.isUnread()) result.add(n);
            else if ("Emergency".equals(tab) && n.getType() == NotificationItem.Type.EMERGENCY) result.add(n);
        }
        adapter.submit(result);
    }
}
