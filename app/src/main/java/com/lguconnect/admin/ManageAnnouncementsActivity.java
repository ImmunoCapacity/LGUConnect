package com.lguconnect.admin;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.lguconnect.R;
import com.lguconnect.activities.AnnouncementDetailActivity;
import com.lguconnect.adapters.AnnouncementAdapter;
import com.lguconnect.data.Repository;
import com.lguconnect.models.Announcement;

import java.util.ArrayList;
import java.util.List;

public class ManageAnnouncementsActivity extends AppCompatActivity {
    private AnnouncementAdapter adapter;
    private String activeTab = "All";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_announcements);

        RecyclerView rv    = findViewById(R.id.rvManageAnnouncements);
        TabLayout tabs     = findViewById(R.id.tabsManage);
        EditText etSearch  = findViewById(R.id.etSearchAdmin);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavAdmin);

        adapter = new AnnouncementAdapter(true, new AnnouncementAdapter.Listener() {
            @Override public void onOpen(Announcement a) {
                Intent i = new Intent(ManageAnnouncementsActivity.this, AnnouncementDetailActivity.class);
                i.putExtra("announcementId", a.getId());
                startActivity(i);
            }
            @Override public void onEdit(Announcement a) {
                Intent i = new Intent(ManageAnnouncementsActivity.this, PostAnnouncementActivity.class);
                i.putExtra("editId", a.getId());
                startActivity(i);
            }
            @Override public void onDelete(Announcement a) {
                new androidx.appcompat.app.AlertDialog.Builder(ManageAnnouncementsActivity.this)
                        .setTitle("Delete?")
                        .setPositiveButton("Delete", (d, w) -> {
                            Repository.get().deleteAnnouncement(a.getId());
                            loadTab(activeTab, "");
                        })
                        .setNegativeButton("Cancel", null).show();
            }
        });
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
        loadTab("All", "");

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            public void onTabSelected(TabLayout.Tab tab) {
                activeTab = tab.getText() != null ? tab.getText().toString() : "All";
                loadTab(activeTab, etSearch.getText().toString());
            }
            public void onTabUnselected(TabLayout.Tab t) {}
            public void onTabReselected(TabLayout.Tab t) {}
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int st, int c, int a) {}
            public void afterTextChanged(Editable s) {}
            public void onTextChanged(CharSequence s, int st, int b, int c) {
                loadTab(activeTab, s.toString());
            }
        });

        bottomNav.setSelectedItemId(R.id.nav_admin_announcements);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_admin_announcements) return true;
            if (id == R.id.nav_admin_dashboard) { finish(); return true; }
            if (id == R.id.nav_admin_categories) {
                startActivity(new Intent(this, ManageCategoriesActivity.class)); return true;
            }
            if (id == R.id.nav_admin_settings) {
                startActivity(new Intent(this, AdminSettingsActivity.class)); return true;
            }
            return false;
        });
    }

    private void loadTab(String tab, String query) {
        String q = query.toLowerCase().trim();
        List<Announcement> result = new ArrayList<>();
        for (Announcement a : Repository.get().getAnnouncements()) {
            boolean matchTab = "All".equals(tab)
                    || (tab.equals("Published") && a.getStatus() == Announcement.Status.PUBLISHED)
                    || (tab.equals("Draft")     && a.getStatus() == Announcement.Status.DRAFT)
                    || (tab.equals("Expired")   && a.getStatus() == Announcement.Status.EXPIRED);
            boolean matchQ = q.isEmpty()
                    || (a.getTitle() != null && a.getTitle().toLowerCase().contains(q));
            if (matchTab && matchQ) result.add(a);
        }
        adapter.submit(result);
    }

    @Override protected void onResume() { super.onResume(); loadTab(activeTab, ""); }
}
