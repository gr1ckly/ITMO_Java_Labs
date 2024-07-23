package org.example;

import commonModule.collectionElements.Chapter;
import commonModule.collectionElements.Coordinates;
import commonModule.collectionElements.SpaceMarine;
import commonModule.collectionElements.interfaces.IHaveInputData;
import commonModule.parsers.FormParser;
import commonModule.requests.Request;
import commonModule.commands.CommandType;
import commonModule.validation.annotations.AnnotationValidator;
import view.ConsoleApp;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ConsoleApp consoleApp = new ConsoleApp();
        consoleApp.launch();
    }
}