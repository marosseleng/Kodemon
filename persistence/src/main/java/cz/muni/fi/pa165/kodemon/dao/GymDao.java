package cz.muni.fi.pa165.kodemon.dao;

import cz.muni.fi.pa165.kodemon.entity.Gym;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Data Access Object for the Gym entity.
 *
 * @author Miso Romanek
 */
public interface GymDao extends JpaRepository<Gym, Long> {

}
