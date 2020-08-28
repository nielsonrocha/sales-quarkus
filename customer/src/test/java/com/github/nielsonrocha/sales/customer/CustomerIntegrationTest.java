package com.github.nielsonrocha.sales.customer;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = "pretty", monochrome = true, snippets = CucumberOptions.SnippetType.CAMELCASE)
public class CustomerIntegrationTest {
}
