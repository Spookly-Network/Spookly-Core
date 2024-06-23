# Spookly-Core-API
Spookly-Core-API is a comprehensive API for managing various aspects of the Spookly system, including players, teams, game phases, events, and more. This API provides interfaces and their implementations for creating and managing complex game systems in a Minecraft environment.

## Features
- Player management (online and offline)
- Team management and display customization
- Game phase management
- Placeholder handling
- Event handling and subscription
- Database connection and operations

## Installation
To use Spookly-Core-API in your project, add the following repository and dependency to your pom.xml:
```xml
<repositories>
  <repository>
    <id>spookly-repository</id>
    <name>Spookly Repository</name>
    <url>https://mvn.spookly.net/repository</url>
  </repository>
</repositories>

<dependencies>
  <dependency>
    <groupId>de.nehlen</groupId>
    <artifactId>Spookly-Core-API</artifactId>
    <version>1.0.0-SNAPSHOT</version>
  </dependency>
</dependencies>

```

## Usage
The API provides various interfaces to interact with different components of the Spookly system. Here are some examples of how to use the API:
### Player Management
```java
SpooklyServer server = // obtain SpooklyServer instance
UUID playerUUID = // player's UUID

server.getOfflinePlayer(playerUUID, offlinePlayer -> {
    // Perform operations with the offline player
});

SpooklyPlayer player = server.getPlayer(playerUUID);
// Perform operations with the online player
```
### Team Management
```java
TeamManager teamManager = server.getTeamManager();
Team team = teamManager.team(playerUUID);

if (team != null) {
    team.registerPlayer(player);
}
```
### Event Handling
```java
EventExecuter eventExecuter = server.getEventExecuter();

eventExecuter.register(SomeEvent.class, event -> {
    // Handle the event
});
```

## Contributing
We welcome contributions to Spookly-Core-API! Here's how you can contribute:

1. Fork the repository.
2. Create a new branch for your feature or bugfix (git checkout -b feature-name).
3. Make your changes and commit them (git commit -m 'Add new feature').
4. Push your changes to your fork (git push origin feature-name).
5. Create a pull request with a description of your changes.

### Code Style
Please follow the existing code style and conventions when contributing. Ensure your code is properly documented with Javadoc comments.

### Reporting Issues
If you encounter any issues or bugs, please report them on the GitHub issues page. Provide as much detail as possible, including steps to reproduce the issue and any relevant code snippets.

## License
Spookly-Core-API is licensed under the MIT License. See the LICENSE file for more information.