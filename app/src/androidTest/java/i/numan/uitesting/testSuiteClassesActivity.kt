package i.numan.uitesting

import org.junit.runner.RunWith
import org.junit.runners.Suite

// This will bundle the whole test classes and run at single click
@RunWith(Suite::class)
@Suite.SuiteClasses(
    MainActivityTest::class,
    SecondActivityTest::class
)
class tesSuiteClassesActivity