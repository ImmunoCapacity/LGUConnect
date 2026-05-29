package com.lguconnect.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lguconnect.R;
import com.lguconnect.activities.AnnouncementDetailActivity;
import com.lguconnect.adapters.AnnouncementAdapter;
import com.lguconnect.data.Repository;

public class AdminDashboardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        TextView tvTotal      = findViewById(R.id.tvTotalAnnouncements);
        TextView tvResidents  = findViewById(R.id.tvActiveResidents);
        TextView tvEmergency  = findViewById(R.id.tvEmergencyAlerts);
        TextView tvCategories = findViewById(R.id.tvCategoryCount);
        RecyclerView rv       = findViewById(R.id.rvAdminAnnouncements);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavAdmin);

        Repository repo = Repository.get();
        tvTotal.setText(String.valueOf(repo.getTotalAnnouncements()));
        tvResidents.setText(String.valueOf(repo.getActiveResidents()));
        tvEmergency.setText(String.valueOf(repo.getEmergencyAlertsSent()));
        tvCategories.setText(String.valueOf(repo.getCategoryCount()));

        AnnouncementAdapter adapter = new AnnouncementAdapter(true, new AnnouncementAdapter.Listener() {
            @Override
            public void onOpen(com.lguconnect.models.Announcement a) {
                Intent i = new Intent(AdminDashboardActivity.this, AnnouncementDetailActivity.class);
                i.putExtra("announcementId", a.getId());
                startActivity(i);
            }
            @Override
            public void onEdit(com.lguconnect.models.Announcement a) {
                Intent i = new Intent(AdminDashboardActivity.this, PostAnnouncementActivity.class);
                i.putExtra("editId", a.getId());
                startActivity(i);
            }
            @Override
            public void onDelete(com.lguconnect.models.Announcement a) {
                new androidx.appcompat.app.AlertDialog.Builder(AdminDashboardActivity.this)
                        .setTitle("Delete announcement")
                        .setMessage("Are you sure you want to delete \"" + a.getTitle() + "\"?")
                        .setPositiveButton("Delete", (d, w) -> {
                            Repository.get().deleteAnnouncement(a.getId());
                            refreshList(rv);
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
        adapter.submit(repo.getAnnouncements());

        // Quick Actions
        findViewById(R.id.btnCreateAnnouncement).setOnClickListener(v ->
                startActivity(new Intent(this, PostAnnouncementActivity.class)));
        findViewById(R.id.btnSendEmergency).setOnClickListener(v ->
                startActivity(new Intent(this, EmergencyAlertActivity.class)));
        findViewById(R.id.btnManageCategories).setOnClickListener(v ->
                startActivity(new Intent(this, ManageCategoriesActivity.class)));

        // Bottom nav
        bottomNav.setSelectedItemId(R.id.nav_admin_dashboard);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_admin_dashboard) return true;
            if (id == R.id.nav_admin_announcements) {
                startActivity(new Intent(this, ManageAnnouncementsActivity.class)); return true;
            }
            if (id == R.id.nav_admin_categories) {
                startActivity(new Intent(this, ManageCategoriesActivity.class)); return true;
            }
            if (id == R.id.nav_admin_settings) {
                startActivity(new Intent(this, AdminSettingsActivity.class)); return true;
            }
            return false;
        });
    }

    private void refreshList(RecyclerView rv) {
        AnnouncementAdapter a = (AnnouncementAdapter) rv.getAdapter();
        if (a != null) a.submit(Repository.get().getAnnouncements());
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh stats
        TextView tvTotal = findViewById(R.id.tvTotalAnnouncements);
        if (tvTotal != null) tvTotal.setText(String.valueOf(Repository.get().getTotalAnnouncements()));
    }
}
