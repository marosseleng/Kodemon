package cz.muni.fi.pa165.kodemon.dao;

import cz.muni.fi.pa165.kodemon.entity.Stadium;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Data Access Object for the Stadium entity.
 *
 * @author Michal Romanek
 */
public interface StadiumDao extends JpaRepository<Stadium, Long> {

}
