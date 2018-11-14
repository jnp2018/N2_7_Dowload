/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phan1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class dowload_D1 {

    private static final int BUFFER_SIZE = 4096;

    public static void main(String[] args) {
        String fileURL = "";
        String save = "";
        ArrayList<String> list = new ArrayList<>();
        Scanner n = new Scanner(System.in);
        System.out.println("so file muon dow :");
        int x = n.nextInt();
        while (x-- >= 1){
            System.out.print("FILE" + x + ":");
            fileURL = n.next();
            list.add(fileURL);
        }

        save = "C:\\Users\\Admin\\Desktop";
        try {
            System.out.println("Dang tai ....");
            for (String url : list) {
                new Thread(new DownloadTask(url, save)).start();
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void downloadFile(String fileURL, String save)
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
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
         
            Thread.sleep(2500);
            outputStream.close();
            inputStream.close();

            System.out.println("Tai hoan tat");
        } else {
            System.out.println("Tai that bai. Server HTTP code: " + responseCode);
        }
        httpConn.disconnect();
    }

    private static class DownloadTask extends Thread {

        private String name;
        private String toPath;

        public DownloadTask(String name, String toPath) {
            this.name = name;
            this.toPath = toPath;
        }

        @Override
        public void run() {
            try {
                downloadFile(name, toPath);
            } catch (Exception ex) {
                Logger.getLogger(dowload_D1.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
//zip file:https://stackjava.com/wp-content/uploads/2018/05/SpringBootDownloadFile.zip
//file anh:http://fbapp.itcuties.com/middle/_DSC4796.jpg
//Nếu quá trình chạy thành công với 2 file trên thì se hiển thi tải hoàn tất cùng lúc
