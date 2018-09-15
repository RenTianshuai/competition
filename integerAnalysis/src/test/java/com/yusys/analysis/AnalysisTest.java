package com.yusys.analysis;

import org.junit.Test;
import org.omg.CORBA.PUBLIC_MEMBER;

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

    @Test
    public void getReturnDataTest(){
        Analysis analysis = new Analysis();
        String returnData = analysis.getReturnData("/home/jike/intdiv/分组名/output.txt");
        System.out.println(returnData);
    }
    @Test
    public void calculateSingle2Test(){
        double pow = Math.pow(8, 1 / 2D);
        System.out.println(pow);
        long round = Math.round(pow);
        System.out.println(round);
    }

    @Test
    public void startTest(){
//        Analysis analysis = new Analysis();
//        analysis.start();
    }
}