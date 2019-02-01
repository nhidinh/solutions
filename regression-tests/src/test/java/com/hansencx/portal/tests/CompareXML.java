package com.hansencx.portal.tests;

import com.hansencx.portal.common.DataFilePathHandler;
import com.hansencx.solutions.core.BaseTest;
import org.testng.annotations.Test;
import utilities.configuration.InitialData;
import utilities.helper.XmlHelper;

import java.io.File;

public class CompareXML extends BaseTest {

    @Test(description = "Compare xml")
    public void compareXml() throws Exception {
        String originPath = DataFilePathHandler.DATA_DIRECTORY_PATH;
        String diffFilePath = InitialData.XML_DIFFERENCE_DIR_PATH + InitialData.XML_DIFF_FILE_NAME;

        XmlHelper xmlHelper = new XmlHelper();
        xmlHelper.createTempAndSplitAndDiffFolder();

        xmlHelper.splitFile(originPath + "BillPrint_QA.xml", InitialData.XML_SPLITTED_DIR_PATH, "Accounts", "Account", "AccountNumber");
        xmlHelper.compare2XMLFiles(InitialData.XML_SPLITTED_DIR_PATH + "5483077000.xml", originPath + "5483077000_Prod.xml", InitialData.XML_DIFFERENCE_DIR_PATH + InitialData.XML_DIFF_FILE_NAME);

        File file = new File(diffFilePath);
        if (file.exists())
            InitialData.FINISH_TEST_INFO = "List diff files: " + diffFilePath;
        else
            InitialData.FINISH_TEST_INFO = "List diff files: None";
    }

}
