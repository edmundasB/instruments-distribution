package com.nasdaq.homework.controller;

import com.google.gson.Gson;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
public class ControllerTest {
    public MockMvc mockMvc;
    public Gson gson = new Gson();
}
