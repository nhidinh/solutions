package com.hansencx.solutions.core;

import org.openqa.selenium.WebDriver;

/**
 * BaseAction class
 *
 * @author  Vi Nguyen
 * @version 1.0
 * @since   2018-12-03
 */
public class BaseAction {
    protected WebDriver driver;

    /**
     * Constructor
     */
    public BaseAction(){}

    public BaseAction(WebDriver driver) {
        this.driver = driver;
        System.out.println("From Base action");
    }
}
