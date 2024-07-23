package model.writers;

import model.converters.ConverterCSV;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Класс-наследник {@link Writer}, реализующий запись данных в csv-документ
 * @param <T>
 */
public class WriterCSV<T> implements Writer<List<T>, Boolean> {
    private ConverterCSV converter;
    private File file;
    private Class Tclass;
    public WriterCSV(ConverterCSV converter, File file, Class Tclass){
        this.converter = converter;
        this.file = file;
        this.Tclass = Tclass;
    }

    /**
     * Метод, реализующий запись коллекции в файл.
     * @param collection {@link LinkedList} с коллекциейж
     * @return {@link Boolean} с ответом записана ли коллекция в файл.
     * @throws IOException - выбрасывается, если не удается получить доступ к файлу.
     */
    @Override
    public Boolean write(List<T> collection) throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter(this.file));
        writer.write(this.converter.getCSVTitle(this.Tclass));
        writer.newLine();
        writer.flush();
        for (T element: collection){
            writer.write(this.converter.convert(element));
            writer.newLine();
            writer.flush();
        }
        writer.close();
        return true;
    }
}
