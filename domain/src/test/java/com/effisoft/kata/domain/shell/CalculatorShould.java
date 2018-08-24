package com.effisoft.kata.domain.shell;

import com.effisoft.kata.domain.core.DefaultStorage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.LinkedList;

public class CalculatorShould {

    private LinkedList<String> inputData;
    private LinkedList<String> outputData;
    private CalculatorStorage storage;
    private Calculator calculator;

    @Before
    public void setUp() {
        inputData = new LinkedList<>();
        outputData = new LinkedList<>();

        DefaultStorage.getInstance().reset();
        storage = Mockito.spy(new CalculatorStorage() {});

        calculator = Mockito.spy(new Calculator(inputData::removeFirst, outputData::addLast, storage));
    }

    @Test
    public void close_on_exit() throws InterruptedException {
        inputData.push("exit");

        Thread thread = new Thread(calculator);
        thread.start();
        thread.join();

        Assert.assertNull(calculator.error);
    }

    @Test
    public void cause_error_on_empty_input() throws InterruptedException {
        Thread thread = new Thread(calculator);
        thread.start();
        thread.join();

        Assert.assertNotNull(calculator.error);
    }

    @Test
    public void compute_1_plus_1_into_2() {
        String result = calculator.compute("1+1");

        Assert.assertEquals("2", result);
    }

    @Test
    public void compute_45_plus_87_into_132() {
        String result = calculator.compute("45+87");

        Assert.assertEquals("132", result);
    }

    @Test
    public void write_responses_to_output() throws InterruptedException {
        inputData.add("3+2");
        inputData.add("exit");

        Thread thread = new Thread(calculator);
        thread.start();
        thread.join();

        Assert.assertEquals("5", outputData.removeFirst());
    }

    @Test
    public void store_operation_result() {
        calculator.compute("1+1");

        Mockito.verify(storage, Mockito.times(1)).store("1+1", "2");
    }

    @Test
    public void compute_operation_only_once() {
        calculator.compute("1+1");
        calculator.compute("1+1");

        Mockito.verify(calculator, Mockito.times(1)).computeOnServices("1+1");
        Mockito.verify(storage, Mockito.times(1)).store("1+1", "2");
        Mockito.verify(storage, Mockito.times(2)).retrieve("1+1");
    }

    @Test
    public void compute_1_minus_1_into_0() {
        String result = calculator.compute("1-1");

        Assert.assertEquals("0", result);
    }

    @Test
    public void compute_6_mult_7_into_42() {
        String result = calculator.compute("6*7");

        Assert.assertEquals("42", result);
    }
}
