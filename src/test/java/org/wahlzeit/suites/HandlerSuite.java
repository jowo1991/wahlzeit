package org.wahlzeit.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        org.wahlzeit.handlers.TellFriendTest.class
})
public class HandlerSuite {
}
