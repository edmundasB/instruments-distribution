package com.nasdaq.homework.controller;

import com.nasdaq.homework.exceptions.TreeNotFoundException;
import com.nasdaq.homework.model.Transaction;
import com.nasdaq.homework.tree.Tree;
import com.nasdaq.homework.tree.Node;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.eq;

class TreeControllerTest extends ControllerTest {
    @InjectMocks
    private TreeController controller;
    @Mock
    private Tree service;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getLedgerTreeWhenStatus200OK() throws Exception {
        Mockito.when(service.fetchTree()).thenReturn(new Node<>("Some tree"));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/ledger/tree")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Mockito.verify(service, Mockito.times(1)).fetchTree();

        String responseString = mvcResult.getResponse().getContentAsString();
        Node<Transaction> treeDto = gson.fromJson(responseString, Node.class);

        Assertions.assertNotNull(treeDto);
    }

    @Test
    public void getLedgerTreeByQueryThenStatus200OK() throws Exception {
        String accountForSearch = "VP0001";
        Mockito.when(service.fetchTree(eq(accountForSearch))).thenReturn(new Node<>("Some tree"));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/ledger/tree?account="+accountForSearch)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Mockito.verify(service, Mockito.times(1)).fetchTree(eq(accountForSearch));

        String responseString = mvcResult.getResponse().getContentAsString();
        Node<Transaction> treeDto = gson.fromJson(responseString, Node.class);

        Assertions.assertNotNull(treeDto);
    }

    @Test
    public void getLedgerTreeWhenStatus404NotFound() throws Exception {
        Mockito.when(service.fetchTree()).thenThrow(new TreeNotFoundException(""));
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/ledger/tree")

                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        Mockito.verify(service, Mockito.times(1)).fetchTree();
    }
}
