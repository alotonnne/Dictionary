package dictionaryAction;

import CommandLine.Word;

public class Meanings extends Word {
    private String pronunciation;
    private String attribute;
    private String explain;
    private String target;
    public Meanings(String pronunciation, String attribute, String listWordtrans, String Key){
        this.pronunciation = pronunciation;
        this.attribute = attribute;
        this.explain = listWordtrans;
        this.target = Key;

    }

    public String getPronunciation(){
        return this.pronunciation;
    }

    public String getAttribute(){
        return this.attribute;
    }

    public String getlistWordtrans(){
        return this.explain;
    }

    public String getKeyWord(){
        return this.target;
    }

    public void setPronunciation(String pronunciation){
        this.pronunciation = pronunciation;
    }

    public void setAttribute(String attribute){
        this.attribute= attribute;
    }

    public void setListWord(String listword){
        this.explain = listword;
    }

    public void setKeyWord(String Key){
        this.target = Key;
    }

}
