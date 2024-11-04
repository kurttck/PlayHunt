package com.aluracursos.playhunt.model;

public enum Genre {
    ACCION("Action", "Acción"),
    AVENTURA("Adventure", "Aventura"),
    INDEPENDIENTE("Indie", "Independiente"),
    DEPORTIVO("Sports", "Deportes"),
    ROL("RPG", "ROL"),
    MULTIJUGADOR("Multiplayer", "Multijugador"),
    ESTRATEGIA("Strategy", "Estrategia"),
    DISPAROS("Shooter", "Disparos"),
    CASUAL("Casual", "Casual"),
    SIMULACION("Simulation", "Simulación"),
    PUZZLE("Puzzle", "Puzzle"),
    ARCADE("Arcade", "Arcade"),
    TERROR("Horror", "Horror"),
    CARRERA("Racing", "Carrera"),
    ARTE("Art", "Arte"),
    PLATAFORMA("Platformer", "Plataforma"),
    LUCHA("Fighting", "Lucha"),
    FAMILIAR("Family", "Familiar"),
    EDUCACION("Educational", "Educativo"),
    CARTAS("Card", "Cartas");

    private String genres;

    private String genresSpa;

    Genre(String genres, String genresSpa) {
        this.genres = genres;
        this.genresSpa = genresSpa;
    }

    public  String getGenresSpa(){
        return genresSpa;
    }

    public static Genre fromString(String text) {

        for(Genre genre : Genre.values()){
            if(genre.genres.equalsIgnoreCase(text)){
                return genre;
            }
        }
        System.out.println("Ningún género encontrado con mmm: "+text);
        System.out.println("Por favor, guíese de la Leyenda.");

        return null;
    }

    public static Genre fromSpa(String text){
        for(Genre genre : Genre.values()){
            if(genre.genresSpa.equalsIgnoreCase(text.trim())){
                return genre;
            }
        }
        System.out.println("Ningún género encontrado con: "+text);
        System.out.println("Por favor, guíese de la Leyenda.");

        return null;
    }


    }
