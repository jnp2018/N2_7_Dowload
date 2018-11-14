/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phan2.dowload;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.SwingWorker;

public class dowload_D1 extends SwingWorker<Void, Void> {

    dowload d;
    private static final int BUFFER_SIZE = 4096;
    private DownloadTask dow;
    int phantramhoantat = 0;


    public void download( String url, String save) {
            try {
                System.out.println("Dang tai ....");
                new Thread(new DownloadTask(url, save)).start();
            } catch (Exception ex) {

            }
       
         
    }

    public void downloadall(String urls, String save) {
        ArrayList<String> list = new ArrayList<>();

        Getphantu s = new Getphantu();
        list = s.getX(urls);
        try {
            System.out.println("Dang tai ....");
            for (String url : list) {
                new Thread(new DownloadTask(url, save)).start();
            }
        } catch (Exception ex) {

        }
    }

    public void downloadFile(String fileURL, String save)
            throws Exception, MalformedURLException {

        URL url = new URL(fileURL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            String fileName = "";
            String disposition = httpConn.getHeaderField("Content-Disposition");
            String contentType = httpConn.getContentType();
            if (disposition != null) {

                int index = disposition.indexOf("taixuong...");
                if (index > 0) {
                    fileName = disposition.substring(index + 10,
                            disposition.length() - 1);
                }
            } else {

                fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1,
                        fileURL.length());

            }
            System.out.println("Kieu file = " + contentType);
            System.out.println("Ten file = " + fileName);
            InputStream inputStream = httpConn.getInputStream();
            String saveFilePath = save + File.separator + fileName;
            FileOutputStream outputStream = new FileOutputStream(saveFilePath);
            int bytesRead = -1;
            byte[] buffer = new byte[BUFFER_SIZE];
            long tongbyte = 0;
            long fileSize = httpConn.getContentLength();
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
                tongbyte += bytesRead;
                phantramhoantat = (int) (tongbyte * 100 / fileSize);

                setProgress(phantramhoantat);
            }
            Thread.sleep(2500);
            outputStream.close();
            inputStream.close();
            System.out.println("tai xong");
        } else {
            System.out.println("Tai that bai. Server HTTP code: " + responseCode);
        }
        httpConn.disconnect();

    }

    @Override
    protected Void doInBackground() throws Exception {

        return null;

    }

    private class DownloadTask implements Runnable {

        private String name;
        private String toPath;
        private Thread th;


        public DownloadTask() {
        }

        public DownloadTask(String name, String toPath) {
            this.name = name;
            this.toPath = toPath;
        }

        @Override
        public void run() {
    
                try {
                    downloadFile(name, toPath);

                } catch (Exception ex) {
                }
            
        }

    }
}