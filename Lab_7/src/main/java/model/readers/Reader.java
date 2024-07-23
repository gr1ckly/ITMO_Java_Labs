package model.readers;

import com.opencsv.exceptions.CsvValidationException;
import validation.exceptions.CSVInvalidTitleException;
import validation.exceptions.CSVNotTitleException;
import validation.exceptions.ElementNotValidException;

import java.io.IOException;

/**
 * Базовый интерфейс для чтения данных
 * @param <T>
 * @param <S>
 */
public interface Reader<T, S> {
    public T read(S s) throws CsvValidationException, IOException, CSVNotTitleException, CSVInvalidTitleException, ElementNotValidException;
}
