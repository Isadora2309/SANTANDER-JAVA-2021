Hi ✨ I'll explain what went down in this project with architecture based on microservices using Spring Cloud down here 👇🏻

<h1> 🛠️ Spring Initialzr </h1>
Here you can generate your project from various precast configurations, as well as add dependencies. All with a very intuitive user interface - a mega tool!

🧷 [Know tool](https://start.spring.io/) | 🧷[Learn about](https://spring.io/)
<h1> 💡 Maven x Gradle </h1>
At first, there's not much difference as both can store dependencies and download them, but the <font style="background-color: #ccff99"> Maven application consumes libraries that can only be replaced by a dependency of a certain version </font>; the <font style="background-color: #ffff99"> Gradle application allows you to select customizable dependencies as well as replacement rules that can be declared once </font>, dealing with unwanted dependencies throughout the project.

That's why the application was made in Gradle.
<h1> 📑 Added dependencies </h1>

<h3> 📝 Spring Boot Actuator </h3>
It supports built-in (or custom) _endpoints_ that allow you to monitor and manage your application - such as application health, metrics, sessions, etc - in other words it helps in building a microservices framework.
<h3> 📝 Spring Web </h3>
Used for creating web apps, including RESTful, using Spring MVC. Use Apache Tomcat as the default built-in container.
<h3> 📝 Spring Data Elasticsearch (Access+Driver) </h3>
A RESTful search and analysis engine delivered with Spring Data Elasticsearch.
<h3> 📝 Lombok </h3>
Java annotation library that helps reduce boilerplate code - that bunch of constructor, getter and setter methods.

<h1> 📁 Class Product </h1>
Here are the attributes that configure the product's characteristics:

`public class Product{`
`	 private Long id;`
` 	private String name;`
`	private Integer amount;`
`}`

It's just that. No initializing constructor, empty constructor, getters and setters, exactly because the Lombok dependency does all this, just do the following (before 𝚙𝚞𝚋𝚕𝚒𝚌 𝚌𝚕𝚊𝚜𝚜):

`@Getter @Setter @AllArgsConstructor @NoArgsConstructor `

But, you need to do a few things as a result of **ElasticSearch**, such as the implementation of the notation:

` @Document(indexName = " ", type = " ")  `

It concerns the index where the information will be stored, and the type of it. Another little thing is:

` @Id  `

It specifies the field, almost like a primary key, as it is unique.

<h1> 📁 Classe ProductRepository </h1>
Vamos precisa de algo que gerencie todas essas informações, certo? Gravar, consultar, realizar todo o CRUD. No Spring o responsável por isso são os 𝚛𝚎𝚙𝚘𝚜𝚒𝚝𝚘𝚛𝚒𝚎𝚜. Correspondem a gerenciadores de entidades (o produto no caso).

Dentro do Spring, este 𝚛𝚎𝚙𝚘𝚜𝚒𝚝𝚘𝚛𝚢 não precisa ser necessariamente uma classe, pode ser apenas uma interface. Ao criá-la, podemos dar a funcionalidade à ela que são herdadas do framework:

` public interface ProductRepository extends crudRepository { }`

Ao realizar isso, temos de especificar a classe e a chave primária:

` public interface ProductRepository extends crudRepository<Product; Integer> { }`

<h1> 📁 Class ProductController </h1>

Now we need to expose this as an endpoint or REST interface. Thus, it is necessary to use controllers:

` public class ProductController { }`

Done! The next step is to tell Spring that everything that will be exposed will be part of the REST services, for that we can use the following notation:

` @RestController `

It is also interesting to use a route for this service, right? So the following notation comes in handy:

` @RequestMapping(value = "/product)`

<h2> 🖋️ Métodos </h2>

First service will be the one to create an entity. First we put `Product` as a return, then the method name (feel free!), we need to say that it will respond through the HTTP term `.POST`, in the body of this request it will have a product entity:

`@RequestMapping(method = RequestMethod.POST)`
`	Product create(@RequestBody Product product){ }`

Now we need to save this product entity, so let's use the repository:

`private ProductRepository productRepository;`

But don't worry, Spring's magic starts now, injecting the `ProductRepository` instance into this class attribute, via the `@Autowired` notation:

`@Autowired`
`private ProductRepository productRepository;`

Here, therefore, the inversion of control takes place. Then we return to the first method built in order to save the product and return the product that was saved:

`@RequestMapping(method = RequestMethod.POST)`
`	Product create(@RequestBody Product product){`
`		return productRepository.save(product);`
`}`

Right! Let's consider searching for a product by id:

` Product findById( ) { }`

But we have to pass in the id, and we can do that again using the ` @RequestMapping ` notation with the url:

`Product findById(@PathVariable Integer id){`
`	return productRepository.findById(id);`
`}`

You can also specify that it will respond to the `.GET` verb, finally we have the following:

`@RequestMapping(value="/{id", method = RequestMethod.GET)`
`Product findById(@PathVariable Integer id){`
`	return productRepository.findById(id);`
`}`

Have you seen that it's giving an error right? It's because the return of `finById( )` is optional <font style="background-color: #ccff99"></font> and we are returning a `Product`, because it might not exist. To avoid all those `null` treatments Java 8 included the `Optional` class, which solves our problem like this:

`@RequestMapping(value="/{id", method = RequestMethod.GET)`
`Optional<Product> findById(@PathVariable Integer id){`
`	return productRepository.findById(id);`
`}`

<H1> ⚙️ Config Server </H1>

Let's go back to Spring Initialzr and create the Config Server, just in case the services grow and management needs to be more sophisticated. It acts as a network external to that of microservices as it requires a larger service.

The dependencies used are almost the same as the previous ones: Lombok, Srping Actuator and Spring Web. However, there is the addition of **Config Server** which acts as a management center for configuration via Git, SVN or HashiCorp Vault.

So we import it into the project. As I'm using IntelliJ, I did the following:

` File > New > Module from Existing Sources... > `

Here you will go to your computer's file manager and copy the file path, pasting it into the field given to. A window will open asking if you want to create a module with existing sources (_Create module from existing sources_) or import a module from an external model (_Import module from external model_) and this is the one you will select, in addition to the Gradle icon, which nor the image below:

![](https://i.pinimg.com/564x/83/5c/d5/835cd53ca3bef59c68162867c70b989e.jpg)

<h1> 📁 Class ConfigServerApplication </h1>

The main file containing the word `main` needs some settings to enable all the features we want. Let's start with the `@SprinBootApplication` notation which initializes all these settings, and to start configuring them we use the `@EnableConfigServer` notation.

<h1> 📄 File application.yml - ConfigServer</h1>

In this file we configure the location where the application will "run". Here it will "run" on the 8888 ordinance:

`server:`
`	port: 8888`

Now Spring has to pull these settings from somewhere and for that we need to configure. In our case, it will pull from GitHub:

`spring:`
`	cloud:`
`		config:`
`			server:`
`				git:`
`					uri:`

So, we enable ConfigServer and tell the application where it has to fetch the config file.

<h1> 📄 File application.yml - Project</h1>
We need to define a name for the application, since it is through it that the other application will look for:

`spring:`
`	application:`
`		name: product-catalog`

We also need a configuration so that `product-catalog` can connect to the ConfigServer:

`cloud:`
`	 spring:`
`		uri: http://localhost:8888`

<font style="background-color: #ffcc99">BUT, the `product-catalog` application needs a `Client` to connect to the ConfigServer</font>. Let's solve it as a dependency, like **ConfigClient**. For that we need to generate again an application with all the previous configurations of **ConfigServer** adding the **ConfigClient**.

<h1> 🔎 Adding Dependencies</h1>
Let's go to our project's `build.gradle` file and we'll add `implementation 'org.springframework.cloud:spring-cloud-starter-config'` to the dependency block. We did this because we don't have a Spring Cloud application yet, we also have to add two other extensions:

`ext{`
`	set("springCloudVersion", "Hoxton.SR3")`
`}`

`dependencyManagement{`
`	imports{`
`		mavenBom"org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}"`
`	}`
`}`

They are only needed if we are going to import something from SpringCloud.

<H1> ⚙️ Service Discovery </H1>

Same logic as ConfigServer, same logic, but it <font style="background-color: #ffff99">acts as an intermediary between ConfigServer and microservices</font>. Going back to Spring Initialzr let's put the dependencies: **ConfigClient** and **Eureka Server** - spring-cloud-netflix Eureka server.

<h1> 📁 Class ServiceDiscoverApplication </h1>

After the same process of importing ConfigServer, we go to the main file and put the notation `@EnableEurekaServer`.

<h1> 📄 File application.yml - ServiceDiscover</h1>

Exactly the same thing as the project `.yml`:

`spring:`
`	application:`
`		name: service-discovery`

`cloud:`
`	config:`
`		uri: http://localhost:8888`

<h1> 🔎 Adding Dependencies</h1>
Let's go to our project's `build.gradle` file and we'll add `implementation 'org.springframework.cloud:spring-cloud-starter-netflix-eureka-server'` and `compiled("redis.clients:jedis")` to the block of dependencies.

<H1> 🧰 Gateway </H1>
It is one of the fundamental pieces for a microservices architecture, as it helps to expose the service, but it <font style="background-color: #e6ff99">cannot speak directly, as it can lead to a very complex communication</font > - and that's not nice for the user or the Front-End programmer.

It should simplify, but also, if you want (or accidentally due to lack of in-depth knowledge), it can complicate matters.

Returning to Spring Initialzr, let's select the following dependencies: **Spring Boot Actuator**, **Config Client** and **Gateway**.

<h1> 📄 File application.yml - Gateway </h1>

Performing the same process:

`spring:`
`	application:`
`		name: gateway`
`	cloud:`
`		config:`
`			uri: http://localhost.8888`

<h1>✨ End! </h1>
Thank you very much if you have read this far! 🎉
