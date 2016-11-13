package cz.muni.fi.pa165;

import cz.muni.fi.pa165.kodemon.KodemonApplicationContext;
import cz.muni.fi.pa165.kodemon.dao.TrainerDao;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.inject.Inject;

/**
 * Created by mseleng on 10/24/16.
 */
@ContextConfiguration(classes = KodemonApplicationContext.class)
public class SomeTest extends AbstractTestNGSpringContextTests {

    @Inject
    TrainerDao dao;

    @Test
    void basicTest() {
        // this is removed in other branch
    }
}
