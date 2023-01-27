package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MovieRepository {
    Map<String,Movie> movies = new HashMap<>();
    Map<String,Director> directors = new HashMap<>();
    Map<String,List<Movie>> directorMovieListMap = new HashMap<>();

    public String addMovie(Movie movie){
        String name = movie.getName();
        movies.put(name,movie);
        return "Movie added successfully";
    }
    public String addDirector(Director director){
        String name = director.getName();
        directors.put(name,director);
        return "Director added successfully";
    }
    public String addMovieDirectorPair(String movie , String director){
        if(!movies.containsKey(movie) || !directors.containsKey(director))
            return "Not Found";
        directors.get(director).setNumberOfMovies(directors.get(director).getNumberOfMovies()+1);
        List<Movie> list = directorMovieListMap.getOrDefault(director,new ArrayList<>());
        list.add(movies.get(movie));
        directorMovieListMap.put(director,list);
        return "Movie Director pair added successfully.";
    }
    public Movie getMovieByName(String name){
        return movies.getOrDefault(name,null);
    }
    public Director getDirectorByName(String name){
        return directors.getOrDefault(name,null);
    }
    public List<Movie> getMoviesByDirectorName(String name){
        return directorMovieListMap.getOrDefault(name,new ArrayList<>());
    }
    public List<Movie> findAllMovies(){
        List<Movie> list = new ArrayList<>();
        for(String name: movies.keySet())
            list.add(movies.get(name));
        return list;
    }
    public String deleteDirectorByName(String director){
        if(!directors.containsKey(director))
            return "Not Found";
        for (Movie movie:directorMovieListMap.getOrDefault(director,new ArrayList<>()))
            movies.remove(movie.getName());
        directorMovieListMap.remove(director);
        directors.remove(director);
       return "Deleted director successfully.";
    }
    public String deleteAllDirectors(){
        for(String director : directors.keySet()) {
            for (Movie movie : directorMovieListMap.getOrDefault(director, new ArrayList<>()))
                movies.remove(movie.getName());
            directorMovieListMap.remove(director);
            directors.remove(director);
        }
        return "Deleted directors successfully.";
    }

}
