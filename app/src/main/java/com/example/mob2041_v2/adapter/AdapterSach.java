package com.example.mob2041_v2.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mob2041_v2.R;
import com.example.mob2041_v2.dao.SachDAO;
import com.example.mob2041_v2.model.ModelLoaisach;
import com.example.mob2041_v2.model.ModelSach;

import java.util.ArrayList;

public class AdapterSach extends RecyclerView.Adapter<AdapterSach.ViewHolder>{
    private Context c;
    private ArrayList<ModelSach> list;
    private SachDAO sachDAO;

    public AdapterSach(Context c, ArrayList<ModelSach> list,SachDAO sachDAO) {
        this.c = c;
        this.list = list;
        this.sachDAO=sachDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)c).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_sach,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtmasach.setText("#ID: "+list.get(position).getMasach());
        holder.txttensach.setText(list.get(position).getTensach());
        holder.txtgia.setText("Giá : "+list.get(position).getGiaban());
        holder.txttacgia.setText(list.get(position).getTacgia());
        holder.txttheloai.setText(list.get(position).getTenloai());

        holder.btnedt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialoguppdate(list.get(holder.getAdapterPosition()));
            }
        });

        holder.btndele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(c);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có chắc chắn muốn xóa thể loại " + list.get(holder.getAdapterPosition()).getTenloai()+ " không ?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int check = sachDAO.xoasach(list.get(holder.getAdapterPosition()).getMasach());
                        switch (check){
                            case -1:
                                Toast.makeText(c, "Xóa thất bại!!", Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                Toast.makeText(c, "Xóa thành công!!", Toast.LENGTH_SHORT).show();
                                loaddataupdate();
                                break;
                        }
                    }
                });

                builder.setNegativeButton("Không",null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();


            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txttensach,txttacgia,txtmasach,txttheloai,txtgia;
        ImageView btnedt,btndele;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txttensach=itemView.findViewById(R.id.tensach);
            txttacgia=itemView.findViewById(R.id.tacgia);
            txtmasach=itemView.findViewById(R.id.masach);
            txttheloai=itemView.findViewById(R.id.theloai);
            txtgia=itemView.findViewById(R.id.gia);
            btnedt=itemView.findViewById(R.id.btnedt1);
            btndele=itemView.findViewById(R.id.btndele1);


        }
    }

    private void showdialoguppdate(ModelSach modelSach){
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        LayoutInflater inflater = ((Activity)c).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_sach,null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.show();

        TextView textView = view.findViewById(R.id.txttitle);

        EditText edttensach=view.findViewById(R.id.edttensach);
        EditText edttacgia=view.findViewById(R.id.edttacgia);
        EditText edtgia=view.findViewById(R.id.edtgia);
        EditText edtmaloai=view.findViewById(R.id.edtmaloai);

        Button btnsave=view.findViewById(R.id.btnsave);
        Button btncancel=view.findViewById(R.id.btncancel);

        textView.setText("Cập nhật thông tin");
        btnsave.setText("CẬP NHẬT");
        edttensach.setText(modelSach.getTensach());
        edttacgia.setText(modelSach.getTacgia());
        edtgia.setText(String.valueOf(modelSach.getGiaban()));
        edtmaloai.setText(String.valueOf(modelSach.getMaloai()));

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tensach=edttensach.getText().toString();
                String tacgia=edttacgia.getText().toString();
                String gia=edtgia.getText().toString();
                String maloai=edtmaloai.getText().toString();

                ModelSach modelSach1 =new ModelSach(modelSach.getMasach(),tensach,tacgia,Integer.valueOf(gia),Integer.valueOf(maloai));
                boolean check = sachDAO.suasach(modelSach1);

                if (check){
                    Toast.makeText(c, "Cập nhập thành công!!", Toast.LENGTH_SHORT).show();
                    loaddataupdate();
                    alertDialog.dismiss();
                }else {
                    Toast.makeText(c, "Cập nhật thất bại!!", Toast.LENGTH_SHORT).show();
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

    private void loaddataupdate(){
        list.clear();
        list = sachDAO.getlistsach();
        notifyDataSetChanged();
    }
}
