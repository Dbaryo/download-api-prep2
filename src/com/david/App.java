package com.david;

import com.david.model.DownloadSystem;

import java.util.Random;
import java.util.Scanner;

public class App {

    private static final int MAX_SIZE = 100000;

    public static void main(String[] args) {
        while (DownloadSystem.getInstance().isRunning()) {
            DownloadSystem.getInstance().printMenu();
            final Scanner scanner = new Scanner(System.in);
            final int cmd = scanner.nextInt();
            switch (cmd) {
                case 1:
                    initDownload();
                    break;
                case 2:
                    abortDownload();
                    break;
                case 3:
                    reactivateDownload();
                    break;
                case 4:
                    DownloadSystem.getInstance().viewDownloads();
                    break;
                case 5:
                    DownloadSystem.getInstance().setRunning(false);
                    DownloadSystem.getInstance().kill();

            }
        }
    }

    public static void initDownload() {
        final Scanner scanner = new Scanner(System.in);
        System.out.print("What's the file name?");
        final String fileName = scanner.next();
        System.out.print("What's the file location? (url)");
        final String url = scanner.next();
        final double size = new Random().nextInt(MAX_SIZE) * 1.0;
        DownloadSystem.getInstance().startDownload(fileName, url, size);
    }

    public static void abortDownload() {
        final Scanner scanner = new Scanner(System.in);
        System.out.print("What's the file name?");
        final String fileName = scanner.next();
        System.out.print("What's the file location? (url)");
        final String url = scanner.next();
        DownloadSystem.getInstance().stopDownload(fileName, url);
    }

    public static void reactivateDownload() {
        final Scanner scanner = new Scanner(System.in);
        System.out.print("What's the file name?");
        final String fileName = scanner.next();
        System.out.print("What's the file location? (url)");
        final String url = scanner.next();
        DownloadSystem.getInstance().resumeDownload(fileName, url);
    }
}
