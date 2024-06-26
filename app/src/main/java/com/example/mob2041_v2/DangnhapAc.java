package com.example.mob2041_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mob2041_v2.dao.NguoidungDAO;
import com.google.android.material.textfield.TextInputEditText;

public class DangnhapAc extends AppCompatActivity {
    private NguoidungDAO nguoidungDAO;
    ImageView imageView;
    TextInputEditText edtuser,edtpass;
    Button btnlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);

        imageView=findViewById(R.id.imageView);
        edtuser=findViewById(R.id.edtuser);
        edtpass=findViewById(R.id.edtpass);
        btnlogin=findViewById(R.id.button4);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DangnhapAc.this,WellcomeAc.class));
                finish();
            }
        });

        nguoidungDAO = new NguoidungDAO(this);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user=edtuser.getText().toString();
                String pass=edtpass.getText().toString();

                boolean check = nguoidungDAO.checklogin(user,pass);
                if (check){
                    startActivity(new Intent(DangnhapAc.this, MainActivity.class));
                    finish();
                }else {
                    Toast.makeText(DangnhapAc.this, "Tên đăng nhập hoặc mật khẩu sai!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}