package com.company;

import jdk.net.Sockets;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) throws IOException {
	// write your code here
        ServerSocket ss = new ServerSocket(8000);
        while (true) {

            Socket s = ss.accept();
            OutputStream os = s.getOutputStream();
            PrintStream out = new PrintStream(os);
            out.println("HTTP/1.0 200 OK");
            out.println("Content-type: text/html");
            out.println();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            out.println("now: " + dtf.format(now));
            out.println("<meta http-equiv=\"Refresh\" content=\"1; URL=http://localhost:8000\" />");
            s.close();
        }
        //

    }

}
