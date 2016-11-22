package com.kodemon.service.implementations;

import com.kodemon.persistence.dao.BadgeDao;
import com.kodemon.persistence.entity.Badge;
import com.kodemon.persistence.entity.Gym;
import com.kodemon.persistence.entity.Trainer;
import com.kodemon.persistence.enums.PokemonType;
import com.kodemon.service.interfaces.BadgeService;
import java.util.HashSet;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

/**
 * Badge service implementation
 * 
 * Can create new badge of a gym, assign a trainer, save and find a badge.
 * 
 * @author Miso Romanek
 */
public class BadgeServiceImpl implements BadgeService {
  
    @Inject
    private BadgeDao badgeDao;

    @Override
    public Badge createBadgeOfGym(Gym gym) {
        Badge badge = new Badge();
        badge.setGym(gym);
        return badge;
    }

    @Override
    public void assignTrainerToBadge(Trainer trainer, Badge badge) {
        badge.setTrainer(trainer);
        badgeDao.save(badge);
    }

    @Override
    public void save(Badge badge) {
        badgeDao.save(badge);
    }

    @Override
    public void delete(Badge badge) {
        badgeDao.delete(badge);
    }

    @Override
    public List<Badge> findByName(String name) {
        return badgeDao.findByName(name);
    }

    @Override
    public List<Badge> findByNameStartingWith(String prefix) {
        return badgeDao.findByNameStartingWith(prefix);
    }

    @Override
    public List<Badge> findByGym(Gym gym) {
        return badgeDao.findByGym(gym);
    }

    @Override
    public List<Badge> findByTrainer(Trainer trainer) {
        return badgeDao.findByTrainer(trainer);
    }    
}
