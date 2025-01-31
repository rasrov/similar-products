# Similar products
<details>
  <summary><strong>Table of contents</strong></summary>

* [ 🏁 Getting Started ](#-getting-started)
    * [ {...} Spring-doc ](#-spring-doc)
    * [ 📞 Cache ](#-cache)
    * [ 🔄 Circuit-breaker ](#-circuit-breaker)
    * [ Testing ](#testing)
* [ 📦 Similar product microservice ](#-similar-product-microservice)

</details>

## 🏁 Getting Started

To start the application it's pretty easy.

Keep in mind the application doesn't run on the default port, runs on port `5000`.

This application has been developed under the hexagonal architecture, but not under DDD. Since this microservice does not have a strong domain layer. Instead, I have chosen to implement the logic in chain-of-responsibility model.

In this case, our api-client module is the one in charge of making requests to the Mock service.

On the other hand, the application layer is responsible for managing and handling calls reactively.

And finally, the infrastructure layer. It is responsible for providing an access point to our service.

We can see more details here [ {...} spring-doc ](#-spring-doc).

## {...} Spring-doc
Spring-doc has been implemented to be able to see the structure of the request offered by the service.

First we must start the application, then we can access to http://localhost:5000/swagger-ui/index.html and see all the API definitions.

### 📞 Cache

This project is implementing cache in internal memory to make more fast recurrent calls to the API.

This is implemented with `caffeine` and configured into `application` module to make sure we are using a single instance at the application level.

If necessary, we can modify the time the data is stored in cache and the total number of records.

We can do this with the `cache.duration` and `cache.maxsize` properties that we have defined in the properties in the `infrastructure` module.

### 🔄 Circuit-breaker

Since we are making requests to an external service from our microservice, I have decided to implement a circuitbreaker to control failures and not saturate the external services when they are not operational.

The configuration being used is quite simple. We can see it in the properties within the `infrastructure` module.

They are all the properties that hang from `resilience4j` properties.

We currently have this covered with the `@Circuitbreaker` annotation within our `api-client` module.

### Testing

Application coverage has been made in all modules. We have both unit and integration tests.

For the api-client and application modules, unit tests with junit5 and mockito have been used.

In the infrastructure module, integration tests have been carried out, using MockMvc. Above all, trying to control the exceptions that may occur.