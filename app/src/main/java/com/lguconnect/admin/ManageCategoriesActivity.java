package com.lguconnect.admin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lguconnect.R;
import com.lguconnect.adapters.CategoryAdapter;
import com.lguconnect.data.Repository;
import com.lguconnect.models.Category;

import java.util.UUID;

public class ManageCategoriesActivity extends AppCompatActivity {
    private CategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_categories);

        RecyclerView rv = findViewById(R.id.rvAdminCategories);
        FloatingActionButton fab = findViewById(R.id.fabAddCategory);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavAdmin);

        adapter = new CategoryAdapter(cat -> showDeleteDialog(cat));
        rv.setLayoutManager(new GridLayoutManager(this, 2));
        rv.setAdapter(adapter);
        adapter.submit(Repository.get().getCategories());

        fab.setOnClickListener(v -> showAddDialog());

        bottomNav.setSelectedItemId(R.id.nav_admin_categories);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_admin_categories) return true;
            if (id == R.id.nav_admin_dashboard) { finish(); return true; }
            if (id == R.id.nav_admin_announcements) {
                startActivity(new Intent(this, ManageAnnouncementsActivity.class)); return true;
            }
            if (id == R.id.nav_admin_settings) {
                startActivity(new Intent(this, AdminSettingsActivity.class)); return true;
            }
            return false;
        });
    }

    private void showAddDialog() {
        int pad = (int)(16 * getResources().getDisplayMetrics().density);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(pad, pad, pad, pad);

        EditText etName  = new EditText(this); etName.setHint("Category name");
        EditText etColor = new EditText(this); etColor.setHint("Hex color (e.g. #D32F2F)");
        layout.addView(etName);
        layout.addView(etColor);

        new AlertDialog.Builder(this)
                .setTitle("Add Category")
                .setView(layout)
                .setPositiveButton("Save", (d, w) -> {
                    String name  = etName.getText().toString().trim();
                    String color = etColor.getText().toString().trim();
                    if (TextUtils.isEmpty(name)) { Toast.makeText(this, "Name required", Toast.LENGTH_SHORT).show(); return; }
                    if (!color.startsWith("#")) color = "#" + color;
                    try { Color.parseColor(color); } catch (Exception e) {
                        Toast.makeText(this, "Invalid color", Toast.LENGTH_SHORT).show(); return;
                    }
                    Repository.get().addCategory(new Category(UUID.randomUUID().toString(), name, color, 0));
                    adapter.submit(Repository.get().getCategories());
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showDeleteDialog(Category cat) {
        new AlertDialog.Builder(this)
                .setTitle("Delete " + cat.getName() + "?")
                .setPositiveButton("Delete", (d, w) -> {
                    Repository.get().deleteCategory(cat.getId());
                    adapter.submit(Repository.get().getCategories());
                })
                .setNegativeButton("Cancel", null).show();
    }
}
