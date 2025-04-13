package com.example;

public class Country {
    private int country_id;
    private String country_name;
    private int population;
    private int area;
    private Continent continent;

    public Country(int country_id, String country_name, int population, int area, Continent continent) {
        this.country_id = country_id;
        this.country_name = country_name;
        this.population = population;
        this.area = area;
        this.continent=continent;
    }

    public Continent getContinent() {
        return continent;
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
    }

    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public int getCountry_id() {
        return country_id;
    }

    public String getCountry_name() {
        return country_name;
    }

    public int getPopulation() {
        return population;
    }

    public int getArea() {
        return area;
    }

    @Override
    public String toString() {
        return "Country{" +
                "country_id=" + country_id +
                ", country_name='" + country_name + '\'' +
                ", population=" + population +
                ", area=" + area +
                ", area=" + continent +
                '}';
    }
}
