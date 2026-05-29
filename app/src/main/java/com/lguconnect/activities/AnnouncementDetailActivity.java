package com.lguconnect.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.lguconnect.R;
import com.lguconnect.data.Repository;
import com.lguconnect.models.Announcement;
import com.lguconnect.utils.TimeUtils;

import android.graphics.Color;

public class AnnouncementDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement_detail);

        String id = getIntent().getStringExtra("announcementId");
        Announcement ann = id != null ? Repository.get().getAnnouncementById(id) : null;

        TextView tvCategory = findViewById(R.id.tvDetailCategory);
        TextView tvTitle    = findViewById(R.id.tvDetailTitle);
        TextView tvBody     = findViewById(R.id.tvDetailBody);
        TextView tvInfo     = findViewById(R.id.tvDetailInfo);
        ImageView ivImage   = findViewById(R.id.ivDetailImage);
        MaterialButton btnShare = findViewById(R.id.btnShare);
        MaterialButton btnSaved = findViewById(R.id.btnSaved);
        View btnBack        = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> finish());

        if (ann == null) {
            tvTitle.setText("Announcement not found");
            return;
        }

        ann.setUnread(false);

        tvCategory.setText(ann.getCategory());
        try { tvCategory.setBackgroundColor(Color.parseColor(
                ann.getCategoryColor() == null ? "#607D8B" : ann.getCategoryColor()));
        } catch (Exception ignored) {}

        tvTitle.setText(ann.getTitle());
        tvBody.setText(ann.getBody());
        tvInfo.setText(TimeUtils.formatDate(ann.getPostedAtMillis()) + "  •  " + ann.getPostedBy());
        ivImage.setImageResource(R.drawable.ic_placeholder_banner);

        btnShare.setOnClickListener(v -> {
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_SUBJECT, ann.getTitle());
            share.putExtra(Intent.EXTRA_TEXT, ann.getTitle() + "\n\n" + ann.getBody());
            startActivity(Intent.createChooser(share, "Share via"));
        });

        btnSaved.setOnClickListener(v ->
                android.widget.Toast.makeText(this, "Saved!", android.widget.Toast.LENGTH_SHORT).show());
    }
}
