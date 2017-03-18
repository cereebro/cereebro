![Build status](https://travis-ci.org/cereebro/cereebro.svg?branch=master)

# cereebro

The goal of Cereebro is to automate the documentation of components' dependencies in a distributed system.

## Why

When building a distributed system, it quickly becomes hard to keep track of every component and its dependencies.    
Writing architecture documents and drawing diagrams is cool, but these pieces of art eventually get obsolete after a few time. Keeping software documentation up-to-date is an ageless problem.  
Project Cereebro answers it by generating a map of a system at runtime : 

 * by simply adding a Spring Boot starter, the context of each application is analyzed to detect its  relationships with other components, providing a fragment of the whole system. This information is published through a Snitch endpoint.
 * the Cereebro server resolves the fragments from every Snitch to reconstitute the big picture  

The name Cereebro is inspired by the [X-Men's Cerebro](https://en.wikipedia.org/wiki/Cerebro) device, which allows its user to locate mutants accross the world.

We named it cere**e**bro because : 

 * cerebro.io wasn't available
 * no legal issues with Marvel comics
 * [that's the way americans pronounce cerebro anyway](https://www.youtube.com/watch?v=EFyYvdvUEqo)
 
## Continuous Integration

 * [Travis CI](https://travis-ci.org/cereebro/cereebro)
 * [SONAR](https://sonarqube.com/dashboard?id=io.cereebro%3Acereebro)

## Code Conventions

Java :
 
  * eclipse formatter : "Java conventions with spaces" (I don't know about IntelliJ)
  * line width : 120
  * indent using spaces only
  * auto-format all the things
  * Use off/on tags to keep the formatter off sections that need specific formatting (like builders)
  * test all the things

XML :

  * indent using 4 spaces
  * line width : 120

## License

Cereebro is Open Source software released under the [Apache 2.0 license](http://www.apache.org/licenses/LICENSE-2.0.html).
