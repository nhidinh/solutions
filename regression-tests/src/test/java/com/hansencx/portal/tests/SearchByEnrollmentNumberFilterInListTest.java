package com.hansencx.portal.tests;

import com.hansencx.solutions.portal.PortalBaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @param
 * @author Nhi Dinh
 * @return
 * @since 1/15/2019
 */

public class SearchByEnrollmentNumberFilterInListTest extends PortalBaseTest {
    @Test(description = "Search by Enrollment Number With Filter 'in list' ")
    public void searchByEnrollmentNumberInList() {
        Page.TopNavigation().clickSearchButton();
        Page.Search().searchByEnrollmentNumberWithFilter("in list", "1");
        Page.Search().clickSearchButton();
        Assert.assertEquals(Page.SearchResult().getNumberOfResult(), 2);
    }
}
