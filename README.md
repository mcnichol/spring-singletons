# Spring Singletons - Not Thread Safe

## Demonstration
Clone and run this project with

`git clone https://github.com/mcnichol/spring-singletons.git && cd spring-singletons`

`./gradlew bootRun`

In another terminal window, navigate to the root directory and execute `./curl-singleton-controller.sh` 

You should see an error logged to the terminal and the `SpringApplication` will receive a **System Halt** 

## What does this show?
Inside the script there are two curl commands that run simultaneously (*for the most part...we can talk semantics later*).

These curl commands hit two separate endpoints on the demo [Controller](./src/main/java/com/mcnichol/spring/Controller.java)
 * `http://localhost:8080/work?do=ThingOne`
 * `http://localhost:8080/morework?do=ThingTwo`

This [Controller](./src/main/java/com/mcnichol/spring/Controller.java) is injected with two Services (ala Spring DI):
 * [MyServiceOne](./src/main/java/com/mcnichol/spring/MyServiceOne.java)
 * [MyServiceTwo](./src/main/java/com/mcnichol/spring/MyServiceTwo.java)

Both of these services are injected with the [ComponentIsSingleton](./src/main/java/com/mcnichol/spring/ComponentShouldBeSingleton.java) object

Both Services now have a reference to the same `ComponentIsSingleton` object.

We simulate [MyServiceOne](./src/main/java/com/mcnichol/spring/MyServiceOne.java) behaving as a long-running process (by inducing a Thread Sleep) while [MyServiceTwo](./src/main/java/com/mcnichol/spring/MyServiceTwo.java) behaves as a significantly faster process. 

Each service will set the [ComponentIsSingleton](./src/main/java/com/mcnichol/spring/ComponentShouldBeSingleton.java).stringValue field to their respective values (`ThingOne` || `ThingTwo`)

It will then check the value which was just set, check if the values are the same, if not equal throws an `Exception`

## Sequence of Events

The sequence of events is (for the most part):
1. Controller endpoint */work* curled with QueryParam **ThingOne**
1. `MyServiceOne.doWork` (from */work* endpoint) triggered on Thread1 setting `ComponentIsSingleton.stringValue` -> **ThingOne**
    1. Thread1.Sleep is called for 2 seconds
1. Controller endpoint */morework* curled with QueryParam **ThingTwo**
1. `MyServiceTwo.doWork` (from */morework* endpoint) triggered on Thread2 setting `ComponentIsSingleton.stringValue` -> **ThingTwo**
1. `MyServiceTwo.doWork` checks if `ComponentIsSingleton.stringValue` is **NOT** equal to **ThingTwo** (This will evaluate false)
1. `MyServiceOne.doWork` Sleep will have elapsed on Thread1 and check if `ComponentIsSingleton.stringValue` is **NOT** equal to **ThingOne** 
    1. This will evaluate true as `ComponentIsSingleton.stringValue` is **ThingTwo** (Set from Thread2)
1. `MyServiceOne.doWork` throws Exception with expected value **ThingOne** and actual value **ThingTwo** exiting SpringApplication

However, if you uncomment the different `@Scope` types in the `ComponentIsSingleton.stringValue` you will see how these will provide new objects:
 * For each object created
    * `@Scope("prototype")`
 * For each HTTP request (Web aware apps)
    * `@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)`
