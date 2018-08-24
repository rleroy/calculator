package com.effisoft.kata.infra.api;

import com.effisoft.kata.domain.shell.Calculator;
import com.effisoft.kata.infra.spi.InMemoryStorage;

import java.util.Scanner;

public class ConsoleCalculator {

    public static void main(String...args) {
        Calculator calculator = new Calculator(
            new Scanner(System.in)::nextLine,
            System.out::println,
            new InMemoryStorage()
        );
        new Thread(calculator).start();
    }

}
