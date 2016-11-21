package com.kodemon.service.test;

import com.kodemon.persistence.dao.BadgeDao;
import com.kodemon.persistence.entity.Badge;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by mseleng on 11/21/16.
 */
public class SimpleTest {

    @Test
    void someTest() {
        BadgeDao dao = mock(BadgeDao.class);
        List<Badge> list = new ArrayList<>();
        when(dao.findByName("aaa")).thenReturn(list);
        assertThat(dao.findByName("aaa"), is(empty()));
    }
}
