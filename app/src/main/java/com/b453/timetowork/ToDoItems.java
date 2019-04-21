package com.b453.timetowork;

public class ToDoItems {
    private String titledoes, datedoes, descdoes, pridoes, keydoes;

    public ToDoItems() {
    }

    public ToDoItems(String titledoes, String datedoes, String descdoes, String pridoes, String keydoes) {
        this.titledoes = titledoes;
        this.datedoes = datedoes;
        this.descdoes = descdoes;
        this.pridoes = pridoes;
        this.keydoes = keydoes;
    }

    public String getTitledoes() {
        return titledoes;
    }

    public void setTitledoes(String titledoes) {
        this.titledoes = titledoes;
    }

    public String getDatedoes() {
        return datedoes;
    }

    public void setDatedoes(String datedoes) {
        this.datedoes = datedoes;
    }

    public String getDescdoes() {
        return descdoes;
    }

    public void setDescdoes(String descdoes) {
        this.descdoes = descdoes;
    }

    public String getPridoes() {
        return pridoes;
    }

    public void setPridoes(String pridoes) {
        this.pridoes = pridoes;
    }

    public String getKeydoes() {
        return keydoes;
    }

    public void setKeydoes(String keydoes) {
        this.keydoes = keydoes;
    }

}
