package com.aluracursos.playhunt.model;

import com.aluracursos.playhunt.service.ApiTranslate;
import jakarta.persistence.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_game")
    private int id;

    @Column(unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;
    private LocalDate releaseDate;
    private String poster;
    private Double rating;

    @Enumerated(EnumType.STRING)
    private Genre genre;


    @ManyToMany
    @JoinTable(
            name = "game_platform",
            joinColumns = @JoinColumn(name = "id_game"),
            inverseJoinColumns = @JoinColumn(name = "id_platform")
    )
    private List<Platform> platforms;

    public Game(){

    }

    public Game(DataGame dataGame){
        this.name = dataGame.name();

        try{
            this.description = ApiTranslate.translate(dataGame.description());
        }catch (IOException | InterruptedException e){
            System.out.println("No se pudo traducir la sinopsis");
            this.description = dataGame.description();
        }
        this.releaseDate = LocalDate.parse(dataGame.releaseDate());

        this.poster = dataGame.poster();
        this.rating = dataGame.rating();

        if(dataGame.genres().size() > 0){
            this.genre = Genre.fromString(dataGame.genres().stream().findFirst().get().name());
        }else{
            this.genre = null;
        }

        this.platforms = new ArrayList<>();
        for(DataGame.Platforms platform : dataGame.platforms()){
            this.platforms.add(new Platform(platform.platform().name()));
        }
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", releaseDate=" + releaseDate +
                ", poster='" + poster + '\'' +
                ", rating=" + rating +
                ", genre='" + genre + '\'' +
                ", plataforms=" + platforms +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public List<Platform> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<Platform> platforms) {
        this.platforms = platforms;
    }
}
