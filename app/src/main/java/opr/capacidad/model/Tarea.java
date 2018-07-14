package opr.capacidad.model;

import android.graphics.Bitmap;

public class Tarea {

    private String tittle;
    private String consigna;
    private Bitmap image1;
    private Bitmap image2;
    private Bitmap image3;
    private int rightChoice;

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getConsigna() {
        return consigna;
    }

    public void setConsigna(String consigna) {
        this.consigna = consigna;
    }

    public Bitmap getImage1() {
        return image1;
    }

    public void setImage1(Bitmap image1) {
        this.image1 = image1;
    }

    public Bitmap getImage2() {
        return image2;
    }

    public void setImage2(Bitmap image2) {
        this.image2 = image2;
    }

    public Bitmap getImage3() {
        return image3;
    }

    public void setImage3(Bitmap image3) {
        this.image3 = image3;
    }

    public int getRightChoice() {
        return rightChoice;
    }

    public void setRightChoice(int rightChoice) {
        this.rightChoice = rightChoice;
    }

    public Tarea() {

    }

    public void getDataById(int id) {
        this.tittle = "Título";
        this.consigna = "Consigna";
        this.image1 = null;
        this.image2 = null;
        this.image3 = null;
        this.rightChoice = 2;
    }

    public void setTareaCompletada(int tiempo) {

    }
}
