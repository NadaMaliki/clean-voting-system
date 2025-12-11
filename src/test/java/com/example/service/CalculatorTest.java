package com.example.service;

import com.example.vote.service.Calculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class CalculatorTest{
    @Test
    public void testAdd(){
        Assertions Assert = null;
        Assert.assertEquals(8,new Calculator().add(5,3));
    }
}