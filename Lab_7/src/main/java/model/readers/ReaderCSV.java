package model.readers;

import validation.exceptions.CSVInvalidTitleException;
import validation.exceptions.CSVNotTitleException;
import model.parsers.ParserCSV;
import validation.CSVValidator;
import server.OutputUnit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Класс-наслденик {@link Reader}, реализующий чтение данных из csv-документа.
 */
public class ReaderCSV implements Reader<List, File> {
    private Class Tclass;
    private CSVValidator validator;
    private String separator;
    private ParserCSV parser;
    private OutputUnit output;
    private int lineNumber;
    public ReaderCSV(Class Tclass, OutputUnit output, String separator){
        this.Tclass = Tclass;
        this.separator = separator;
        this.parser = new ParserCSV(separator, output);
        this.validator = new CSVValidator(separator);
        this.output = output;
    }

    /**
     * Метод, реализующий создание коллекции по информации из файла.
     * @param file - {@link File}, из которого необходимо загрузить коллекцию.
     * @return {@link LinkedList} - загруженная коллекция.
     * @throws IOException - выбрасывается, если не удается получить доступ к файлу с коллекцией.
     * @throws CSVNotTitleException - выбрасыватся, если в csv-документе нет заголовка в формате csv.
     * @throws CSVInvalidTitleException - выбрасывается, если в документе неверный заголовок для загрузки заданных объектов.
     */
    @Override
    public List read(File file) throws IOException, CSVNotTitleException, CSVInvalidTitleException {
        List elementsList = new LinkedList<>();
        try {
            FileReader fileReader = new FileReader(file);
            this.lineNumber = 0;
            String line = this.readln(fileReader);
            if (line == null){
                throw new CSVNotTitleException(file);
            }
            lineNumber += 1;
            String title = line.trim();
            String[] titleArray = title.split(this.separator);
            if (this.validator.isTitleValid(title, this.Tclass)) {
                line = this.readln(fileReader);
                lineNumber +=1 ;
                while (line != null) {
                    line = line.trim();
                    if (!this.validator.isValid(title, line)) {
                        this.output.writeln("Неверное количество столбцов в " + lineNumber + " строке.");
                    } else {
                        String[] lineArray = line.split("(?<!/)" + this.separator);
                        LinkedHashMap<String, String> formForParser = new LinkedHashMap<>();
                        for (int i = 0; i < lineArray.length; i++) {
                            formForParser.put(titleArray[i].trim(), lineArray[i].trim().replace("/" + this.separator, this.separator));
                        }
                        elementsList.add(this.parser.parse(formForParser, this.Tclass));
                        if (elementsList.get(elementsList.size()-1)==null){
                            elementsList.remove(elementsList.size() - 1);
                        }
                    }
                    line = this.readln(fileReader);
                    lineNumber += 1;
                }
            }else{
                throw new CSVInvalidTitleException(file);
            }
        } catch (FileNotFoundException e) {
            return elementsList;
        }
        return elementsList;
    }

    public String readln(FileReader fileReader) throws IOException{
        int character = fileReader.read();
        if (character != -1){
            String line = "";
            StringBuilder builder = new StringBuilder();
            while (character != -1){
                char sign = (char) character;
                if (!Character.toString(sign).equals("\n")) {
                    builder.append(Character.toString(sign));
                }else{
                    break;
                }
                character = fileReader.read();
            }
            return builder.toString();
        }else{
            return null;
        }
    }
}
