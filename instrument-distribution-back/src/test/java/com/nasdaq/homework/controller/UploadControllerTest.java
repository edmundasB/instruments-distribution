package com.nasdaq.homework.controller;

import com.nasdaq.homework.service.CSVDataExtractor;
import com.nasdaq.homework.service.DataExtractorFactory;
import com.nasdaq.homework.model.ExtractorType;
import com.nasdaq.homework.tree.Tree;
import com.nasdaq.homework.fixtures.InputPropertiesFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UploadControllerTest extends ControllerTest {
    @InjectMocks
    private UploadController controller;
    @Mock
    private DataExtractorFactory dataExtractorFactory;
    @Mock
    private Tree tree;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        when(dataExtractorFactory.getDataExtractor(eq(ExtractorType.CSV))).thenReturn(new CSVDataExtractor(InputPropertiesFixture.get()));
    }

    @Test
    public void postMultipartFileWhen200Success() throws Exception {
        MockMultipartFile requestFile =
                new MockMultipartFile("ledgerCsv", "ledger.csv", "text/plain", "some file".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/v1/upload")
                .file(requestFile))
                .andExpect(status().is(200));
    }
}
