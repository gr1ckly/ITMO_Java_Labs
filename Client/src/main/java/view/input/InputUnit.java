package view.input;

import commonModule.input.Inputable;
import controller.exceptions.RecursionException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Deque;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class InputUnit implements Inputable<Scanner, String> {
    private Deque<Scanner> scannerDeque;
    private Scanner currentScanner;
    private Deque<File> filesDeque;
    private InputMode mode;

    public InputUnit(InputMode mode, Scanner scanner){
        this.scannerDeque = new LinkedList<>();
        this.scannerDeque.addLast(scanner);
        this.currentScanner = this.scannerDeque.peekLast();
        this.filesDeque = new LinkedList<>();
        this.mode = mode;
    }

    @Override
    public String readln(){
        if (this.canRead()) {
            try {
                return this.currentScanner.nextLine();
            }catch (NoSuchElementException | NullPointerException e){
                this.mode = InputMode.STOP;
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
                this.mode = InputMode.STOP;
                return null;
            }
        }
        return null;
    }

    public InputMode getMode() {
        return mode;
    }

    @Override
    public boolean canRead(){
        if (this.mode.equals(InputMode.STOP)){
            return false;
        } else if (this.mode.equals(InputMode.CONSOLE) && this.currentScanner != null){
            return true;
        }else if(this.mode.equals(InputMode.SCRIPT) && this.currentScanner != null &&  this.currentScanner.hasNext()){
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

    public void readFile(File file) throws FileNotFoundException, RecursionException {
        if (this.filesDeque.contains(file)){
            throw new RecursionException("При попытке выполнения скрипта с файла " + this.filesDeque.peekLast().getName() + " обнаружена недопустимая рекурсия.");
        }else{
            filesDeque.addLast(file);
            scannerDeque.addLast(new Scanner(file));
            this.currentScanner = this.scannerDeque.peekLast();
            this.mode = InputMode.SCRIPT;
        }
    }

    @Override
    public boolean removeCurrentInputDevice() {
        if (this.scannerDeque.toArray().length != 0){
            this.scannerDeque.removeLast();
            this.currentScanner = this.scannerDeque.peekLast();
            if (this.mode == InputMode.SCRIPT){
                this.filesDeque.removeLast();
                if (this.filesDeque.size() == 0){
                    this.mode = InputMode.CONSOLE;
                }
            }
            return true;
        } else {
            this.mode = InputMode.STOP;
        }
        return false;
    }

    public void setMode(InputMode mode) {
        this.mode = mode;
    }

    public void clear(){
        this.filesDeque.clear();
        this.scannerDeque.clear();
        this.currentScanner = null;
    }

    @Override
    public void stopInput() {
        this.mode = InputMode.STOP;
    }

    public Deque<Scanner> getScannerDeque() {
        return scannerDeque;
    }
}
