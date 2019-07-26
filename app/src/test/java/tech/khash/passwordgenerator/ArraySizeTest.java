package tech.khash.passwordgenerator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 *      Local unit test for testing the size of the arrays
 */

@RunWith(JUnit4.class)
public class ArraySizeTest {

    /**
     * Set up the environment for testing
     */
    @Before
    public void setUp() {
    }


    @Test
    public void lowercaseSizeTest() {
        int result = PassGenerator.getLowercaseArraySize();
        assertThat(result, is(equalTo(26)));
    }

    @Test
    public void uppercaseSizeTest() {
        int result = PassGenerator.getUppercaseArraySize();
        assertThat(result, is(equalTo(26)));
    }

    @Test
    public void numberSizeTest() {
        int result = PassGenerator.getNumbersArraySize();
        assertThat(result, is(equalTo(10)));
    }

    @Test
    public void characterSizeTest() {
        int result = PassGenerator.getCharacterArraySize();
        assertThat(result, is(equalTo(15)));
    }
}