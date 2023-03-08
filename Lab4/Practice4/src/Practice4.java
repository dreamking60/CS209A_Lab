import java.io.IOException;

import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Practice4 {
    public static class City
    {
        private String name;
        private String state;
        private int population;

        public City(String name, String state, int population)
        {
            this.name = name;
            this.state = state;
            this.population = population;
        }

        public String getName()
        {
            return name;
        }

        public String getState()
        {
            return state;
        }

        public int getPopulation()
        {
            return population;
        }

        @Override
        public String toString() {
            return "City{" +
                    "name='" + name + "'" +
                    ", state='" + state + "'" +
                    ", population=" + population +
                    "}" ;
        }
    }

    public static Stream<City> readCities(String filename) throws IOException
    {
        return Files.lines(Paths.get(filename))
                .map(l -> l.split(", "))
                .map(a -> new City(a[0], a[1], Integer.parseInt(a[2])));
    }

    public static void main(String[] args) throws IOException, URISyntaxException {

        Stream<City> cities = readCities("cities.txt");
        // Q1: count how many cities there are for each state
        // TODO: Map<String, Long> cityCountPerState = ...
        Map<String, Long> cityCountPerState = cities.collect(Collectors.groupingBy(City::getState, Collectors.counting()));
        System.out.println("# of city per states: ");
        System.out.println(cityCountPerState.toString());

        cities = readCities("cities.txt");
        // Q2: count the total population for each state
        // TODO: Map<String, Integer> statePopulation = ...
        Map<String, Integer> statePopulation = cities.collect(Collectors.groupingBy(City::getState, Collectors.summingInt(City::getPopulation)));
        System.out.println("population per state: ");
        System.out.println(statePopulation.toString());

        cities = readCities("cities.txt");
        // Q3: for each state, get the set of cities with >500,000 population
        // TODO: Map<String, Set<City>> largeCitiesByState = ...
        Map<String, Set<City>> largeCitiesByState = cities.
                filter(city -> city.getPopulation() > 500_000).
                collect(Collectors.groupingBy(City::getState, Collectors.toSet()));
        System.out.println("Cities with population > 500_000");
        for (Map.Entry<String, Set<City>> entry : largeCitiesByState.entrySet()) {
            String state = entry.getKey();
            Set<City> citiesInState = entry.getValue();
            System.out.println(state + ": " + citiesInState);
        }

    }
}
