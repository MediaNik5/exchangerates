# Exchange Rates service

To run it, you should have docker installed.

You might run existing docker image:
```
docker run -p 8080:8080 -d medianik5/exchangerates
```

If you have docker and jdk 11, you might create docker image and run container using these commands(instead of 'java' you could use full path to java executable):
```
./gradlew clean build
docker build --build-arg JAR_FILE=build/libs/*.jar -t exchangerates .
docker run -p 8080:8080 exchangerates
```
If you don't have docker, but have jdk11, then run these commands sequentially in root folder:
```
./gradlew clean build
java -jar build/libs/exchangerates-1.0.0.jar
```
