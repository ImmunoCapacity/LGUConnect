package com.lguconnect.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.lguconnect.R;
import com.lguconnect.adapters.AnnouncementAdapter;
import com.lguconnect.data.Repository;
import com.lguconnect.models.Announcement;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AnnouncementAdapter adapter;
    private List<Announcement> allAnnouncements = new ArrayList<>();
    private String activeFilter = "All";
    private String searchQuery = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvGreeting = findViewById(R.id.tvGreeting);
        TextView tvLocation = findViewById(R.id.tvLocation);
        RecyclerView rv     = findViewById(R.id.rvAnnouncements);
        SwipeRefreshLayout swipe = findViewById(R.id.swipeRefresh);
        ChipGroup chips     = findViewById(R.id.chipGroupFilters);
        EditText etSearch   = findViewById(R.id.etSearch);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);

        tvGreeting.setText("Good Morning, Juan Dela Cruz! 👋");
        tvLocation.setText("Balayan, Batangas");

        adapter = new AnnouncementAdapter(false, ann -> {
            Intent i = new Intent(this, AnnouncementDetailActivity.class);
            i.putExtra("announcementId", ann.getId());
            startActivity(i);
        });
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        loadData();

        swipe.setOnRefreshListener(() -> {
            loadData();
            swipe.setRefreshing(false);
        });

        chips.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (!checkedIds.isEmpty()) {
                Chip c = group.findViewById(checkedIds.get(0));
                if (c != null) {
                    activeFilter = c.getText().toString();
                    applyFilter();
                }
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int st, int c, int a) {}
            public void afterTextChanged(Editable s) {}
            public void onTextChanged(CharSequence s, int st, int b, int c) {
                searchQuery = s.toString().toLowerCase().trim();
                applyFilter();
            }
        });

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) return true;
            if (id == R.id.nav_notifications) {
                startActivity(new Intent(this, NotificationsActivity.class));
                return true;
            }
            if (id == R.id.nav_categories) {
                startActivity(new Intent(this, CategoriesActivity.class));
                return true;
            }
            if (id == R.id.nav_profile) {
                startActivity(new Intent(this, ProfileActivity.class));
                return true;
            }
            return false;
        });
    }

    private void loadData() {
        allAnnouncements = Repository.get().getPublishedAnnouncements();
        applyFilter();
    }

    private void applyFilter() {
        List<Announcement> result = new ArrayList<>();
        for (Announcement a : allAnnouncements) {
            boolean matchCat = "All".equals(activeFilter) || activeFilter.equalsIgnoreCase(a.getCategory());
            boolean matchSearch = searchQuery.isEmpty()
                    || (a.getTitle() != null && a.getTitle().toLowerCase().contains(searchQuery))
                    || (a.getBody() != null && a.getBody().toLowerCase().contains(searchQuery));
            if (matchCat && matchSearch) result.add(a);
        }
        adapter.submit(result);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
        // Keep bottom nav on Home
        BottomNavigationView nav = findViewById(R.id.bottomNav);
        nav.setSelectedItemId(R.id.nav_home);
    }
}
