package cz.muni.fi.pa165.kodemon;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.sql.DataSource;

/**
 * Configuration class for Spring.
 *
 * <p>We prefer using annotation based configuration (Java config).
 * <p>For the creation of DAOs we chose to use {@link org.springframework.data.jpa.repository.JpaRepository} from Spring Data.
 *
 * @author <a href="xseleng@fi.muni.cz">Maros Seleng, 422624</a>
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories
@ComponentScan(basePackages = "cz.muni.fi.pa165.kodemon")
public class KodemonApplicationContext {

    @Bean
    public JpaTransactionManager transactionManager(){
        return  new JpaTransactionManager(entityManagerFactory().getObject());
    }

    /**
     * Starts up a container that emulates behavior prescribed in JPA spec for container-managed EntityManager
     *
     * @return configured entity manager factory
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        LocalContainerEntityManagerFactoryBean jpaFactoryBean = new LocalContainerEntityManagerFactoryBean();
        jpaFactoryBean.setDataSource(db());
        jpaFactoryBean.setLoadTimeWeaver(instrumentationLoadTimeWeaver());
        jpaFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        return jpaFactoryBean;
    }

    @Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean(){
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public LoadTimeWeaver instrumentationLoadTimeWeaver() {
        return new InstrumentationLoadTimeWeaver();
    }

    /**
     * Creates the {@link DataSource} configured to use the Derby database.
     *
     * @return The dataSource
     */
    @Bean
    public DataSource db(){
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.DERBY)
                .build();
    }
}
