package com.aluracursos.playhunt.repository;

import com.aluracursos.playhunt.model.Platform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlatformRepository extends JpaRepository<Platform, Integer> {

    Optional<Platform> findByName(String name);


    @Query("select p from Platform p where p.name ilike %:namePlatform%")
    List<Platform> findPlatformsByName(String namePlatform);
}
