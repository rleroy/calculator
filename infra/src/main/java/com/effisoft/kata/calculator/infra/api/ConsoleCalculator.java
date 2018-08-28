package com.effisoft.kata.calculator.infra.api;

import com.effisoft.kata.calculator.domain.shell.Calculator;
import com.effisoft.kata.calculator.infra.spi.XmlStorage;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Scanner;

public class ConsoleCalculator {

    public static void main(String...args) throws JAXBException, IOException {
        Calculator calculator = new Calculator(
            new Scanner(System.in)::nextLine,
            System.out::println,
            new XmlStorage()
        );
        new Thread(calculator).start();
    }

}
