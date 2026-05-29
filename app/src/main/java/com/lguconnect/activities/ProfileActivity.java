package com.lguconnect.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lguconnect.R;

public class ProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.nav_profile);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_profile) return true;
            if (id == R.id.nav_home) { finish(); return true; }
            if (id == R.id.nav_notifications) {
                startActivity(new Intent(this, NotificationsActivity.class)); return true;
            }
            if (id == R.id.nav_categories) {
                startActivity(new Intent(this, CategoriesActivity.class)); return true;
            }
            return false;
        });

        findViewById(R.id.rowNotifSettings).setOnClickListener(v ->
                android.widget.Toast.makeText(this, "Notification Settings", android.widget.Toast.LENGTH_SHORT).show());
        findViewById(R.id.rowSaved).setOnClickListener(v ->
                android.widget.Toast.makeText(this, "Saved Announcements", android.widget.Toast.LENGTH_SHORT).show());
        findViewById(R.id.rowAbout).setOnClickListener(v ->
                android.widget.Toast.makeText(this, "About LGU Connect", android.widget.Toast.LENGTH_SHORT).show());
        findViewById(R.id.rowHelp).setOnClickListener(v ->
                android.widget.Toast.makeText(this, "Help & Support", android.widget.Toast.LENGTH_SHORT).show());
        findViewById(R.id.rowPrivacy).setOnClickListener(v ->
                android.widget.Toast.makeText(this, "Privacy Policy", android.widget.Toast.LENGTH_SHORT).show());
        findViewById(R.id.btnLogout).setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finishAffinity();
        });
    }
}
