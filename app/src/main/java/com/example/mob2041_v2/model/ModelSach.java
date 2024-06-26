package com.example.mob2041_v2.model;

public class ModelSach {
    private int masach;
    private String tensach;
    private String tacgia;
    private int giaban;
    private int maloai;
    private String tenloai;

    public ModelSach(String tensach, String tacgia, int giaban, int maloai) {
        this.tensach = tensach;
        this.tacgia = tacgia;
        this.giaban = giaban;
        this.maloai = maloai;
    }

    public ModelSach(int masach, String tensach, String tacgia, int giaban, int maloai, String tenloai) {
        this.masach = masach;
        this.tensach = tensach;
        this.tacgia = tacgia;
        this.giaban = giaban;
        this.maloai = maloai;
        this.tenloai = tenloai;
    }

    public ModelSach(int masach, String tensach, String tacgia, int gia, int maloai) {
    }

    public int getMasach() {
        return masach;
    }

    public void setMasach(int masach) {
        this.masach = masach;
    }

    public String getTensach() {
        return tensach;
    }

    public void setTensach(String tensach) {
        this.tensach = tensach;
    }

    public String getTacgia() {
        return tacgia;
    }

    public void setTacgia(String tacgia) {
        this.tacgia = tacgia;
    }

    public int getGiaban() {
        return giaban;
    }

    public void setGiaban(int giaban) {
        this.giaban = giaban;
    }

    public int getMaloai() {
        return maloai;
    }

    public void setMaloai(int maloai) {
        this.maloai = maloai;
    }

    public String getTenloai() {
        return tenloai;
    }

    public void setTenloai(String tenloai) {
        this.tenloai = tenloai;
    }
}

