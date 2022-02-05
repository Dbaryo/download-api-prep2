package com.david.model.execptions;

public class DownloadNotFoundException extends Exception{

    private String msg;

    public DownloadNotFoundException() {
        this.msg = "Download was not found";
    }

    public DownloadNotFoundException(String name, String location){
        this.msg = name + ", " + location + " was not found";
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
