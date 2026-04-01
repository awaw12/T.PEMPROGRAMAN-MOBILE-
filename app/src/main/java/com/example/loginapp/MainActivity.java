package com.example.loginapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.*;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etNama, etEmail, etPassword, etConfirm;
    Button btnSubmit;
    Spinner spinner;
    RadioGroup rgGender;
    CheckBox cb1, cb2, cb3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // INIT
        etNama = findViewById(R.id.etNama);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirm = findViewById(R.id.etConfirm);
        btnSubmit = findViewById(R.id.btnSubmit);
        spinner = findViewById(R.id.spinner);
        rgGender = findViewById(R.id.rgGender);
        cb1 = findViewById(R.id.cb1);
        cb2 = findViewById(R.id.cb2);
        cb3 = findViewById(R.id.cb3);

        // =====================
        // REAL-TIME EMAIL VALIDATION
        // =====================
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().contains("@")){
                    etEmail.setError("Email tidak valid");
                } else {
                    etEmail.setError(null);
                }
            }

            @Override public void afterTextChanged(Editable s) {}
        });

        // =====================
        // REAL-TIME CONFIRM PASSWORD
        // =====================
        etConfirm.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pass = etPassword.getText().toString();
                String confirm = etConfirm.getText().toString();

                if(!confirm.equals(pass)){
                    etConfirm.setError("Password tidak sama");
                } else {
                    etConfirm.setError(null);
                }
            }

            @Override public void afterTextChanged(Editable s) {}
        });

        // =====================
        // SPINNER
        // =====================
        String[] kota = {"Pilih Kota", "Bandung", "Jakarta", "Surabaya"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, kota);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // =====================
        // BUTTON SUBMIT
        // =====================
        btnSubmit.setOnClickListener(v -> {

            String nama = etNama.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String pass = etPassword.getText().toString().trim();
            String confirm = etConfirm.getText().toString().trim();

            String emailBenar = "admin@gmail.com";
            String passwordBenar = "1234";

            int gender = rgGender.getCheckedRadioButtonId();

            int hobi = 0;
            if(cb1.isChecked()) hobi++;
            if(cb2.isChecked()) hobi++;
            if(cb3.isChecked()) hobi++;

            String kotaDipilih = spinner.getSelectedItem().toString();

            // VALIDASI
            if(nama.isEmpty()){
                etNama.setError("Nama wajib diisi");
            } else if(email.isEmpty()){
                etEmail.setError("Email wajib diisi");
            } else if(pass.isEmpty()){
                etPassword.setError("Password wajib diisi");
            } else if(confirm.isEmpty()){
                etConfirm.setError("Confirm password wajib diisi");
            } else if(!pass.equals(confirm)){
                etConfirm.setError("Password tidak sama");
            } else if(!email.equals(emailBenar) || !pass.equals(passwordBenar)){
                Toast.makeText(this, "Login gagal!", Toast.LENGTH_SHORT).show();
            } else if(gender == -1){
                Toast.makeText(this, "Pilih jenis kelamin", Toast.LENGTH_SHORT).show();
            } else if(hobi < 1){
                Toast.makeText(this, "Pilih minimal 1 hobi", Toast.LENGTH_SHORT).show();
            } else if(kotaDipilih.equals("Pilih Kota")){
                Toast.makeText(this, "Pilih kota", Toast.LENGTH_SHORT).show();
            } else {

                new AlertDialog.Builder(this)
                        .setTitle("Konfirmasi")
                        .setMessage("Apakah data ini sudah benar?")
                        .setPositiveButton("Ya", (dialog, which) -> {

                            Toast.makeText(this, "Login Berhasil!", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                            startActivity(intent);

                        })
                        .setNegativeButton("Tidak", null)
                        .show();
            }
        });

        // =====================
        // LONG PRESS
        // =====================
        btnSubmit.setOnLongClickListener(v -> {
            Toast.makeText(this, "Long Press aktif!", Toast.LENGTH_SHORT).show();
            return true;
        });
    }
}