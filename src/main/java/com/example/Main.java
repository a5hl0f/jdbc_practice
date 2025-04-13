package com.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final String URL = "jdbc:postgresql://localhost:5432/jdbc";
    private static final String USER = "postgres";
    private static final String PASSWORD = "password";


    public static void main(String[] args) throws SQLException {

        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        Statement statement = connection.createStatement();
        String ifexittable="DROP TABLE IF EXISTS country";
        statement.execute(ifexittable);

        String table="CREATE TABLE country (country_id int primary key , country_name varchar(20) not null," +
                "population int not null,area  int ,continent varchar(20))";
         statement.executeUpdate(table);


        System.out.println("print all country from postgres---------------------");
        String sql = "SELECT * FROM country";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            System.out.print(resultSet.getString(1));
            System.out.print(" " + resultSet.getString("country_name"));
            System.out.print(" " + resultSet.getString("population"));
            System.out.print(" " + resultSet.getString("area"));
            System.out.println(" " + resultSet.getString("continent"));
            System.out.println();
        }
        System.out.println("getAllCountries through method--------------------");
        List<Country> countryList = getAllCountries(connection);
        for (Country c : countryList) {
            System.out.println(c);
        }
        int max = getMaxId(connection) + 1;
        Country country = new Country(max, "Hungary", 1243, 23454, Continent.EUROPE);
        insertCountry(connection, country);
        deleteAll(connection);
        insertCountry(connection, new Country(1, "germany", 234243, 3535, Continent.EUROPE));
        insertCountry(connection, new Country(2, "mexico", 32532, 217868127, Continent.NORTH_AMERICA));
        insertCountry(connection, new Country(3, "USA2", 340100000, 9867000, Continent.NORTH_AMERICA));
        deleteById(connection, 1);
        updateById(connection, 2, 24355);
        System.out.println("after rollback transaction----------------");
        rollBack();
        System.out.println("getAllCountries through method after delete all--------------------");
        List<Country> countryList1 = getAllCountries(connection);
        for (Country c : countryList1) {
            System.out.println(c);
        }

        statement.close();
        connection.close();
    }

    public static List<Country> getAllCountries(Connection connection) throws SQLException {
        List<Country> countryList = new ArrayList<>();
        Statement statement = connection.createStatement();

        String sql = "SELECT * FROM country";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String name = resultSet.getString("country_name");
            int population = resultSet.getInt("population");
            int area = resultSet.getInt("area");
            Continent continent = Continent.valueOf(resultSet.getString("continent"));
            countryList.add(new Country(id, name, population, area, continent));
        }
        return countryList;
    }

    public static void insertCountry(Connection connection, Country country) throws SQLException {
        String insert = "insert into country (country_id,country_name,population,area,continent) values(?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(insert);
        statement.setInt(1, country.getCountry_id());
        statement.setString(2, country.getCountry_name());
        statement.setInt(3, country.getPopulation());
        statement.setInt(4, country.getArea());
        statement.setString(5, country.getContinent().name());
        statement.executeUpdate();
    }

    public static int getMaxId(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String MAXiD = "SELECT * FROM country WHERE country_id=(select max(country_id)  from country)";
        ResultSet maxId = statement.executeQuery(MAXiD);
        int max = 0;
        while (maxId.next()) {
            max = maxId.getInt(1);
        }
        return max;
    }

    public static void deleteAll(Connection connection) throws SQLException {
        String deleteAll = "truncate country";
        try (PreparedStatement statement = connection.prepareStatement(deleteAll)) {
            statement.executeUpdate();
        }
    }

    public static void deleteById(Connection connection, int id) throws SQLException {
        Statement statement = connection.createStatement();
        String deleteByID = "DELETE from country WHERE country_id=" + id;
        statement.executeUpdate(deleteByID);
    }

    public static void updateById(Connection connection, int id, int population) throws SQLException {
        Statement statement = connection.createStatement();
        String updateByID = "UPDATE country SET population=" + population + " WHERE country_id=" + id;
        statement.executeUpdate(updateByID);
    }

    public static void rollBack() throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String add = "insert into country (country_id,country_name,population,area,continent) values(?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(add);
            connection.setAutoCommit(false);
            int id = getMaxId(connection) + 1;
            statement.setInt(1, id);
            statement.setString(2, "Egypt");
            statement.setInt(3, 12532);
            statement.setInt(4, 2354352);
            statement.setString(5, Continent.NORTH_AMERICA.name());
            statement.executeUpdate();
            connection.rollback();
        }
    }
}