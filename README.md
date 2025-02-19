# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAE5M9qBACu2GADEaMBUljAASij2SKoWckgQaIEA7gAWSGBiiKikALQAfOSUNFAAXDAA2gAKAPJkACoAujAA9D4GUAA6aADeAETtlMEAtih9pX0wfQA0U7jqydAc45MzUyjDwEgIK1MAvpjCJTAFrOxclOX9g1AjYxNTs33zqotQyw9rfRtbO58HbE43FgpyOonKUCiMUyUAAFJForFKJEAI4+NRgACUh2KohOhVk8iUKnU5XsKDAAFUOrCbndsYTFMo1Kp8UYdKUAGJITgwamURkwHRhOnAUaYRnElknUG4lTlNA+BAIHEiFRsyXM0kgSFyFD8uE3RkM7RS9Rs4ylBQcDh8jqM1VUPGnTUk1SlHUoPUKHxgVKw4C+1LGiWmrWs06W622n1+h1g9W5U6Ai5lCJQpFQSKqJVYFPAmWFI6XGDXDp3SblVZPQN++oQADW6ErU32jsohfgyHM5QATE4nN0y0MxWMYFXHlNa6l6020C3Vgd0BxTF5fP4AtB2OSYAAZCDRJIBNIZLLdvJF4ol6p1JqtAzqBJoIcDcujheT576V5LD99A7FiChT5iWw63O+46tl8LxvMskGLpgIFAUUaooOUCAHjysL7oeqLorE2IJoYLphm6ZIUgatJvqMJpEuGFocjA3K8gagrCjAoriq60pJpeqEQhSPi3HaAraI6zoEqRLLlJ63pBgGQYhtx5qRox0YwLGwZiURnZIeUOE8tmuaIecBa8ShJRXK+I6jH+NZBrOzbwW2gGdtkPYwP2g69NZ4G2c5XzTo584BUunCrt4fiBF4KDoHuB6+Mwx7pJkmDuReFnXtIACiu7ZfU2XNC0D6qE+3RBY26DtshekwBVc4mUCHayvxMAYfYiXYQlvp4RihFysRkl0WRMAlEgABmliaQpdaVWgtFMm6DHlMxtr1VVykRi1TryjAkJgEJSTrWg4mJkNi3SUYKDcJk03TkpUkqYUloyNdFKGJp8YDbppkljhiVGQgea-SC22gQBV6g4U6VgH2A5DmFK6eJFG6Qrau7QjAADio6sslp5peezBg2mFRY-lRX2KO5UOXN1VsrVx2NambJEeh0I46MqjYRzuO9QRp2DTIj3uqNlATVN8nHQtZoRs9jGrXVtMNZtnZs3tgnCUzOkkcNl3kmAnNqLCMv0apK08raRtsWEVOjAAktIgs-U1abILERuqIDwOu2rkNXFMdtqOMlT9EHjshwAjL2ADMAAsTwnpkBoVhMXw6AgoANin75p08QcAHKjmnewwI0EPUM1pww3D3k9IHuMhxUYejhH5TR-Hicpfq1H3H06eZyA2e9ysXyF8X-el+XZjhcj66BNgQnYNw8C6pk2Ojik3eEzkxN8ZZlS1A0lPU8EyvoEODejEX-kTpMFfHMmIPlMdl99OPt9TPfzNmfv4IwLJTIRtYRwDXigI2-MsTO11hdUkYsoASzuufeaoY9ZPXZBbXkTNVbmXVvtQ6StZoNR1udWW5FDajlhB-FApslrm0VkbIUttRw3zEDg7a-9FTKmgaQ8MHowHAKDg9NBcsMHqWttpb65laqgK9EA0c3sf5V33qBK+KA24wA7nHGAD9lFdl3rXN+4dpBR1jtoxGEV54BEsNdDCyQYAACkIA8g3qMQIGcs473MKzf2h9KR3haEHGmRCL69GXsAGxUA4AQAwlAWYxjdE1WfoQmcc0hzhMidE2J8TW7SABCDHxrUABWzi0DANkXqCBKA0R9R4cLER5QxqTSQSElBOD5aYLWsg1BsCtp-12vgrW3SSH1N6eQwRuTaE8Q6Qwze7FjE9Nln7VqgykgSPkHUza5Q-BaHkaMKhkzFlmw6TAGoJRkgIPXow+ZuToFP1duUJxPIqk5iBkoqGmU0w9ESW5ImhjegWLnlFAIXgIldi9LAYA2Bl6EHiIkLeBMYaFIPhUHKeUCpFWMPTe5qZSjvORf-EA3A8Am02SLGSxKoCMlJe0jBV0bqGGAMqIw2gYAuN2bofQYgdY4uBOURAELFFIWWQfb52Lq5-M8vDAFM8VxAA)

## Modules

The application has three modules.

- **Client**: The command line program used to play a game of chess over the network.
- **Server**: The command line program that listens for network requests from the client and manages users and games.
- **Shared**: Code that is used by both the client and the server. This includes the rules of chess and tracking the state of a game.

## Starter Code

As you create your chess application you will move through specific phases of development. This starts with implementing the moves of chess and finishes with sending game moves over the network between your client and server. You will start each phase by copying course provided [starter-code](starter-code/) for that phase into the source code of the project. Do not copy a phases' starter code before you are ready to begin work on that phase.

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared test`      | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

## Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```
