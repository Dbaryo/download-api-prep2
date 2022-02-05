package com.david.model;

import lombok.Data;
import lombok.ToString;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Data

public class FakeDownload {
    public FakeDownload(String fileName, String fileLocation, double fileSize) {
        this.fileName = fileName;
        this.fileLocation = fileLocation;
        this.fileSize = fileSize;
        this.currentData = 0;
        this.isActive = true;
        this.finishedTime = null;
        //this.time = new SimpleDateFormat("dd MMM yyyy HH:mm:ss").format(finishedTime.getTime());
    }

    private final String fileName;
    private final String fileLocation;
    private final double fileSize;
    private double currentData;
    private boolean isActive;
    private Calendar finishedTime;

    public String calToStringFinishedTime() {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(finishedTime.getTime());
    }


    public void downloadPrint() {
        String time;
        if (isActive) {
            time = "N/A";
        } else {
            time = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(finishedTime.getTime());
        }
        System.out.println("Download: \n" +
                "fileName: '" + fileName + '\'' +
                ", fileLocation: '" + fileLocation + '\'' +
                ", fileSize: " + fileSize +
                ", currentData: " + currentData +
                ", isActive: " + isActive +
                ", finishedTime: " + time +
                "\n");

    }
}
