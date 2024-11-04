package com.aluracursos.playhunt.main;

import com.aluracursos.playhunt.model.*;
import com.aluracursos.playhunt.repository.GameRepository;
import com.aluracursos.playhunt.repository.PlatformRepository;
import com.aluracursos.playhunt.service.ApiConsumer;
import com.aluracursos.playhunt.service.DataConvert;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Main {
    private Scanner scan = new Scanner(System.in);
    private ApiConsumer apiConsumer = new ApiConsumer();
    private final String URL_BASE ="https://api.rawg.io/api/";
    private final String API_KEY = System.getenv("KEY_RAWG");
    private GameRepository gameRepository;
    private PlatformRepository platformRepository;
    private DataConvert dataConvert = new DataConvert();

    public Main(GameRepository gameRepository, PlatformRepository platformRepository) {
        this.gameRepository = gameRepository;
        this.platformRepository = platformRepository;
    }


    public void menu() throws UnsupportedEncodingException {
        int option=0;
        System.out.println("********* BIENVENIDO A PLAYHUNT GAME MANAGER *********");
        while (option != 3) {
            System.out.println("\n*** Menu Principal ***");
            System.out.println("""
                1. Registrar
                2. Consultas
                
                3. Salir
                
                Ingresa la opción deseada:
                """);

            try{
                option = scan.nextInt();
            }catch (InputMismatchException e){
                System.out.println("Por favor ingresa un número");
                scan.nextLine();
                continue;
            }


            switch (option){
                case 1: registerMenu();break;
                case 2: queryMenu();break;
                case 3:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida");
                    break;
            }
        }
    }

    //**** Menu para registrar un juego o consola ****
    public void registerMenu() throws UnsupportedEncodingException {
        var option = 1;

        while (option!=2){
            System.out.println("""
                *** Menu de Registro con API RAWG ***
                
                1. Registro de juego: buscando por nombre
                
                2. salir
                """);

            try{
                option = scan.nextInt();
            }catch (InputMismatchException e){
                System.out.println("Por favor ingresa un número");
                scan.nextLine();
                continue;
            }

            switch (option){
                case 1: registerGameForName();break;
                case 2:
                    System.out.println("Volviendo al menú...");
                    break;
                default:
                    System.out.println("Opción inválida");
                    break;
            }
        }
    }


    public DataGame getDataGame() throws UnsupportedEncodingException {
        System.out.println("Ingresa el nombre del juego o salir para regresar");

        String nameGame;
        while(true){
            nameGame = scan.nextLine().trim();
            if (!nameGame.isEmpty()){
                break;
            }
        }


        if(nameGame.equalsIgnoreCase("salir")){
            return null;
        }

        String encodeName = URLEncoder.encode(nameGame, "UTF-8");

        var json = apiConsumer.getData(URL_BASE+"games?search="+encodeName+"&key="+API_KEY);
        DataGameSearch dataGameSearch = dataConvert.convert(json, DataGameSearch.class);

        if(dataGameSearch.results().isEmpty()){
            System.out.println("No se encontraron juegos con el nombre: "+nameGame);
            return null;
        }

        var jsonSearch = apiConsumer.getData(URL_BASE+"games/"+dataGameSearch.results().get(0).slug()+"?key="+API_KEY);
        DataGame dataGame = dataConvert.convert(jsonSearch, DataGame.class);

        return dataGame;
    }

    public void registerGameForName() throws UnsupportedEncodingException {
        List<Platform> platformVerification = platformRepository.findAll();

        if(platformVerification.isEmpty()){
            registerAllConsoles();
        }

        while (true){
            DataGame dataGame = getDataGame();
            if(dataGame == null){
                return;
            }


            Game game = new Game(dataGame);
            List<Platform> platforms = new ArrayList<>();
            for(DataGame.Platforms platformData : dataGame.platforms()){
                DataGame.Platforms.Platform platform = platformData.platform();

                Optional<Platform> platformOptional = platformRepository.findByName(platform.name());

                if(platformOptional.isPresent()){
                    platforms.add(platformOptional.get());
                };
            }

            game.setPlatforms(platforms);
            String genreValidation = dataGame.genres().isEmpty() ? "" : dataGame.genres().get(0).name();

            showGame(game);

            try{
                gameRepository.save(game);
                System.out.println(""+dataGame.name()+" guardado correctamente\n");
            } catch (DataIntegrityViolationException ex) {
                System.out.println("Ya existe el juego: "+dataGame.name());
            }
        }
    }

    public void registerAllConsoles(){

        var json = apiConsumer.getData(URL_BASE+"platforms?key="+API_KEY);
        DataPlatform dataPlatform = dataConvert.convert(json, DataPlatform.class);

        dataPlatform.results().forEach(e-> {
            Platform platform = new Platform(e.name(), e.imageBackground());
            platformRepository.save(platform);
        });
    }



    //**** MENU PARA CONSULTAS EN LA BASE DE DATOS CON JPQL ****
    public void queryMenu(){

        int option = 0;

        while (option!=6){

            System.out.println("""
                
                *** Menú de Consultas a nuestra BD ***
                
                1. Mostrar todos los juegos detallados
                2. Buscar juegos por consola
                3. Buscar juegos por género
                4. Buscar por nombre de juego
                5. Top 10 juegos más populares de nuestra BD
                
                6. Salir
                """);

            try{
                option = scan.nextInt();
            }catch (InputMismatchException e){
                System.out.println("Por favor ingresa un número");
                scan.nextLine();
                continue;
            }

            switch (option){
                case 1: queryAllGames();break;
                case 2: queryGamesByConsole();break;
                case 3: queryGamesByGenre();break;
                case 4: queryGamesByName();break;
                case 5: queryTopGames();break;
                case 6:
                    System.out.println("Voliendo al menú...");
                    break;
                default:
                    System.out.println("Opción inválida");
                    break;
            }
        }

    }

    @Transactional
    public void queryAllGames(){
        List<Game> games = gameRepository.findAllWithPlatforms();

        games.forEach(e->showGame(e));
    }

    public void queryGamesByConsole(){
        System.out.println("Ingrese la consola a buscar");

        scan.nextLine();
        var console = scan.nextLine().trim();
        List<Game> games = gameRepository.findGamesByConsole(console);


        if(games.isEmpty()){
            System.out.println("No se encontraron juegos con la consola: "+console);
            return;
        }

        List<Platform> platforms = platformRepository.findPlatformsByName(console);

        String platformsShow = platforms.stream()
                .map(Platform::getName)
                .collect(Collectors.joining(", "));

        System.out.println("\nJuegos de la consola : "+platformsShow);
        games.forEach(e->{
            String platformsPerGame = e.getPlatforms().stream()
                    .map(Platform::getName)
                    .collect(Collectors.joining(", "));
            System.out.println("Juego: "+e.getName()+" ("+platformsPerGame+")");
        });


    }

    public void queryGamesByGenre() {
        System.out.println("Escribe el género del juego");

        String genresList = Arrays.stream(Genre.values()).map(Genre::getGenresSpa).collect(Collectors.joining(", "));
        System.out.println("Leyenda: "+genresList);

        scan.nextLine();
        var genre = scan.nextLine().trim();

        var genreEnum = Genre.fromSpa(genre);

        if (genreEnum == null) {
            return;
        }

        List<Game> games = gameRepository.findByGenre(genreEnum);

        games.forEach(e->showGame(e));

    }

    @Transactional
    public void queryGamesByName(){
        System.out.println("Ingrese el nombre del juego a buscar");
        scan.nextLine();
        var nameGame = scan.nextLine().trim();

        List<Game> games = gameRepository.findGamesByName(nameGame);


        if(games.isEmpty()){
            System.out.println("No se encontraron juegos con el nombre: "+nameGame);
            return;
        }
        games.forEach(e->showGame(e));
    }

    public void queryTopGames(){
        List<Game> games = gameRepository.findTop10Games();

        System.out.println("***** Top 10 Juegos *****\n");
        AtomicInteger count= new AtomicInteger(1);
        games.forEach(e-> showGame(e));
    }

   /* public void showGame(String name, String description, String platform, String genre, double rating, String releaseDate){

        System.out.printf("""
                    ***********************
                    Juego: %s
                    Descripción: %s
                    Consolas: %s
                    Género: %s
                    Rating: %.2f
                    Fecha de lanzamiento: %s
                    ***********************
                    """, name, description, platform, genre, rating, releaseDate);
    }*/

    public void showGame(Game g){
        String platforms = g.getPlatforms().stream()
                .map(Platform::getName)
                .collect(Collectors.joining(", "));

        String genreValidation = g.getGenre() != null ? g.getGenre().name() : "";

        System.out.printf("""
                    ***********************
                    Juego: %s
                    Descripción: %s
                    Consolas: %s
                    Género: %s
                    Rating: %.2f
                    Fecha de lanzamiento: %s
                    ***********************
                    """, g.getName(), g.getDescription(), platforms, genreValidation, g.getRating(), g.getReleaseDate());
    }
}
