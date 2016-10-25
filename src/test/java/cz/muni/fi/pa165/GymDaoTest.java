package cz.muni.fi.pa165;

import cz.muni.fi.pa165.kodemon.KodemonApplicationContext;
import cz.muni.fi.pa165.kodemon.dao.GymDao;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import javax.inject.Inject;

/**
 * Created by mseleng on 10/25/16.
 */
@ContextConfiguration(classes = KodemonApplicationContext.class)
public class GymDaoTest extends AbstractTestNGSpringContextTests {

    @Inject
    private GymDao dao;


}
