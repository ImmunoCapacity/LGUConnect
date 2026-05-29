package com.lguconnect.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.lguconnect.R;
import com.lguconnect.activities.LoginActivity;

public class AdminSettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_settings);

        SwitchMaterial swManageAnn  = findViewById(R.id.swManageAnnouncements);
        SwitchMaterial swEmergency  = findViewById(R.id.swSendEmergency);
        SwitchMaterial swCategories = findViewById(R.id.swManageCategories);
        SwitchMaterial swUsers      = findViewById(R.id.swManageUsers);
        SwitchMaterial swDarkMode   = findViewById(R.id.swDarkMode);

        swManageAnn.setChecked(true);
        swEmergency.setChecked(true);
        swCategories.setChecked(true);
        swUsers.setChecked(false);
        swDarkMode.setChecked(false);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavAdmin);
        bottomNav.setSelectedItemId(R.id.nav_admin_settings);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_admin_settings) return true;
            if (id == R.id.nav_admin_dashboard) { finish(); return true; }
            if (id == R.id.nav_admin_announcements) {
                startActivity(new Intent(this, ManageAnnouncementsActivity.class)); return true;
            }
            if (id == R.id.nav_admin_categories) {
                startActivity(new Intent(this, ManageCategoriesActivity.class)); return true;
            }
            return false;
        });

        findViewById(R.id.rowSuperAdmin).setOnClickListener(v ->
                android.widget.Toast.makeText(this, "Super Admin", android.widget.Toast.LENGTH_SHORT).show());
        findViewById(R.id.rowAdministrator).setOnClickListener(v ->
                android.widget.Toast.makeText(this, "Administrator", android.widget.Toast.LENGTH_SHORT).show());
        findViewById(R.id.rowNotifications).setOnClickListener(v ->
                android.widget.Toast.makeText(this, "Notifications settings", android.widget.Toast.LENGTH_SHORT).show());
    }
}
