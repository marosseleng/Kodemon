package cz.muni.fi.pa165.kodemon.dao;

import cz.muni.fi.pa165.kodemon.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Data Access Object for the Trainer entity.
 *
 * @author <a href="xseleng@fi.muni.cz">Maros Seleng, 422624</a>
 */
public interface TrainerDao extends JpaRepository<Trainer, Long> {

}
