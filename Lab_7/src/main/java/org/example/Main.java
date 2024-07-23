package org.example;

import commonModule.collectionElements.SpaceMarine;
import model.CollectionStorage;
import server.InputUnit;
import server.OutputUnit;
import server.ServerApp;
import server.network.Server;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int port = 12345;
        if (args.length >=1){
            try {
                port = Integer.parseInt(args[0]);
                if (port <= 1024){
                    port = 12345;
                }
            }catch (NumberFormatException e){
            }
        }
        Server.setLocalPort(port);
        ServerApp serverApp = null;
        try {
            serverApp = new ServerApp();
            try {
                serverApp.launch();
            } catch (IOException e) {
                serverApp.stop();
            } catch (ClassNotFoundException e) {
                serverApp.stop();
            }
        } catch (IOException e) {
        }
    }
}