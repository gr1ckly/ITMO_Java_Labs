package server;

import commonModule.input.Inputable;
import server.ServerMode;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Deque;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class InputUnit implements Inputable<Scanner, String> {
    private Deque<Scanner> scannerDeque;
    private Scanner currentScanner;
    private ServerMode mode;

    public InputUnit(ServerMode mode, Scanner scanner){
        this.scannerDeque = new LinkedList<>();
        this.scannerDeque.addLast(scanner);
        this.currentScanner = this.scannerDeque.peekLast();
        this.mode = mode;
    }

    @Override
    public String readln(){
        if (this.canRead()) {
            try {
                return this.currentScanner.nextLine();
            }catch (NoSuchElementException | NullPointerException e){
                this.mode = ServerMode.STOP;
                return null;
            }
        }
        return null;
    }

    @Override
    public String read() {
        if (this.canRead()) {
            try {
                return this.currentScanner.nextLine();
            }catch (NoSuchElementException | NullPointerException e){
                this.mode = ServerMode.STOP;
                return null;
            }
        }
        return null;
    }

    public ServerMode getMode() {
        return mode;
    }

    @Override
    public boolean canRead(){
        if (this.mode.equals(ServerMode.STOP)){
            return false;
        } else if (this.mode.equals(ServerMode.CONSOLE) && this.currentScanner != null){
            return true;
        }
        return false;
    }

    @Override
    public Scanner getCurrentInputDevice() {
        return this.currentScanner;
    }

    @Override
    public boolean addInputDevice(Scanner scanner) {
        if (!this.scannerDeque.contains(scanner)){
            this.scannerDeque.addLast(scanner);
            this.currentScanner = this.scannerDeque.peekLast();
        }
        return false;
    }

    @Override
    public boolean removeCurrentInputDevice() {
        if (this.scannerDeque.toArray().length != 0){
            this.scannerDeque.removeLast();
            this.currentScanner = this.scannerDeque.peekLast();
            return true;
        } else {
            this.mode = ServerMode.STOP;
        }
        return false;
    }

    public void setMode(ServerMode mode) {
        this.mode = mode;
    }

    public void clear(){
        this.scannerDeque.clear();
        this.currentScanner = null;
    }

    @Override
    public void stopInput() {
        this.mode = ServerMode.STOP;
    }

    public Deque<Scanner> getScannerDeque() {
        return scannerDeque;
    }
}
