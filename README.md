## Spring Boot with Ollama (AI integration to help desk)

**Problem:** 
>Huge traffic of new comments in help desk platforms.

**Solution:** 
- Integrate AI to separate comments from questions and issue support tickets if needed.
- Answer simpler questions with help of AI (not covered in this project).


Topics covered by this project:
* AI integration in Spring Boot application.
* Handling asynchronous requests in Spring Boot.
* Running (LLMs) on your local machine with help of Ollama tool.

NOTE: For this project deepseek-r1:1.5b model was applied. This model MUST be used,
otherwise corresponding model name must be changed in application.properties file.

**AI response time and precision far from expectation, however resources are limited.**

#### SET UP:

1. Download and install Ollama application from https://ollama.com/.
2. Run command in terminal window (approximately 1.1GB of disk space is required):
>ollama pull deepseek-r1:1.5b
3. Close ollama application in the background and run command in terminal window:
>ollama serve
4. Open other terminal window and enter a command:
>ollama run deepseek-r1:1.5b
5. Run Spring Boot application:
>SpringBootWithOllamaApplication

***
**Postman requests in json file format provided with a project.**
***

H2 database console (default connection):
> http://localhost:8080/h2-console

Documentation with swagger:
>http://localhost:8080/swagger-ui/index.html


Frontend:
>https://github.com/pauliustechin/AI-integration-to-help-desk-app-react.git
>
> Port: 5173

In case if React is running on different port it must be changed in application.properties file
to corresponding port.



