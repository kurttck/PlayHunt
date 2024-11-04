package com.aluracursos.playhunt.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="plataforms")
public class Plataform {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_plataform")
    private int id;

    @Column(unique = true)
    private String name;

    private String image;

    @ManyToMany(mappedBy = "plataforms", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Game> game;

    @Override
    public String toString() {
        return "Plataform{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                '}';
    }


    public Plataform(){

    }

    public Plataform( String name, String image) {

        this.name = name;
        this.image = image;
    }

    public Plataform(String name){
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
