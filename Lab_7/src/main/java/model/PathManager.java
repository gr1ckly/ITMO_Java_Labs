package model;

import validation.exceptions.InvalidFileNameException;
import server.ServerMode;
import server.InputUnit;
import server.OutputUnit;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Класс, управляющий получением файла/ссылки на файл.
 */
public class PathManager {
    private String path;
    private String pattern;
    private OutputUnit output;
    private InputUnit input;
    public PathManager(String path, OutputUnit output, InputUnit input){
        this.path = path;
        this.output = output;
        this.input = input;
        this.pattern = "^[\\w,\\s-]+\\.csv$";
    }

    /**
     * Метод, преверяющий строку на то, может ли она быть ссылкойна csv-документ
     * @param path - {@link String} строка которую нужно проверить.
     * @return ответ в формате boolean.
     */
    public boolean isValidByRegex(String path){
        return path.matches(this.pattern);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Метод, реализующий получение ссылки на файл из консоли.
     * @param message - сообщение, которое необходимо вывести в консоль.
     * @return {@link File}
     * @throws InvalidFileNameException - выбрасывается, если некорректное имя файла.
     * @throws FileNotFoundException - выбрасывается, если файл по ссылке не найден.
     */
    public File getFileFromConsole(String message) throws InvalidFileNameException, FileNotFoundException {
        if (this.input.getMode().equals(ServerMode.CONSOLE)){
            this.output.write(message);
            this.path = this.input.readln();
            if (this.path != null) {
                this.path = this.path.trim();
                if (this.isValidByRegex(this.path)) {
                    Path path = Paths.get(this.path);
                    if (Files.exists(path)) {
                        return new File(this.path);
                    } else {
                        throw new FileNotFoundException();
                    }
                } else {
                    throw new InvalidFileNameException();
                }
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

    /**
     * Метод, реализующий получение файла.
     * @param message - строка которую нужно будет выводить пользователю, если потребуется считывание с консоли.
     * @return {@link File}
     * @throws FileNotFoundException - выбрасывается, если файл по полученной ссылке не найден.
     * @throws InvalidFileNameException - выбрасывается, если некорректное имя файла.
     */
    public File getFile(String message) throws FileNotFoundException, InvalidFileNameException {
        if (this.path == null){
            File file  = this.getFileFromConsole(message);
            if (file == null){
                throw new FileNotFoundException();
            }else{
                return file;
            }
        }else{
            if (this.isValidByRegex(this.path)){
                Path path = Paths.get(this.path);
                if (Files.exists(path)){
                    return new File(this.path);
                }else{
                    File file  = this.getFileFromConsole(message);
                    if (file == null){
                        throw new FileNotFoundException();
                    }else{
                        return file;
                    }
                }
            }else{
                File file  = this.getFileFromConsole(message);
                if (file == null){
                    throw new FileNotFoundException();
                }else{
                    return file;
                }
            }
        }
    }
}
