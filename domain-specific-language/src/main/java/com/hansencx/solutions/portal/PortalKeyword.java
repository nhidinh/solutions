package com.hansencx.solutions.portal;

import com.hansencx.solutions.core.BaseKeyword;
import com.hansencx.solutions.portal.utilities.PortalPageGenerator;
import org.openqa.selenium.WebDriver;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortalKeyword extends BaseKeyword {

    public PortalKeyword(WebDriver driver) {super(driver);}

    public void callKeyword(String keyword, final List<String> listParameters) {
         Map<String, Runnable> dictionary = new HashMap<String, Runnable>();
         PortalPageGenerator pageGenerator = new PortalPageGenerator(driver);

         dictionary.put("go to search page", ()-> pageGenerator.TopNavigation().clickSearchButton());
         dictionary.put("navigate to portal", () -> pageGenerator.Login().goTo());
         dictionary.put("log in to portal", () -> pageGenerator.Login().logonWithUsername(listParameters.get(0),listParameters.get(1)));
         dictionary.put("search by enrollment number with filter", () -> pageGenerator.Search().searchByEnrollmentNumberWithFilter(listParameters.get(0),listParameters.get(1)));
         dictionary.put("click search", () -> pageGenerator.Search().clickSearchButton());
         dictionary.put("verify search result", ()-> pageGenerator.SearchResult().verifySearchResult(listParameters.get(0)));

        if (dictionary.containsKey(keyword.toLowerCase())){
            dictionary.get(keyword.toLowerCase()).run();
        }
    }
}
