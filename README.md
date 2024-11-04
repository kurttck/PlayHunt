```mermaid
classDiagram
  class Game {
    +Integer id
    +String name
    +String description
    +LocalDate releaseDate
    +String poster
    +Double rating
    +Genre genre
    +List~Platform~ platforms
  }
  class Platform {
    +String id
    +String name
    +String image
    +List~Game~ games
  }

  class Game_Platform {
    +Integer gameId
    +String platformId
  }

  Game "1" -- "*" Game_Platform : "relaciona con"
  Platform "1" -- "*" Game_Platform : "relaciona con"
