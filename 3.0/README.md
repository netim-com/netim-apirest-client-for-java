# APIRest-client-java 3.0

## Description
This client is designed to communicate with the netim REST API.
It can be integrated into all your java projects.

## Compilation
```bash
mvn package
```

The file usable for projects is the "APIRest-1.0-jar-with-dependencies.jar" file in the target directory (after compilation)

## Generating the javadoc
```bash
mvn javadoc:javadoc
```

## Configuration
The configuration is done via the conf.xml file where you can specify the API URL, your login, the secret and the language of your choice.
For the login and the secret you can also override them at the instantiation of the object by giving the login and the secret to the constructor

```xml
<?xml version="1.0" encoding="utf-8"?>
<configuration>
    <url>http://oterest.netim.com/3.0/</url>
    <login>login</login>
    <secret>secret</secret>
    <preferences>
        <lang>EN</lang>
    </preferences>
</configuration>
```

## Usage
To communicate with the API, instantiate an APIRest object and use its methods to communicate.

The class implements the AutoCloesable interface, which means that if you don't want to handle the connection/disconnection yourself, you can simply use the object like this:

```java
try (APIRest api = new APIRest()) {

    //Example
    api.hello();

}
```

Otherwise you will have to close the connection yourself after each use of the object.

```java
APIRest api = new APIRest();

api.hello();

api.sessionClose();

// From here the session is closed. If you want to reuse the session later you will have to do api.sessionOpen();
```