package cz.muni.fi.pa165.kodemon.dao;

import cz.muni.fi.pa165.kodemon.entity.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Data Access Object for the Badge entity.
 *
 * @author Oliver Roch
 */
public interface BadgeDao extends JpaRepository<Badge, Long> {

}
