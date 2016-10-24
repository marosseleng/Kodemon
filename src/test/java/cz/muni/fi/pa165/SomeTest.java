package cz.muni.fi.pa165;

import cz.muni.fi.pa165.kodemon.KodemonApplicationContext;
import cz.muni.fi.pa165.kodemon.dao.TrainerDao;
import cz.muni.fi.pa165.kodemon.entity.Trainer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

/**
 * Created by mseleng on 10/24/16.
 */
@ContextConfiguration(classes = KodemonApplicationContext.class)
public class SomeTest extends AbstractTestNGSpringContextTests {

    @Inject
    TrainerDao dao;

    @Test
    void basicTest() {
        Trainer trainer = new Trainer();
        trainer.setDateOfBirth(new Date(1445715970L));
        trainer.setFirstName("Maros");
        trainer.setLastName("Seleng");
        dao.save(trainer);
        assertThat(dao.findAll().size(), is(greaterThan(0)));
    }
}
