# java-performance-sample

## Install & Start Visual VM
https://visualvm.github.io/

## Build with Maven

**1. Clone the application**

**2. Build the app using the Maven wrapper**

```bash
cd java-performance-sample
mvn package
```

**3. Run the application ::8081** 
```bash
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

Alternatively, you can run the app directly without packaging

```bash
mvnw spring-boot:run
```

## Review in Visual VM
1. Open process with Visual VM
2. Test avaliable endpoints & actions 

/generatethreads - Spawn 10 inactive threads

/cpuload?time=120&load=0.8 - Maintain 80% CPU for 120 Seconds

/deadlock - Trigger Deadlock

/generateobjects - Create Memory Leak

/addmemory - consume heap memory

/release - release memory
