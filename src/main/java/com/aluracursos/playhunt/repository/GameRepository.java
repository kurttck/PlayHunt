package com.aluracursos.playhunt.repository;

import com.aluracursos.playhunt.model.Game;
import com.aluracursos.playhunt.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    @Query("select g from Game g join fetch g.plataforms where g.genre ilike :genre")
    List<Game> findByGenre(Genre genre);

    @Query("SELECT g FROM Game g JOIN FETCH g.plataforms")
    List<Game> findAllWithPlatforms();

    @Query("select g from Game g join fetch g.plataforms where g.name ilike %:nameGame%")
    List<Game> findGamesByName(String nameGame);

    @Query("select g from Game g join fetch g.plataforms p where p.name ilike %:nameConsole%")
    List<Game> findGamesByConsole(String nameConsole);

    @Query("select g from Game g join fetch g.plataforms p order by g.rating desc limit 10")
    List<Game> findTop10Games();

}
