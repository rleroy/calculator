# Calculator


## The Application

The application is a simple console calculator.
You can run it using the command "java -jar".
Operations are read from the console input and results sent to the console output.

## The code

This project is organized into 2 submodules following an hexagonal architecture :

- http://alistair.cockburn.us/Hexagonal+architecture
- https://blog.xebia.fr/2016/03/16/perennisez-votre-metier-avec-larchitecture-hexagonale/

### Domain

This modules contains the "business" part of the application and is separated in 2 packages :

- core : Contains the "soft core" of the Hexagon. The business logic lies here.
- shell : Contains the "hard shell" of the Hexagon. The outside world will use it.

### Infra

This modules contains the "infrastructure" part of the application and is separated in 2 packages :

- API (Application Provider Interfaces) : implementation(s) of application(s) using the domain.
- SPI (Service Provider Interfaces) : implementation(s) of components used by the application(s).

## The Kata

### Goal

The aim of this kata is to migrate this simple application from JDK8 to JDK11.
During this process we will discover some new features introduced in JDK9, JDK10 and JDK11.

References :
- http://openjdk.java.net/projects/jdk9/
- http://openjdk.java.net/projects/jdk/10/
- http://openjdk.java.net/projects/jdk/11/

### Checklist

- [x] Build the project using JDK11.
- [x] Handle JAXB removal (JDK11 - JEP 320).
- [x] Hide domain.core package from infra module using the new Module System (JDK9 - JEP 261).
- [x] Use the new process API (JDK9 - JEP 102).
- [ ] Use variables in the try-with-resources statement (JDK9 - JEP 213).
- [ ] Use diamond operator in conjunction with anonymous inner classes (JDK9 - JEP 213).
- [ ] Add an interface private method (JDK9 - JEP 213).
- [ ] Create an immutable Set (JDK9 - JEP 269).
- [ ] Stream optionals (JDK9 - JEP 269).
- [ ] Use the new HTTP Client API (JK9 - JEP 110 / JDK11 - JEP 321).
- [ ] Local-Variable Type Inference (JDK10 - JEP 286).
- [ ] Use the new Flow API (JDK9 - JEP 266).

### Step 4 : Small language changes.

JEP 213 offers some language improvement you will experiment :

- Use variables in the try-with-resources statement : 

In `MultiplicationService.tryCompute` try to extract the inner try.
This will avoid 2 levels indentation and stress out the exception thrown.

- Use diamond operator in conjunction with anonymous inner classes :

In the constructor of the `Calculator`, the declaration of the division service can now be simplified.

- Add an interface private method :

You can introduce a private method and remove duplication in `CalculatorStorage`.

