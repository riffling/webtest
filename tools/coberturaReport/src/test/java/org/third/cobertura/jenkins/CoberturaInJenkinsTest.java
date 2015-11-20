package org.third.cobertura.jenkins;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class CoberturaInJenkinsTest {
    CoberturaInJenkins cij = null;

    @Before
    public void setup() {
        cij = new CoberturaInJenkins();
    }

    @Test
    public void testHello() throws Exception {
        assertEquals("Response:zhangsan", cij.hello("zhangsan"));
    }

    @Test
    public void testHello1() {
        assertEquals("Response:zhangsan", cij.hello1("zhangsan"));
    }

    @Test
    public void testHello2() throws Exception {
        CoberturaInJenkins c1 = PowerMockito.mock(CoberturaInJenkins.class);
        PowerMockito.when(c1, "hello", new Object[]{ "user1"}).thenReturn("user1_mock");
        System.out.println(c1.hello("user1"));
        
        CoberturaInJenkins c2 = PowerMockito.mock(CoberturaInJenkins.class);
        PowerMockito.when(c2, "hello", new Object[]{ "user21"}).thenReturn("user21_mock");
        System.out.println(c2.hello("user21"));
        
         PowerMockito.mockStatic(CoberturaInJenkins.class);
         System.out.println(c1.hello("user1"));
         System.out.println(c2.hello("user1"));

        
        assertEquals("Response:zhangsan", cij.hello2("zhangsan"));

    }

}
