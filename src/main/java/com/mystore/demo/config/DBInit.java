package com.mystore.demo.config;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DBInit {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void initialize(){
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            statement.execute("DROP TABLE IF EXISTS customer");
            statement.executeUpdate("CREATE TABLE customer (id integer, firstname varchar(30), lastname varchar(20), country varchar(20))");
            statement.executeUpdate("INSERT INTO customer  VALUES (1, 'James', 'Perez', 'CANADA')" );
            statement.executeUpdate("INSERT INTO customer  VALUES (2, 'Maria', 'Maria', 'CANADA')" );
            statement.executeUpdate("INSERT INTO customer  VALUES (3, 'Juan', 'Jones', 'USA')" );
            statement.close();
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}