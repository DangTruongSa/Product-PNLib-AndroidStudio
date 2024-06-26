package com.example.mob2041_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mob2041_v2.dao.NguoidungDAO;
import com.example.mob2041_v2.model.ModelNguoidung;
import com.google.android.material.textfield.TextInputEditText;

public class DangkyAc extends AppCompatActivity {

    private NguoidungDAO nguoidungDAO;
    ImageView imageView;
    TextInputEditText user,pass,name,phone,adress,repass;
    Button btnsignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);

        imageView=findViewById(R.id.imageView);
        user=findViewById(R.id.edtusersignup);
        pass=findViewById(R.id.edtpasssignup);
        name=findViewById(R.id.edtnamesignup);
        phone=findViewById(R.id.edtphonesignup);
        adress=findViewById(R.id.edtadresssignup);
        btnsignup=findViewById(R.id.button4);
        repass=findViewById(R.id.edtrepasssignup);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DangkyAc.this, WellcomeAc.class));
                finish();
            }
        });

        nguoidungDAO=new NguoidungDAO(this);

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String password=pass.getText().toString();
                String repassword=repass.getText().toString();

                if (!password.equals(repassword)){
                    Toast.makeText(DangkyAc.this, "Mật khẩu không trùng nhau!!", Toast.LENGTH_SHORT).show();
                }else {
                    String username= user.getText().toString();
                    String nameuser=name.getText().toString();
                    String phoneuser=phone.getText().toString();
                    String addressuser=adress.getText().toString();

                    ModelNguoidung modelNguoidung = new ModelNguoidung(nameuser,phoneuser,addressuser,username,password);
                    boolean check = nguoidungDAO.signupaccount(modelNguoidung);
                    if (check){
                        Toast.makeText(DangkyAc.this, "Đăng ký thành công!!", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(DangkyAc.this, "Đăng ký thất bại!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}