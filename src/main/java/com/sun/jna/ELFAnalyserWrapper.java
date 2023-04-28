package com.sun.jna;

import java.io.IOException;

public class ELFAnalyserWrapper {
    public static boolean isArmHardFloat(String path) {
        try {
            return ELFAnalyser.analyse(path).isArmHardFloat();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
