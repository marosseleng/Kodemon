package cz.muni.fi.pa165.kodemon.dao;

import cz.muni.fi.pa165.kodemon.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Data Access Object for the Trainer entity.
 *
 * @author <a href="mailto:marosseleng@gmail.com">Maros Seleng</a>
 */
public interface TrainerDao extends JpaRepository<Trainer, Long> {

}
