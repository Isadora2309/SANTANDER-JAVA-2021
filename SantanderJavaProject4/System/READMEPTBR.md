Olá ✨ Vou explicar o que rolou neste projeto com arquitetura baseada em microsserviços usando Spring Cloud aqui embaixo 👇🏻

<h1> 🛠️ Spring Initialzr </h1>
Aqui você pode gerar seu projeto a partir de várias configurações pré-moldadas, além de adicionar dependências. Tudo com uma interface bem intuitiva ao usuário - uma mega ferramenta!

🧷 [Conhecer ferramenta](https://start.spring.io/) | 🧷[Aprender sobre](https://spring.io/)
<h1> 💡 Maven x Gradle </h1>
A princípio, não há muuuita diferença já que ambos podem armazenar dependências e baixá-las, mas, a aplicação <font style="background-color: #ccff99"> Maven consome bibliotecas que só podem ser substituídas por uma dependência de uma determinada versão</font>; já a aplicação <font style="background-color: #ffff99"> Gradle permite selecionar dependências personalizáveis além de regras de substituição que podem ser declaradas uma vez </font>, lidando com dependências indesejadas em todo o projeto.

Por isso, a aplicação fora feita em Gradle.
<h1> 📑 Dependências Adicionadas </h1>

<h3> 📝 Spring Boot Actuator </h3>
Suporta _endpoints_ integrados (ou personalizados) que permitem monitorar e gerenciar seu aplicativo - como a integridade do aplicativo, métricas, sessões, etc - em outras palavra auxilia na construção de uma estrutura de microsserviços.
<h3> 📝 Spring Web </h3>
Utilizado para criação de aplicativos web, incluindo RESTful, usando Spring MVC. Usa Apache Tomcat como o contêiner integrado padrão.
<h3> 📝 Spring Data Elasticsearch (Access+Driver) </h3>
Um mecanismo de pesquisa e análise RESTful distribuído com Spring Data Elasticsearch.
<h3> 📝 Lombok </h3>
Biblioteca de anotação Java que ajuda a reduzir o código clichê - aquele monte de construtor, métodos getter e setter. 

<h1> 📁 Classe Product </h1>
Aqui estarão os atributos que configuram as características do produto:

`public class Product{`
`	 private Long id;`
` 	private String name;`
`	private Integer amount;`
`}`

E apenas isso. Sem construtor inicializando, construtor vazio, getters e setters, exatamente porque a dependência Lombok, já faz tudo isso, basta apenas realizar o seguinte (antes de 𝚙𝚞𝚋𝚕𝚒𝚌 𝚌𝚕𝚊𝚜𝚜):

`@Getter @Setter @AllArgsConstructor @NoArgsConstructor `

Mas, é preciso fazer algumas coisinhas em decorrência do **ElasticSearch**, como a implementação da notação:

` @Document(indexName = " ", type = " ")  `

Que diz respeito ao índice onde as informações serão armazenadas, e o tipo delas. Outra coisinha é o: 

` @Id  `

Ele especifica o campo, quase que uma chave primária, já que é única.

<h1> 📁 Classe ProductRepository </h1>
Vamos precisa de algo que gerencie todas essas informações, certo? Gravar, consultar, realizar todo o CRUD. No Spring o responsável por isso são os 𝚛𝚎𝚙𝚘𝚜𝚒𝚝𝚘𝚛𝚒𝚎𝚜. Correspondem a gerenciadores de entidades (o produto no caso).

Dentro do Spring, este 𝚛𝚎𝚙𝚘𝚜𝚒𝚝𝚘𝚛𝚢 não precisa ser necessariamente uma classe, pode ser apenas uma interface. Ao criá-la, podemos dar a funcionalidade à ela que são herdadas do framework:

` public interface ProductRepository extends crudRepository { }`

Ao realizar isso, temos de especificar a classe e a chave primária:

` public interface ProductRepository extends crudRepository<Product; Integer> { }`

<h1> 📁 Classe ProductController </h1>

Agora precisamos expor isso como um endpoint ou interface REST. Assim se faz necessária a utilização de controllers:

` public class ProductController { }`

Feito! O próximo passo é falar para o Spring que tudo que será exposto será parte dos serviços REST, para isso podemos utilizar a seguinte notação:

` @RestController `

Também é interessante utilizar uma rota para este serviço, né? Então a notação a seguir se faz útil:

` @RequestMapping(value = "/product)`

<h2> 🖋️ Métodos </h2>

Primeiro serviço será aquele para criar uma entidade. Primeiro colocamos `Product` como retorno, então o nome do método (fique a vontade!), precisamos falar que ele irá responder através do termo `.POST` do HTTP, no corpo desta requisição terá uma entidade produto:

`@RequestMapping(method = RequestMethod.POST)`
`	Product create(@RequestBody Product product){ }`

Agora precisamos salvar esta entidade produto, então vamos usar o repositório:

`private ProductRepository productRepository;`

Mas calma que a mágia do Spring se inicia agora, injetando a instância do `ProductRepository` neste atributo de classe, através da notação `@Autowired`:

`@Autowired`
`private ProductRepository productRepository;`

Aqui ocorre portanto a inversão de controle. Em seguida voltamos ao primeiro método contruído com o intuito de salvar o produto e retornar o produto que fora salvo:

`@RequestMapping(method = RequestMethod.POST)`
`	Product create(@RequestBody Product product){`
`		return productRepository.save(product);`
`}`

Certo! Vamos considerar a possibilidade de buscar um produto pelo id:

` Product findById( ) { }`

Mas temos que passar o id, e podemos fazer isso usando novamente a notação ` @RequestMapping ` tendo como parâmetro a url:

`Product findById(@PathVariable Integer id){`
`	return productRepository.findById(id);`
`}`

Pode-se especificar também que ele irá responder ao verbo `.GET`, por fim temos o seguinte:

`@RequestMapping(value="/{id", method = RequestMethod.GET)`
`Product findById(@PathVariable Integer id){`
`	return productRepository.findById(id);`
`}`

Já viu que tá dando erro né? É porque o retorno do `finById( )` é <font style="background-color: #ccff99"> opcional</font> e estamos retornando um `Product`, porque ele pode não existir. Para evitar todos aqueles tratamentos de `null` o Java 8 incluiu a classe `Optional`, que resolve nosso problema dessa forma:

`@RequestMapping(value="/{id", method = RequestMethod.GET)`
`Optional<Product> findById(@PathVariable Integer id){`
`	return productRepository.findById(id);`
`}`

<H1> ⚙️ Config Server </H1>

Vamos voltar aa Spring Initialzr e criar o Config Server, para o caso dos serviços crescerem e o gerenciamento precisar ser mais sofisticado. Ele atua como uma rede externa àquela dos microsserviços, uma vez que exige um serviço maior.

As dependências utilizadas são quase que as mesmas do anterior: Lombok, Srping Actuator e Spring Web. Porém, há a adição do **Config Server** o qual atua como uma central de gerenciamento para configuração via Git, SVN ou HashiCorp Vault.

Então nós o importamos para o projeto. Como estou utilizando o IntelliJ fiz o seguinte caminho:

` File > New > Module from Existing Sources... > `

Aqui você irá no gerenciador de arquivos do seu computador e irá copiar o caminho do arquivo, colando-o no campo cedido para. Irá abrir uma janela perguntando se deseja criar um módulo com fontes existentes (_Create module from existing sources_) ou importar um módulo de um modelo externo (_Import module from external model_) e é esse que você irá selecionar, além do ícone do Gradle, que nem a imagem abaixo:

![](https://i.pinimg.com/564x/83/5c/d5/835cd53ca3bef59c68162867c70b989e.jpg)

<h1> 📁 Classe ConfigServerApplication </h1>

O arquivo principal contendo a palavrinha `main` precisa de algumas configurações que habilitem todas as características que desejamos. Vamos começar pela notação `@SprinBootApplication` que inicia todas essas configurações, e para começarmos a configurá-las utilizamos a notação `@EnableConfigServer`.

<h1> 📄 Arquivo application.yml - ConfigServer</h1>

Neste arquivo realizamos a configuração do local em que a aplicação irá "rodar". Aqui ela irá "rodar" na portaria 8888

`server:`
`	port: 8888`

Agora o Spring tem de puxar essas configurações de algum lugar e para isso precisamos configurar. No nosso caso, ele irá puxar do GitHub:

`spring:`
`	cloud:`
`		config:`
`			server:`
`				git:`
`					uri:`

Então, nós habilitamos o ConfigServer e falamos para a aplicação onde ela tem de buscar o arquivo de configurações.

<h1> 📄 Arquivo application.yml - Projeto</h1>
Precisamos definir um nome para a aplicação, já que é através dela que a outra aplicação irá buscar:

`spring:`
`	application:`
`		name: product-catalog`

Também precisamos de uma configuração para que o `product-catalog` possa se conectar ao ConfigServer:

`cloud:`
`	 spring:`
`		uri: http://localhost:8888`

<font style="background-color: #ffcc99">MAAAAAS, a aplicação `product-catalog` precisa de um `Client` para se conectar ao ConfigServer</font>.  Vamos resolver como sendo uma dependência, como a **ConfigClient**. Para isso precisamos gerar novamente uma aplicação com todas as configurações anteriores do **ConfigServer** adicionando o **ConfigClient**.

<h1> 🔎 Adicionando Dependências</h1>
Vamos ao arquivo `build.gradle` do nosso projeto e iremos adicionar `implementation 'org.springframework.cloud:spring-cloud-starter-config'` ao bloco de dependências. Fizemos isso por não termos ainda uma aplicação Spring Cloud, também temos de adicionar duas outras extensões:

`ext{`
`	set("springCloudVersion", "Hoxton.SR3")`
`}`

`dependencyManagement{`
`	imports{`
`		mavenBom"org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}"`
`	}`
`}`

Elas são necessárias somente se formos importar algo do SpringCloud.

<H1> ⚙️ Service Discovery </H1>

Mesma lógica do ConfigServer, mesma lógica, mas ele <font style="background-color: #ffff99">atua como um intermediário entre o ConfigServer e os microsserviços</font>. Voltando ao Spring Initialzr vamos colocar as dependências: **ConfigClient** e **Eureka Server** - servidor Eureka de spring-cloud-netflix.

<h1> 📁 Classe ServiceDiscoverApplication </h1>

Feito o mesmo processo de importação do ConfigServer, vamos ao arquivo principal e colocaremos a notação `@EnableEurekaServer`.

<h1> 📄 Arquivo application.yml - ServiceDiscover</h1>

Exatamente a mesma coisa do `.yml` do projeto:

`spring:`
`	application:`
`		name: service-discovery`

`cloud:`
`	config:`
`		uri: http://localhost:8888`

<h1> 🔎 Adicionando Dependências</h1>
Vamos ao arquivo `build.gradle` do nosso projeto e iremos adicionar `implementation 'org.springframework.cloud:spring-cloud-starter-netflix-eureka-server'` e `compiled("redis.clients:jedis")` ao bloco de dependências.

<H1> 🧰 Gateway </H1>
Ele é uma das peças fundamentais para uma arquitetura de microsserviços, pois ajuda a expor o serviço, porém ele <font style="background-color: #e6ff99">não pode falar diretamente, pois pode acarretar em uma comunicação muito complexa</font> - e isso não é nada legal para o usuário nem para o programador do Front-End.

A priori ele deve simplificar, mas também, caso queira (ou acidentalmente por falta de conhecimento aprofundado), pode complicar.

Voltando ao Spring Initialzr, vamos selecionar as seguintes dependências: **Spring Boot Actuator**, **Config Client** e **Gateway**.

<h1> 📄 Arquivo application.yml - Gateway </h1>

Realizando o mesmíssimo processo:

`spring:`
`	application:`
`		name: gateway`
`	cloud:`
`		config:`
`			uri: http://localhost.8888`

<h1>✨ Fim! </h1>
Muito obrigada caso tenha lido até aqui 🎉
