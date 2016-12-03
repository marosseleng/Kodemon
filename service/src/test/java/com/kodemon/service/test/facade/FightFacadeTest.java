package com.kodemon.service.test.facade;

import com.kodemon.service.config.ServiceConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

/**
 * @author Oliver Roch
 */

@ContextConfiguration(classes=ServiceConfig.class)
public class FightFacadeTest extends AbstractTestNGSpringContextTests {
}
