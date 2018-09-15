package com.yusys.analysis;

import org.junit.Test;

import java.security.AlgorithmParameterGenerator;

import static org.junit.Assert.*;

public class AnalysisTest {

    @Test
    public void calculateSingle() {
        Analysis analysis = new Analysis();
        String num = "914848764359059584";
        String res = analysis.calculateSingle(num);
        System.out.println(res);
    }
}