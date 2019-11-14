# Static resource management

## Build

Use maven:
```bash
mvn clean package
```

Or build docker image:
```bash
docker build -t tyt/static-resource:latest .
```

## Run

Use maven:
```bash
mvn spring-boot:run
```

Or run java -jar
```bash
java -jar ./target/static-resource-*-*.jar
```

Or run docker image:
```bash
docker run -it --rm\
 -p 8080:8080\
 -v $PWD/.webroot:/app/.webroot\
 tyt/static-resource:latest
```

# Howto

static resources will be accessible by http://localhost:8080/
there are two kind resources. first is embedded resources. it's located in jar file (in project path 'src/main/resources/static'). second is external resources. it's located on the local file system (for current configuration '.webroot'). for example try http://localhost:8080/index.html - 'Hello World' will be displayed in browser. 'index.html' is embedded resoure. and then try http://localhost:8080/index2.html - 'Hello World2' will be displayed. 'index2.html' is external resource. External resources can be modified in runtime. But embedded is not. External resource has higher priority than embedded.

to manage external resources you can use REST API. For that open in the browser http://localhost:8080/swagger-ui.html
