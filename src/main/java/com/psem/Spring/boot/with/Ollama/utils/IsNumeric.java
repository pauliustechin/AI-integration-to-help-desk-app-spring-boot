package com.psem.Spring.boot.with.Ollama.utils;

public class IsNumeric {

    String number;

    public IsNumeric(String number) {
        this.number = number;
    }

    public boolean isNumeric(){
        try{
            Integer.parseInt(number);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }
}
