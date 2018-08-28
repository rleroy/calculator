package com.effisoft.kata.infra.api;

import com.effisoft.kata.calculator.domain.core.AdditionService;
import org.junit.Assert;
import org.junit.Test;

public class ConsoleCalculatorShould {

    @Test
    public void not_have_access_to_the_domain_core() {
        AdditionService service = new AdditionService();
        Assert.assertEquals(Integer.valueOf(2), service.compute(1, 1));
    }
}
