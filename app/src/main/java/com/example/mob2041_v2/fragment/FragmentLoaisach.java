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
import com.example.mob2041_v2.adapter.AdapterLoaisach;
import com.example.mob2041_v2.dao.LoaisachDAO;
import com.example.mob2041_v2.model.ModelLoaisach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FragmentLoaisach extends Fragment {
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private LoaisachDAO loaisachDAO;
    private ArrayList<ModelLoaisach> list;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_loaisach,container,false);

        //code
        recyclerView=view.findViewById(R.id.rcyvloaisach);
        floatingActionButton=view.findViewById(R.id.floatingbutton);

        loaisachDAO = new LoaisachDAO(getContext());


        //adapter
        loaddata();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialogadd();
            }
        });

        return view;
    }

    private void loaddata(){
        list = loaisachDAO.getdslaoisach();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        AdapterLoaisach adapterLoaisach=new AdapterLoaisach(getContext(),list,loaisachDAO);
        recyclerView.setAdapter(adapterLoaisach);
    }

    private void showdialogadd(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_loaisach,null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.show();

        //ánh xạ
        EditText edttenloai=view.findViewById(R.id.edttenloai);
        Button btnsave=view.findViewById(R.id.btnsave);
        Button btncancel=view.findViewById(R.id.btncancel);

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenloai = edttenloai.getText().toString();

                if (tenloai.equals("")){
                    Toast.makeText(getContext(), "Hãy nhập đầy đủ thông tin!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean check = loaisachDAO.themlaoisach(tenloai);

                if (check){

                    Toast.makeText(getContext(), "Thêm thành công!!", Toast.LENGTH_SHORT).show();
                    loaddata();
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
