package cz.muni.fi.pa165.kodemon.dao;

import cz.muni.fi.pa165.kodemon.entity.TrainerFight;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Data Access Object for the TrainerFight entity.
 *
 * @author Oliver Roch
 */
public interface TrainerFightDao extends JpaRepository<TrainerFight, Long> {
}
