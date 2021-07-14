package com.dioJavaProject3.swagger;

public class SwaggerCongig{
    @Bean
    public new Docket(DocumentationType.SWAGGER_2) {
        .select()
                .apis(RequestHandlerSelectors.basePackage("com.dioJavaProject3.backend"))
                .paths(PathSelectors.ant("/**"))
                .build().apiInfo(apiInfo()).globalOperationsParameters(
                        Collections.singletonList(
                                new ParameterBuilder().name("Authorization")
                                        .description("Header para Token JWT")
                                        .modelRef(new ModelRef("string")).parameterType("header")
                                        .required(false).build()));
    }
    @Bean
    public ApiInfo apiInfo(){
        return new ApiInfoBuilder().title("API-REST"")
                .description("Api_para_gerenciamento_de_ponto_e_acesso")
                .version("1.0.0").license("Apache License Version 2.0")
                .llicenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
                .contact(new Contact("DIO", "https://web.digitalinnovation.one", "contato@digitalinnovationone.com.br"))
                .build();
    }
}
