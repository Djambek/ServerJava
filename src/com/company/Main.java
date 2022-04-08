package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        // write your code here
        ServerSocket ss = new ServerSocket(8000);
        String filepath;
        while (true) {
            Socket s = ss.accept();
            OutputStream os = s.getOutputStream();
            PrintStream out = new PrintStream(os);
            Scanner sc = new Scanner(s.getInputStream());
            String line = sc.nextLine();

            filepath = getRequestedFilePath(line);
            sendfile(filepath.substring(1), out);

            //out.println("<meta http-equiv=\"Refresh\" content=\"1; URL=http://localhost:8000\" />");
            s.close();
        }
        //

    }

    static String getRequestedFilePath(String line) {
        return line.split(" ")[1];
    }

    static void sendfile(String filepath, PrintStream out){
        System.out.println(filepath);
        File file = new File(filepath);
        if (file.isDirectory()){
            sendfile(filepath.substring(1)+"index.html", out);
        }else{
            if (!file.exists()){
                send404(out);
            }else {
                printHeaders(getMimeType(filepath), out);
                try {
                    System.out.println("File found!");
                    FileInputStream fis = new FileInputStream(file);
                    byte[] data = fis.readAllBytes();
                    out.write(data);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    static void printHeaders(String mimeType, PrintStream out) {
        out.println("HTTP/1.0 200 OK");
        out.println("Connection: close");
        switch (mimeType){
            case "jpg":
                out.println("Content-type: image/jpg\n");
            case "html":
                out.println("Content-type: text/html\n");
            case "png":
                out.println("Content-type: image/png\n");
        }
    }

    static String getMimeType(String filepath) {
        return filepath.split("\\.")[1];
    }

    static void send404(PrintStream out) {
        out.println("HTTP/1.0 404 Not Found");
        out.println("Connection: close");
        out.println("Content-type: text/html\n");
        out.println("FILE NOT FOUND");

    }

}
