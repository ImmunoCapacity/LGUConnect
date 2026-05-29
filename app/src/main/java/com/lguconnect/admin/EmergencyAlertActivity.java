package com.lguconnect.admin;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.lguconnect.R;

public class EmergencyAlertActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_alert);

        TextInputEditText etMessage = findViewById(R.id.etAlertMessage);
        CheckBox cbPush             = findViewById(R.id.cbPushNotif);
        CheckBox cbSms              = findViewById(R.id.cbSms);
        MaterialButton btnSend      = findViewById(R.id.btnSendAlert);
        MaterialButton btnBack      = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> finish());

        btnSend.setOnClickListener(v -> {
            String msg = etMessage.getText() == null ? "" : etMessage.getText().toString().trim();
            if (TextUtils.isEmpty(msg)) {
                Toast.makeText(this, "Alert message required", Toast.LENGTH_SHORT).show();
                return;
            }
            StringBuilder channels = new StringBuilder();
            if (cbPush.isChecked()) channels.append("Push ");
            if (cbSms.isChecked())  channels.append("SMS");
            Toast.makeText(this, "Emergency alert sent via: " + channels, Toast.LENGTH_LONG).show();
            finish();
        });
    }
}
