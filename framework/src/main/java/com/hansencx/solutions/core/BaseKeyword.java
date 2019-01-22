package com.hansencx.solutions.core;

import org.openqa.selenium.WebDriver;

/**
 * BaseKeyword class
 *
 * @author  Vi Nguyen
 * @version 1.0
 * @since   2018-12-03
 */
public class BaseKeyword {
    public WebDriver driver;

    /**
     * Constructor
     */
    public BaseKeyword(WebDriver driver) {
        this.driver = driver;
        System.out.println("From Base action");
    }
}
