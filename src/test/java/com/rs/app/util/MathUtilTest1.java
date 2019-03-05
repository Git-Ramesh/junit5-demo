package com.rs.app.util;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/*
    Note1: JUNIT creates an instance of test class for each test case, to control instance creation we use
    @TestInstance annotation by supplying the Lifecycle enum.
 */

/*
    Note2: @BeforeAll, @AfterAll will execute only once per test class,
    where as @BeforeEach, @AfterEach will execute for every test.
 */

/*
    Note3: In general @BeforeAll, @AfterAll annotated methods should be static, but if you
    use Lifecycle as Lifecycle.PER_CLASS then it also possible to make them instance methods.

    => If you use static methods those will be executed after static block.
            MathUtilTest: static block
            MathUtilTest: BeforeAll
            MathUtilTest: 0 - param constr
            MathUtilTest: BeforeEach
            MathUtilTest: AfterEach
            MathUtilTest: 0 - param constr
            public void com.rs.app.util.MathUtilTest.testAddToExpectFailure() is @Disabled
            MathUtilTest: AfterAll
    => If you use instance methods those will be executed after constructor.
            MathUtilTest: static block
            MathUtilTest: 0 - param constr
            MathUtilTest: BeforeAll
            MathUtilTest: BeforeEach
            MathUtilTest: AfterEach
            MathUtilTest: AfterAll
 */

/*
    Note4: JUNIT instantiate the test class even for @Disabled methods to.
 */

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@DisplayName("When running MathUtils")
public class MathUtilTest1 {
    static {
        System.out.println("MathUtilTest: static block");
    }

    private MathUtil mathUtil;

    private MathUtilTest1() {
        System.out.println("MathUtilTest: 0 - param constr");
    }

    @BeforeAll
    private static void setUpBeforeAll() {
        System.out.println("MathUtilTest: BeforeAll\n");
    }

    @AfterAll
    private static void tearDownAfterAll() {
        System.out.println("MathUtilTest: AfterAll");
    }

    @BeforeEach
    private void setUpBeforeEach() {
        this.mathUtil = new MathUtil();
        System.out.println("MathUtilTest: BeforeEach");
    }

    @AfterEach
    private void tearDownAfterEach() {
        this.mathUtil = null;
        System.out.println("MathUtilTest: AfterEach");
    }

    @Test
    @DisplayName("mul method")
    public void testMul() {
        assertAll(
                () -> assertEquals(4, this.mathUtil.mul(2, 2)),
                () -> assertEquals(-2, this.mathUtil.mul(-2, 1)),
                () -> assertEquals(0, this.mathUtil.mul(2, 0))
        );
    }

    @Test
    @EnabledOnOs(OS.LINUX)
    @EnabledOnJre(JRE.JAVA_8)
    public void testDiv() {
        boolean isServerUp = false;
        assumeTrue(isServerUp); // org.opentest4j.TestAbortedException: Assumption failed: assumption is not true
        assertThrows(ArithmeticException.class, () -> {
            this.mathUtil.div(10, 0);
        }, "divide by ZERO should throw an ArithmeticException");
    }

    @Test
    @DisplayName("TDD method should not be run")
    @Disabled
    public void testDisabled() {
        fail("This method shouldn't be run");
    }

    @Nested // You can work with assertAll instead of @Nested
    @DisplayName("add test")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class AddTest {
        private AddTest() {
            System.out.println("AddTest: 0 - param constr");
        }

        @Test
        @DisplayName("When adding two possitive numbers") // Display name for test method.
        public void testAddPossitiveNumbers() {
            int expected = 30;
            int actual = mathUtil.add(10, 20);
            assertEquals(expected, actual, "should return the right result");
        }

        @Test // Make the method as test.
        @DisplayName("When adding two negative numbers")
        public void testAddNegativeNumbers() {
            int expected = -30;
            int actual = mathUtil.add(-10, -20);
            assertEquals(expected, actual, "should return the right result");
        }
    }
}
