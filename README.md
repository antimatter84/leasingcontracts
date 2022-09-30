# Leasing Contracts

This is a SpringBoot demo application for managing leasing contracts along with customers and vehicles.

## Prerequisites

This application has been developed and tested under Linux Mint / Ubuntu.
Final tests ran in Ubuntu 22.04 Jammy Jellyfish, which is the recommended environment for running the app.

### Install Git & JDK 11

```bash
sudo apt install git openjdk-11-jdk 
```

### Install latest Gradle

Ubuntu comes with Gradle 4.4, so let's install something fresher.

```bash
sudo add-apt-repository ppa:cwchien/gradle
sudo apt update
sudo apt install gradle
```

Source: https://launchpad.net/~cwchien/+archive/ubuntu/gradle

### Install Docker Engine

Please go to https://docs.docker.com/engine/install/ubuntu and follow steps 1 to 3 under "Install using the repository"
as well as step 1 from "Install Docker Engine".
Make sure to install all the listed packages including the compose plugin.

Add the current user to the 'docker' group:

```bash
sudo usermod -aG docker $USER
```

Logoff/logon or reboot the machine. Try the latter if the following test fails.

Run the hello-world example from docker to test if docker can be run as user.

```bash
docker run hello-world
```

## Usage

Clone this Git repository and move into the new project directory. Then build the application with:

````bash
gradle build
````

Start the MariaDB instance with the docker compose file.

```bash
docker compose up
```

Once the database container is up, run the application JAR from the build/lib directory:

```bash
java -jar build/lib/leasingcontracts-0.0.1-SNAPSHOT.jar
```

The application should now run and e.g. apply the Flyway migrations.

The endpoints can be reviewed in the code of the REST controllers, if one wants to test the API with a REST client such
as Postman/Insomnia/etc.

## Reasons for chosen solution

Besides the given technical constraints, the solution is based on the typical common-sense approach for SpringBoot
applications:

- REST Controller
- Service layer
- Repository layer
- Database (or other means of persistence)

Data transfer objects were used make communication between client and server more efficient. They are generally used to
consolidate data into a single response, where otherwise multiple requests would be necessary.

Some unit and integration tests were also added. For the latter, the Testcontainers library is being included to easily
facilitate throw-away databases.
However, the integration tests do currently not run during 'gradle build' and were therefore excluded from the build
run. They run successfully when started from the IDE.