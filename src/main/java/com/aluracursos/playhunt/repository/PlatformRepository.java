package com.aluracursos.playhunt.repository;

import com.aluracursos.playhunt.model.Plataform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlatformRepository extends JpaRepository<Plataform, Integer> {

    Optional<Plataform> findByName(String name);


    @Query("select p from Plataform p where p.name ilike %:namePlatform%")
    List<Plataform> findPlatformsByName(String namePlatform);
}
