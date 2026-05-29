package com.lguconnect.admin;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.lguconnect.R;
import com.lguconnect.data.Repository;
import com.lguconnect.models.Announcement;

import java.util.UUID;

public class PostAnnouncementActivity extends AppCompatActivity {
    private String editId;
    private static final String[] CATEGORIES = {"Emergency","Education","Health","Events","Weather","Public Advisory"};
    private static final String[] COLORS     = {"#D32F2F","#8E24AA","#388E3C","#F4511E","#1E88E5","#607D8B"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_announcement);

        TextInputEditText etTitle    = findViewById(R.id.etTitle);
        TextInputEditText etBody     = findViewById(R.id.etBody);
        AutoCompleteTextView spinCat = findViewById(R.id.spinnerCategory);
        SwitchMaterial swEmergency   = findViewById(R.id.switchEmergency);
        SwitchMaterial swPushNotif   = findViewById(R.id.switchPushNotif);
        MaterialButton btnPickImage  = findViewById(R.id.btnPickImage);
        MaterialButton btnSubmit     = findViewById(R.id.btnSubmitAnnouncement);
        MaterialButton btnBack       = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> finish());

        ArrayAdapter<String> catAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, CATEGORIES);
        spinCat.setAdapter(catAdapter);
        spinCat.setText(CATEGORIES[0], false);

        btnPickImage.setOnClickListener(v ->
                Toast.makeText(this, "Image picker coming soon", Toast.LENGTH_SHORT).show());

        editId = getIntent().getStringExtra("editId");
        if (!TextUtils.isEmpty(editId)) {
            btnSubmit.setText("Update Announcement");
            Announcement existing = Repository.get().getAnnouncementById(editId);
            if (existing != null) {
                etTitle.setText(existing.getTitle());
                etBody.setText(existing.getBody());
                spinCat.setText(existing.getCategory(), false);
                swEmergency.setChecked(existing.isEmergency());
            }
        }

        btnSubmit.setOnClickListener(v -> {
            String title    = etTitle.getText() == null ? "" : etTitle.getText().toString().trim();
            String body     = etBody.getText() == null ? "" : etBody.getText().toString().trim();
            String category = spinCat.getText().toString().trim();
            boolean emerg   = swEmergency.isChecked();

            if (TextUtils.isEmpty(title) || TextUtils.isEmpty(body)) {
                Toast.makeText(this, "Title and description are required", Toast.LENGTH_SHORT).show();
                return;
            }

            String color = colorFor(category);
            if (TextUtils.isEmpty(editId)) {
                Announcement ann = new Announcement(
                        UUID.randomUUID().toString(), title, body, category,
                        color, emerg, System.currentTimeMillis(), "Admin",
                        Announcement.Status.PUBLISHED);
                Repository.get().addAnnouncement(ann);
                Toast.makeText(this, "Announcement published!", Toast.LENGTH_SHORT).show();
            } else {
                Announcement existing = Repository.get().getAnnouncementById(editId);
                if (existing != null) {
                    existing.setTitle(title); existing.setBody(body);
                    existing.setCategory(category); existing.setCategoryColor(color);
                    existing.setEmergency(emerg);
                    Repository.get().updateAnnouncement(existing);
                    Toast.makeText(this, "Announcement updated!", Toast.LENGTH_SHORT).show();
                }
            }
            finish();
        });
    }

    private String colorFor(String cat) {
        for (int i = 0; i < CATEGORIES.length; i++)
            if (CATEGORIES[i].equalsIgnoreCase(cat)) return COLORS[i];
        return "#607D8B";
    }
}
