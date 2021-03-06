package com.nasdaq.homework.tree;

import com.nasdaq.homework.exceptions.TreeNotFoundException;
import com.nasdaq.homework.model.Transaction;
import com.nasdaq.homework.service.CSVDataExtractor;
import com.nasdaq.homework.fixtures.InputPropertiesFixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.PropertySource;

import java.io.File;
import java.util.List;

@PropertySource("classpath:input.properties")
class TreeTest {
    @InjectMocks
    private Tree tree;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        CSVDataExtractor csvDataExtractor = new CSVDataExtractor(InputPropertiesFixture.get());

        File inputFile =  new File(getClass().getResource("../test_data.csv").toURI());
        List<Transaction> transactions = csvDataExtractor.load(inputFile);
        tree.load(transactions);
    }

    @Test
    void makeTransactionsTreeFromList() throws TreeNotFoundException {
        Assertions.assertEquals(1, tree.searchFirstNodeByName("Company1").get().getChildren().size());
        Assertions.assertEquals(1, tree.searchFirstNodeByName("VP0001").get().getChildren().size());
        Assertions.assertEquals(1, tree.searchFirstNodeByName("VP0002").get().getChildren().size());
        Assertions.assertEquals(6, tree.searchFirstNodeByName("VP0003").get().getChildren().size());
    }

    @Test
    void fetchTreeByLastLeafAccountNoThenCheckFullTree() throws TreeNotFoundException {
        Node<Transaction> filteredTree = tree.fetchTree("VP0008");

        Assertions.assertEquals(1, tree.searchFirstNodeByName(filteredTree, "Company1").get().getChildren().size());
        Assertions.assertEquals(1, tree.searchFirstNodeByName(filteredTree, "VP0001").get().getChildren().size());
        Assertions.assertEquals(1, tree.searchFirstNodeByName(filteredTree, "VP0002").get().getChildren().size());
        Assertions.assertEquals(1, tree.searchFirstNodeByName(filteredTree, "VP0003").get().getChildren().size());
        Assertions.assertEquals(0, tree.searchFirstNodeByName(filteredTree, "VP0008").get().getChildren().size());

        Node<Transaction> fullTree = tree.fetchTree(null);

        Assertions.assertEquals(6, tree.searchFirstNodeByName("VP0003").get().getChildren().size());
    }

    @Test
    void fetchTreeByMiddleAccountNo() throws TreeNotFoundException {
        Node<Transaction> filteredTree = tree.fetchTree("VP0002");

        Assertions.assertEquals(1, tree.searchFirstNodeByName(filteredTree, "Company1").get().getChildren().size());
        Assertions.assertEquals(1, tree.searchFirstNodeByName(filteredTree, "VP0001").get().getChildren().size());
        Assertions.assertEquals(0, tree.searchFirstNodeByName(filteredTree, "VP0002").get().getChildren().size());
    }

    @Test
    void cleanTreeWhenTreeRemoved(){
        tree.clean();
        Assertions.assertThrows( TreeNotFoundException.class, () -> { tree.fetchTree(null); } );
    }

}
