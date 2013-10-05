package edu.njit.cs634.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AprioriTests.class, FileHandlerTests.class,
		ItemListTests.class, ParserTests.class })
public class AllTests {

}
