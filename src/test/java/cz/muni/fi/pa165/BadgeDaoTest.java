package cz.muni.fi.pa165;

import cz.muni.fi.pa165.kodemon.KodemonApplicationContext;
import cz.muni.fi.pa165.kodemon.dao.BadgeDao;
import cz.muni.fi.pa165.kodemon.dao.TrainerDao;
import cz.muni.fi.pa165.kodemon.dao.GymDao;
import cz.muni.fi.pa165.kodemon.entity.Badge;
import cz.muni.fi.pa165.kodemon.entity.Trainer;
import cz.muni.fi.pa165.kodemon.entity.Gym;
import cz.muni.fi.pa165.kodemon.enums.PokemonType;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Unit tests of {@link BadgeDao}
 *
 * @author Miso Romanek
 */
@Transactional
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@ContextConfiguration(classes = KodemonApplicationContext.class)
public class BadgeDaoTest extends AbstractTestNGSpringContextTests {

    @Inject
    private BadgeDao badgeDao;

    @Inject
    private TrainerDao trainerDao;

    @Inject
    private GymDao gymDao;

    private Trainer trainer;
    private Badge badge;
    private Gym gym;

    @BeforeMethod
    void prepare() {
        assertThat(badgeDao, is(notNullValue(BadgeDao.class)));
        badgeDao.deleteAll();
        assertThat("badgeDao.count() != 0L", badgeDao.count(), is(0L));

        assertThat(trainerDao, is(notNullValue(TrainerDao.class)));
        trainerDao.deleteAll();
        assertThat("trainerDao.count() != 0L", trainerDao.count(), is(0L));

        assertThat(gymDao, is(notNullValue(GymDao.class)));
        gymDao.deleteAll();
        assertThat("gymDao.count() != 0L", gymDao.count(), is(0L));

        prepareTrainer();
        assertThat("trainer == null", trainer, is(notNullValue()));
        trainerDao.save(trainer);

        prepareGym();
        assertThat("gym == null", gym, is(notNullValue()));
        gymDao.save(gym);

        badge = null;
    }

    /** save(...) tests */

    @Test(expectedExceptions = {DataAccessException.class})
    void testSaveNullBadge() {
        badge = null;
        assertThat(badge, is(nullValue(Badge.class)));
        badgeDao.save(badge);
    }

    @Test
    void testSaveCorrectInstance() {
        badge = randomBadge(1);
        badge.setName("Thunder Badge");
        badgeDao.save(badge);
        assertThat("trainer.getId() == null", trainer.getId(), is(notNullValue(Long.class)));
        assertThat("gym.getId() == null", gym.getId(), is(notNullValue(Long.class)));
        Badge testBadge = badgeDao.findOne(badge.getId());
        assertThat(testBadge, is(equalTo(badge)));
    }

    @Test
    void testSaveMultipleCorrectInstances() {
        int numberOfBadges = 3;
        List<Badge> badges = randomBadges(numberOfBadges);
        assertThat(badges.size(), is(equalTo(numberOfBadges)));
        badgeDao.save(badges);
        assertThat(badgeDao.count(), is(equalTo((long) badges.size())));
        assertThat(badgeDao.findAll(), is(equalTo(badges)));
    }

    @Test(expectedExceptions = {NullPointerException.class})
    void testSaveBadgeWithNullName() {
        badge = new Badge(gym, trainer);
        badge.setName(null);
        badgeDao.save(badge);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    void testSaveBadgeWithNullGym() {
        badge = new Badge(null, trainer);
        badge.setName("Volcano Badge");
        badgeDao.save(badge);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    void testSaveBadgeWithNullTrainer() {
        badge = new Badge(gym, null);
        badge.setName("Volcano Badge");
        badgeDao.save(badge);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    void testSaveBadgeWithNullNameAndNullGym() {
        badge = new Badge(null, trainer);
        badge.setName(null);
        badgeDao.save(badge);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    void testSaveBadgeWithNullNameAndNullTrainer() {
        badge = new Badge(gym, null);
        badge.setName(null);
        badgeDao.save(badge);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    void testSaveBadgeWithNullGymAndNullTrainer() {
        badge = new Badge();
        badge.setName("Volcano Badge");
        badgeDao.save(badge);
    }


    @Test(expectedExceptions = {NullPointerException.class})
    void testSaveBadgeWithEverythingNull() {
        badge = new Badge();
        badge.setName(null);
        badgeDao.save(badge);
    }

    /** delete(...) tests */

    @Test(expectedExceptions = {DataAccessException.class})
    void testDeleteNullBadge() {
        badge = null;
        assertThat(badge, is(nullValue(Badge.class)));
        badgeDao.delete(badge);
    }

    @Test(expectedExceptions = {DataAccessException.class})
    void testDeleteWithNonexistantId() {
        badgeDao.delete(99999999999L);
    }

    @Test
    void testDeleteByPassingInstance() {
        badge = randomBadge(5);
        assertThat(badgeDao.count(), is(equalTo(0L)));
        badgeDao.save(badge);
        assertThat(badgeDao.count(), is(equalTo(1L)));
        assertThat("badge.getId() == null", badge.getId(), is(notNullValue(Long.class)));
        badgeDao.delete(badge);
        assertThat(badgeDao.count(), is(equalTo(0L)));
    }

    @Test
    void testDeleteByPassingId() {
        badge = randomBadge(5);
        assertThat(badgeDao.count(), is(equalTo(0L)));
        badgeDao.save(badge);
        assertThat(badgeDao.count(), is(equalTo(1L)));
        assertThat("badge.getId() == null", badge.getId(), is(notNullValue(Long.class)));
        badgeDao.delete(badge.getId());
        assertThat(badgeDao.count(), is(equalTo(0L)));
    }

    @Test
    void testDeleteMultipleInstances() {
        List<Badge> badges = randomBadges(5);
        assertThat(badgeDao.count(), is(equalTo(0L)));
        badgeDao.save(badges);
        assertThat(badgeDao.count(), is(equalTo(5L)));
        badgeDao.delete(badges);
        assertThat(badgeDao.count(), is(equalTo(0L)));
    }

    /** find(...) tests */

    @Test
    void testFindByPassingId() {
        badge = randomBadge(5);
        badgeDao.save(badge);
        assertThat(badge.getId(), is(notNullValue()));
        Badge found = badgeDao.findOne(badge.getId());
        assertThat(found, is(equalTo(badge)));
    }

    @Test
    void testFindByPassingExample() {
        badge = randomBadge(5);
        badgeDao.save(badge);
        assertThat(badge.getId(), is(notNullValue()));
        Badge badgeExample = new Badge(badge.getGym(), badge.getTrainer());
        badgeExample.setName(badge.getName());
        Badge found = badgeDao.findOne(Example.of(badgeExample));
        assertThat(found, is(equalTo(badge)));
    }

    @Test
    void testFindAll() {
        int numberOfBadges = 5;
        assertThat(badgeDao.findAll(), is(empty()));
        List<Badge> badges = randomBadges(numberOfBadges);
        badgeDao.save(badges);
        List<Badge> found = badgeDao.findAll();
        assertThat(found.size(), is(equalTo(numberOfBadges)));
        assertThat(found, is(equalTo(badges)));
    }

    /** update tests */

    @Test
    void testCorrectNameUpdate() {
        badge = randomBadge(5);
        badgeDao.save(badge);
        assertThat(badgeDao.count(), is(equalTo(1L)));
        badge.setName("Rainbow Badge");
        badgeDao.saveAndFlush(badge);
        Badge found = badgeDao.findOne(badge.getId());
        assertThat(found, equalTo(badge));
    }

    @Test(expectedExceptions = {NullPointerException.class})
    void testUpdateWithNullName() {
        badge = randomBadge(5);
        assertThat(badgeDao.count(), is(equalTo(0L)));
        badgeDao.save(badge);
        badge.setName(null);
        badgeDao.saveAndFlush(badge);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    void testUpdateWithNullTrainer() {
        badge = randomBadge(5);
        assertThat(badgeDao.count(), is(equalTo(0L)));
        badgeDao.save(badge);
        badge.setTrainer(null);
        badgeDao.saveAndFlush(badge);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    void testUpdateWithNullGym() {
        badge = randomBadge(5);
        assertThat(badgeDao.count(), is(equalTo(0L)));
        badgeDao.save(badge);
        badge.setGym(null);
        badgeDao.saveAndFlush(badge);
    }

    private void prepareTrainer() {
        trainer = new Trainer();
        trainer.setFirstName("Ash");
        trainer.setLastName("Ketchum");
        Date born = new Calendar.Builder().setDate(1990, 12, 24).build().getTime();
        trainer.setDateOfBirth(born);
    }

    private void prepareGym() {
        gym = new Gym();
        gym.setCity("Saffron City");
        gym.setType(PokemonType.WATER);
    }

    private List<Badge> randomBadges(int size) {
        List<Badge> badges = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            badges.add(randomBadge(i));
        }
        assertThat(badges.size(), is(equalTo(size)));
        return badges;
    }

    private Badge randomBadge(int index) {
        Badge badge = new Badge(gym, trainer);
        String chars = "abcdefghijklmnopqrstuvxyzABCDEFGHIJKLMNOPQRSTUVXYZ ";
        badge.setName(chars.substring(index % chars.length()));
        return badge;
    }
}