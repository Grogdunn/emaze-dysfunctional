package net.emaze.dysfunctional.delegates;

import org.junit.Test;
import org.junit.Assert;

/**
 *
 * @author rferranti
 */
public class NeverTest {

    @Test
    public void neverYieldsFalseWithNull(){
        Assert.assertFalse(new Never<Object>().test(null));
    }


}