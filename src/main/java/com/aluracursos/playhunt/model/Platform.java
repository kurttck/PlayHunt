package com.aluracursos.playhunt.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="platforms")
public class Platform {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_platform")
    private int id;

    @Column(unique = true)
    private String name;

    private String image;

    @ManyToMany(mappedBy = "platforms", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Game> game;

    @Override
    public String toString() {
        return "Platform{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                '}';
    }


    public Platform(){

    }

    public Platform(String name, String image) {

        this.name = name;
        this.image = image;
    }

    public Platform(String name){
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
