package com.javaProject4.demo.config;

@Configuration
@EnabledElasticSearchRepositories(basePackages = "com")
public class ElasticSearchConfig extends AbstractElasticSearchConfiguration{
    public RestHighLevelClient elasticsearchClient(){
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                ,connetedTo("localhost:9200", "localhost:9300").build();
        return RestClients.create(clientConfiguration).rest();
    }
    @Bean @Override
    public EntityMapper entityMapper(){
        ElasticSearchMapper entityMapper = new ElasticsearcjEntityMapper(elasticsearchMappingContext(),
                new DefaultConversionService());
        entityMapper.setConversion(elasticsearchCustomConversion());
        return entityMapper;
    }
}