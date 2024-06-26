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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mob2041_v2.R;
import com.example.mob2041_v2.dao.LoaisachDAO;
import com.example.mob2041_v2.model.ModelLoaisach;

import java.util.ArrayList;

public class AdapterLoaisach extends RecyclerView.Adapter<AdapterLoaisach.Viewholder>{

    private Context c;
    private ArrayList<ModelLoaisach> list;
    private LoaisachDAO loaisachDAO;

    public AdapterLoaisach(Context c, ArrayList<ModelLoaisach> list,LoaisachDAO loaisachDAO) {
        this.c = c;
        this.list = list;
        this.loaisachDAO = loaisachDAO;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=((Activity)c).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_loaisach,parent,false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        //settext cho Textview
        holder.tenloai.setText("Thể loại : "+"\n"+list.get(position).getTenloai());
        holder.maloai.setText("Mã loại : "+list.get(position).getMaloai());

        //Bat su kien click cho Framelayout

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
                        int check = loaisachDAO.xoaloaisach(list.get(holder.getAdapterPosition()).getMaloai());
                        switch (check){
                            case -1:
                                Toast.makeText(c, "Xóa thất bại!!", Toast.LENGTH_SHORT).show();
                                break;
                            case 0:
                                Toast.makeText(c, "Bạn cần xóa các cuốn sách có thể loại này trước!!", Toast.LENGTH_SHORT).show();
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

    public class Viewholder extends RecyclerView.ViewHolder{
        TextView tenloai,maloai;
        ImageView btnedt,btndele;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            tenloai=itemView.findViewById(R.id.tenloai);
            maloai=itemView.findViewById(R.id.maloai);
            btnedt=itemView.findViewById(R.id.btnedt1);
            btndele=itemView.findViewById(R.id.btndele1);

        }
    }

    private void showdialoguppdate(ModelLoaisach modelLoaisach){
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        LayoutInflater inflater = ((Activity)c).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_loaisach,null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.show();

        TextView textView = view.findViewById(R.id.txttitle);
        EditText edttenloai=view.findViewById(R.id.edttenloai);
        Button btnsave=view.findViewById(R.id.btnsave);
        Button btncancel=view.findViewById(R.id.btncancel);

        textView.setText("Cập nhật thông tin");
        btnsave.setText("CẬP NHẬT");
        edttenloai.setText(modelLoaisach.getTenloai());

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenloai=edttenloai.getText().toString();
                ModelLoaisach modelLoaisach1 =new ModelLoaisach(modelLoaisach.getMaloai(),tenloai);
                boolean check = loaisachDAO.sualoaisach(modelLoaisach1);
                
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
        list = loaisachDAO.getdslaoisach();
        notifyDataSetChanged();
    }
}
