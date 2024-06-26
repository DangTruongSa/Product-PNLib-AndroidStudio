package com.example.mob2041_v2.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mob2041_v2.R;
import com.example.mob2041_v2.adapter.AdapterSach;
import com.example.mob2041_v2.dao.SachDAO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FragmentSach extends Fragment {
    private RecyclerView recyclerView;
    private SachDAO sachDAO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragmnet_sach,container,false);

        recyclerView=view.findViewById(R.id.rcyvsach);
        FloatingActionButton floatingActionButton=view.findViewById(R.id.floataddsach);

        sachDAO = new SachDAO(getContext());

        loadData();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialogadd();
            }
        });

        return view;
    }

    private void loadData(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        AdapterSach adapterSach = new AdapterSach(getContext(),sachDAO.getlistsach(),sachDAO);
        recyclerView.setAdapter(adapterSach);
    }

    private void showdialogadd(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_sach,null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.show();

        //ánh xạ
        EditText edttensach=view.findViewById(R.id.edttensach);
        EditText edttacgia=view.findViewById(R.id.edttacgia);
        EditText edtgia=view.findViewById(R.id.edtgia);
        EditText edtmaloai=view.findViewById(R.id.edtmaloai);


        Button btnsave=view.findViewById(R.id.btnsave);
        Button btncancel=view.findViewById(R.id.btncancel);

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tensach=edttensach.getText().toString();
                String tacgia=edttacgia.getText().toString();
                String gia=edtgia.getText().toString();
                String maloai=edtmaloai.getText().toString();

                if (tensach.equals("") | tacgia.equals("") | gia.equals("") | maloai.equals("")){
                    Toast.makeText(getContext(), "Hãy nhập đầy đủ thông tin!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean check = sachDAO.themsach(tensach,tacgia,Integer.valueOf(gia),Integer.valueOf(maloai));

                if (check){

                    Toast.makeText(getContext(), "Thêm thành công!!", Toast.LENGTH_SHORT).show();
                    loadData();
                    alertDialog.dismiss();
                }else {
                    Toast.makeText(getContext(), "Thêm thất bại!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }
}
