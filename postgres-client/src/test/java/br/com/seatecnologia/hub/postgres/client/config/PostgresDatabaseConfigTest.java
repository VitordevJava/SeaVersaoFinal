package br.com.seatecnologia.hub.postgres.client.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.test.context.TestPropertySource;

import javax.sql.DataSource;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a configuração do banco de dados PostgreSQL.
 */
@SpringBootTest(classes = PostgresDatabaseConfig.class)
@TestPropertySource(locations = "classpath:postgres-client.properties")
public class PostgresDatabaseConfigTest {

    @Autowired
    private PostgresDatabaseConfig postgresDatabaseConfig;

    @Test
    public void testDataSourceProperties() {
        // Verificar se o bean DataSourceProperties é criado corretamente
        DataSourceProperties properties = postgresDatabaseConfig.dataSourceProperties();

        assertNotNull(properties, "DataSourceProperties não deve ser nulo");
    }

    @Test
    public void testDataSource() {
        // Verificar se o bean DataSource é criado corretamente
        DataSource dataSource = postgresDatabaseConfig.dataSource();

        assertNotNull(dataSource, "DataSource não deve ser nulo");
        assertTrue(dataSource instanceof DriverManagerDataSource, "DataSource deve ser uma instância de DriverManagerDataSource");
    }

    @Test
    public void testEntityManagerFactory() {
        // Verificar se o bean EntityManagerFactory é criado corretamente
        LocalContainerEntityManagerFactoryBean emf = postgresDatabaseConfig.entityManagerFactory();

        assertNotNull(emf, "EntityManagerFactory não deve ser nulo");

        // Verificar propriedades do JPA
        Map<String, Object> jpaProperties = emf.getJpaPropertyMap();
        assertNotNull(jpaProperties, "JPA properties não deve ser nulo");
        assertEquals("update", jpaProperties.get("hibernate.hbm2ddl.auto"), "hibernate.hbm2ddl.auto incorreto");
        assertEquals("true", jpaProperties.get("hibernate.show_sql"), "hibernate.show_sql incorreto");
        assertEquals("true", jpaProperties.get("hibernate.format_sql"), "hibernate.format_sql incorreto");
        assertEquals("org.hibernate.dialect.PostgreSQLDialect", jpaProperties.get("hibernate.dialect"), "hibernate.dialect incorreto");
    }

    @Test
    public void testTransactionManager() {
        // Verificar se o bean TransactionManager é criado corretamente
        JpaTransactionManager transactionManager = (JpaTransactionManager) postgresDatabaseConfig.transactionManager();

        assertNotNull(transactionManager, "TransactionManager não deve ser nulo");
        assertNotNull(transactionManager.getEntityManagerFactory(), "EntityManagerFactory no TransactionManager não deve ser nulo");
    }
}
