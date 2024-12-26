package com.pyro.ets.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.pyro.utils.Config;
import com.pyrodb.DbConfig;
import com.pyrodb.Dbconn;
import com.pyrodb.jndireg;

import javax.sql.DataSource;

@SpringBootApplication
@ComponentScan(basePackages = "com.pyro")
public class StartModule {

    @Bean
    public Config config() {
        return new Config();
    }

    @Bean
    public DbConfig dbConfig() {
        return new DbConfig();
    }

    @Bean
    public jndireg jndiReg() {
        jndireg reg = new jndireg();
        reg.reg();
        return reg;
    }

    @Bean(name = "dataSource")
    public DataSource dataSource() {
        return new Dbconn().getDataSource();
    }

    @Bean(name = "dataSource2")
    public DataSource dataSource2() {
        return new Dbconn(DbConfig.properties.getProperty("CONTEXTPOOLNAME2"),
                DbConfig.properties.getProperty("PROVIDER_URL2")).getDataSourcenode2();
    }

    public static void main(String[] args) {
        SpringApplication.run(StartModule.class, args);
        System.out.println(" Module Started...");
    }
}
