package com.example.model;

import java.io.UnsupportedEncodingException;

/**
 * Created by Z on 2015 08 02.
 */
public class ReceiptLine {
    private String tantoID;
    private String goodsTitle;
    private String kosu;
    private String time;
    private String receiptNo;
    private String tableNO;

    public String getTantoID() {
        return tantoID;
    }

    public void setTantoID(String tantoID) {
        this.tantoID = tantoID;
    }

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    public String getKosu() {
        return kosu;
    }

    public void setKosu(String kosu) {
        this.kosu = kosu;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public String getTableNO() {
        return tableNO;
    }

    public void setTableNO(String tableNO) {
        this.tableNO = tableNO;
    }

    public ReceiptLine(String tantoID, String goodsTitle, String kosu, String time, String receiptNo, String tableNO) {
        this.tantoID = tantoID;
        try {
            this.goodsTitle = new String(goodsTitle.getBytes(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        this.kosu = kosu;
        this.time = time;
        this.receiptNo = receiptNo;
        this.tableNO = tableNO;
    }
}
