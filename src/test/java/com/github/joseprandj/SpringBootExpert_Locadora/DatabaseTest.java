package com.github.joseprandj.SpringBootExpert_Locadora;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseTest {
    static Connection connection;

    @BeforeAll
    static void setUpDataBase() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
        connection.createStatement().execute("CREATE TABLE USERS(ID INT, NAME VARCHAR2)");
    }

    @AfterAll
    static void closeDataBase() throws SQLException {
        connection.close();
    }

    @BeforeEach
    void insertUserTest() throws SQLException {
        connection.createStatement().execute("INSERT INTO USERS (ID, NAME) VALUES(1, 'JJ')");
    }

    @Test
    void testUserExists() throws SQLException {
        ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM USERS WHERE ID = 1");
        Assertions.assertTrue(rs.next());
    }
}
