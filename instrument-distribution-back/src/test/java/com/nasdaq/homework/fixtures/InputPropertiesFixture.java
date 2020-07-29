package com.nasdaq.homework.fixtures;

import com.nasdaq.homework.service.InputProperties;

public class InputPropertiesFixture {

    public static InputProperties get(){
        return new InputProperties("Transakcijos ID",
                "Šaltinio sąskaita",
                "Gavėjo sąskaita",
                "Instrumentas",
                "Pervedimo suma",
                ';');
    }

}
