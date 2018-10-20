# Hiatus for Spring Boot
A Spring Boot 2 starter for graceful work interruption or shutdown.

[ ![Download](https://api.bintray.com/packages/jihor/maven/hiatus-spring-boot/images/download.svg) ](https://bintray.com/jihor/maven/hiatus-spring-boot/_latestVersion)
[ ![CircleCI](https://circleci.com/gh/jihor/hiatus-spring-boot/tree/master.svg?style=shield) ](https://circleci.com/gh/jihor/hiatus-spring-boot/tree/master)

Current version is written for Spring Boot 2.
Older versions of this starter (0.x and 1.0) are written for Spring Boot 1.x and have different paths, see previous readme versions.

#### What is Hiatus for Spring Boot?
**Hiatus for Spring Boot** is a starter that allows a Spring Boot application to... go on hiatus :) i.e. return an 'OUT OF SERVICE' result in respond to a health check, while allowing in-flight requests to complete, and also provides a way to keep score of these requests. The basic use cases are as follows:

__A. Graceful shutdown__
1. Tell the service instance to go on hiatus (invoke `/actuator/hiatus-on`). If you are behind HAProxy / Nginx / any other decent load balancer or discovery server that checks your `/actuator/health` endpoint, this means the load balancer will cease sending new requests to this service (or the discovery server will mark this instance as "down". Anyway, the instance will be taken out of load balancing).
2. Wait until the count of in-flight requests reaches zero. 
3. Now the instance can be restarted with no requests in danger.

__B. Hiatus__
1. Same as in (A)
2. When it's time to put the service back to work, invoke `/actuator/hiatus-off`. Service will then be taken back to load balancing

#### Spring Boot already has `/actuator/shutdown`, why not use it?
`/actuator/shutdown` destroys the whole Spring context, leading to failed requests. The goal of this starter is to allow such operations without interfering with the application context, relying on load balancing only.


### Download
##### Gradle
```
repositories {
    jcenter()
}

dependencies {
    compile group: 'ru.jihor.hiatus-spring-boot', name: 'hiatus-spring-boot-starter', version: '<version>'
    // or
    // compile 'ru.jihor.hiatus-spring-boot:hiatus-spring-boot-starter:<version>'
}
```
##### Maven
```
<dependency>
    <groupId>ru.jihor.hiatus-spring-boot</groupId>
    <artifactId>hiatus-spring-boot-starter</artifactId>
    <version>(version)</version>
    <type>pom</type>
</dependency>
```

### API Description
#### REST API

The following REST API is available when using hiatus-spring-boot-starter:

* `/actuator/hiatus-on`, method = `POST` - go on hiatus
 
    Returns:
    - `true` if the service is gone on hiatus as result of this request, `false` if it's already on hiatus  

* `/actuator/hiatus-off`, method = `POST` - go back to work

	Returns:
    - `true` if the service is resuming work as result of this request, `false` if it's already working normally
      
* `/actuator/hiatus`, method = `GET` - the health indicator which provides the information on the service state and the count of requests in processing

    Returns:
    - json like ``
{
    "paused": true,
    "count": 0
}
`` which provides the info on whether the system is paused and the count of requests in processing. Returns HTTP status 200 if working normally, 503 if on hiatus. 

#### Java API
##### UnitOfWork annotation

If this method-level annotation is present on a method, the counter of requests in processing will be incremented before the method invocation and decremented after exit.

Example:
```
@RestController
public class DemoController {
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @UnitOfWork
    public String doWork() throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        return "Done";
    }
}
```

### Demo project
1. Clone the repo and start `DemoApplication`.
2. curl the `/actuator/health` endpoint
```
$ curl -i http://127.0.0.1:8080/actuator/health
HTTP/1.1 200                                              
...
{"status":"UP","diskSpace":{"status":"UP","total":999,"free":997,"threshold":1},"hiatus":{"status":"UP","paused":false,"count":0}}
```
3. curl the `/actuator/hiatus` endpoint
```
$ curl -i http://127.0.0.1:8080/actuator/hiatus
HTTP/1.1 200 
...
{"paused":false,"count":0}
```
4. curl the `/test` endpoint a couple times without waiting for them to return 
```
$ curl -i http://127.0.0.1:8080/test > /dev/null 2>&1 &
[4] 21112
$ curl -i http://127.0.0.1:8080/test > /dev/null 2>&1 &
[5] 21114
$ curl -i http://127.0.0.1:8080/test > /dev/null 2>&1 &
[6] 21116
```
5. curl the `/actuator/hiatus` endpoint again and see in-flight requests:
```
$ curl -i http://127.0.0.1:8080/actuator/hiatus
HTTP/1.1 200 
...
{"paused":false,"count":3}
```
6. curl the `/actuator/hiatus-on` endpoint with a POST:
```
$ curl -X POST http://127.0.0.1:8080/actuator/hiatus-on
true
```
7. curl the `/actuator/health` endpoint again. Now it returns code 503:
```
$ curl -i http://127.0.0.1:8080/actuator/health
HTTP/1.1 503                                              
...
{"status":"OUT_OF_SERVICE","diskSpace":{"status":"UP","total":999,"free":997,"threshold":1},"hiatus":{"status":"OUT_OF_SERVICE","paused":true,"count":3}}
``` 
8. curl the `/actuator/hiatus` endpoint again. Now it also returns code 503:
```
$ curl -i http://127.0.0.1:8080/actuator/hiatus
HTTP/1.1 503 
...
{"paused":true,"count":3}
```
