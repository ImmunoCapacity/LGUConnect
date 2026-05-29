package com.lguconnect.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lguconnect.R;
import com.lguconnect.adapters.CategoryAdapter;
import com.lguconnect.data.Repository;

public class CategoriesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        RecyclerView rv = findViewById(R.id.rvCategories);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);

        CategoryAdapter adapter = new CategoryAdapter(cat -> {
            Toast.makeText(this, cat.getName() + " announcements", Toast.LENGTH_SHORT).show();
        });
        rv.setLayoutManager(new GridLayoutManager(this, 2));
        rv.setAdapter(adapter);
        adapter.submit(Repository.get().getCategories());

        bottomNav.setSelectedItemId(R.id.nav_categories);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_categories) return true;
            if (id == R.id.nav_home) { finish(); return true; }
            if (id == R.id.nav_notifications) {
                startActivity(new Intent(this, NotificationsActivity.class)); return true;
            }
            if (id == R.id.nav_profile) {
                startActivity(new Intent(this, ProfileActivity.class)); return true;
            }
            return false;
        });
    }
}
