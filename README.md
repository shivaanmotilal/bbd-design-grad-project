# Virtual Wallet

A Payments API for a virtual wallet. The api needs to be able to create a profile,
add money to my wallet, pay another profile, use a two-step Payment model (authorise
and then settle).

## Getting Started

Download the project or clone using [git](https://git-scm.com/downloads):

```shell
git clone git@github.com:Zentren/bbd-design-grad-project.git
```

### Prerequisites

You will need to install a [Java 11 SDK](https://adoptopenjdk.net/) and optionally
[Maven](https://maven.apache.org/download.cgi).

### Running on IntelliJ
Create an Application Run Configuration.
Then Run:
```shell
mvn clean install
```
to autogenerate the Mapping classes using MapStruct. Thereafter run the project.

### Building

To build the project, you will need to run the following command in the project
root:

```shell
mvn package
```

### Running

To run the project, you will need to run the following command in the `target` folder
found in the project root after building:

```shell
java -jar wallet-0.0.1-SNAPSHOT.jar
```
### MySQL Console Configs

Use Username and password as "root".
SQL script in startUpScripts folder

### H2 Console Configs

Change this field on localhost:8080/h2-console/login.jsp
```
JDBC URL : jdbc:h2:mem:testdb
```
