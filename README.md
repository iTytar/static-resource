Static resource management

Build:
mvn clean package

Run:
mvn spring-boot:run

static resources will be accessible by http://localhost:8080/
there are two kind resources. first is embedded resources. it's located in jar file (in project path 'src/main/resources/static'). second is external resources. it's located on the local file system (for current configuration '.webroot'). for example try http://localhost:8080/index.html - 'Hello World' will be displayed in browser. 'index.html' is embedded resoure. and then try http://localhost:8080/index2.html - 'Hello World2' will be displayed. 'index2.html' is external resource. External resources can be modified in runtime. But embedded not. External resource has higher priority than embedded.

to manage external resources you can use REST API. For that open in the browser http://localhost:8080/swagger-ui.html
