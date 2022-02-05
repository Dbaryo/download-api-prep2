package com.david.threads;

import com.david.model.FakeDownload;


import java.util.Calendar;
import java.util.Random;

public class DownloadTask extends Thread {

    public DownloadTask(FakeDownload fakeDownload) {
        this.fakeDownload = fakeDownload;
    }

    private final FakeDownload fakeDownload;
    private final Random random = new Random();

    @Override
    public void run() {
        try {
            while (fakeDownload.isActive()) {
                fakeDownload.setCurrentData(fakeDownload.getCurrentData() + ((random.nextInt((int)fakeDownload.getFileSize()) * 0.1)));
                if (fakeDownload.getCurrentData() >= fakeDownload.getFileSize()){
                    fakeDownload.setCurrentData(fakeDownload.getFileSize());
                    fakeDownload.setActive(false);
                    fakeDownload.setFinishedTime(Calendar.getInstance());
                    System.out.println(fakeDownload.getFileName() + " was fully downloaded, " +
                            "\n and finished at: \n" + fakeDownload.calToStringFinishedTime());
                }else {
                    Thread.sleep((random.nextInt(5) + 1) * 1000);
                }
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
