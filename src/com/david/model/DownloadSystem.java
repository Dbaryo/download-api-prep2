package com.david.model;

import com.david.model.execptions.DownloadNotFoundException;
import com.david.threads.DownloadTask;
import lombok.Data;
import lombok.ToString;


import java.util.Calendar;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@ToString

public class DownloadSystem {
    private DownloadSystem() {
        this.isRunning = true;
        inactiveDownloadKiller = new Thread(() -> {
            while (isRunning) {
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                downloads.removeIf(fD -> !fD.isActive());
            }
        });
        inactiveDownloadKiller.start();
    }

    private final static DownloadSystem instance = new DownloadSystem();

    public static DownloadSystem getInstance() {
        return instance;
    }

    private final Set<FakeDownload> downloads = new HashSet<>();
    private boolean isRunning;
    private final Thread inactiveDownloadKiller;

    public void printMenu() {
        System.out.println("Please choose an action: \n" +
                "1 - download file \n" +
                "2 - stop download \n" +
                "3 - restart download \n" +
                "4 - see all downloads \n" +
                "5 - exit");
    }

    public void startDownload(String name, String location, double size) {
        FakeDownload download = new FakeDownload(name, location, size);
        new DownloadTask(download).start();
        downloads.add(download);
    }

    public void stopDownload(String name, String location) {
        try {
            FakeDownload currentFD = findDownload(name, location);
            currentFD.setActive(false);
            currentFD.setFinishedTime(Calendar.getInstance());
            String percentage = String.format("%.2f", (currentFD.getCurrentData() * 100 / currentFD.getFileSize()));
            System.out.println(currentFD.getFileName() + " download was stopped by user, \n" +
                    "percentage of file download: " + percentage + "% " +
                    "\n at: \n" + currentFD.calToStringFinishedTime());
        } catch (DownloadNotFoundException dNFE) {
            System.out.println(dNFE.getMsg());
        }
    }

    public void resumeDownload(String name, String location) {
        try {
            FakeDownload currentFD = findDownload(name, location);
            if (currentFD.isActive()) {
                System.out.println(name + " is already active");
            } else {
                currentFD.setActive(true);
                new DownloadTask(currentFD).start();
                System.out.println(currentFD.getFileName() + " download was resumed by user, " +
                        "\n at: \n" + currentFD.calToStringFinishedTime());
            }
        } catch (DownloadNotFoundException dNFE) {
            System.out.println(dNFE.getMsg());
        }
    }

    public void viewDownloads() {
        System.out.println("Current downloads: \n");
        for (FakeDownload fD : downloads) {
            fD.downloadPrint();
        }
    }

    public FakeDownload findDownload(String name, String location) throws DownloadNotFoundException {
        FakeDownload wantedFD = null;
        for (FakeDownload fD : downloads) {
            if (Objects.equals(fD.getFileName(), name) && Objects.equals(fD.getFileLocation(), location)) {
                wantedFD = fD;
            }
        }
        if (wantedFD == null) {
            throw new DownloadNotFoundException(name, location);
        } else {

            return wantedFD;
        }
    }

    public void kill() {
        System.out.println("Thank you and Goodbye");
        inactiveDownloadKiller.stop();
    }
}
