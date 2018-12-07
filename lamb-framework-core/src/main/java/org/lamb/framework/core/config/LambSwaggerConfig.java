package org.lamb.framework.core.config;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.lamb.framework.common.enumeration.ExceptionEnum;
import org.lamb.framework.common.util.JsonUtil;
import org.lamb.framework.core.templete.LambResponseTemplete;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import springfox.documentation.builders.*;
import springfox.documentation.schema.TypeNameExtractor;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.readers.operation.SwaggerResponseMessageReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.lamb.framework.common.enumeration.ExceptionEnum.E000000000;
import static org.lamb.framework.common.enumeration.ExceptionEnum.ES00000000;


/**
 * @description: swagger配置
 * @author: Mr.WangGang
 * @create: 2018-10-17 下午 2:55
 **/
public  class LambSwaggerConfig extends SwaggerResponseMessageReader implements WebFluxConfigurer {

    private final TypeNameExtractor typeNameExtractor;
    private final TypeResolver typeResolver;

    public LambSwaggerConfig(TypeNameExtractor typeNameExtractor, TypeResolver typeResolver) {
        super(typeNameExtractor, typeResolver);
        this.typeNameExtractor = typeNameExtractor;
        this.typeResolver = typeResolver;
        api();
    }
    @Override
    protected Set<ResponseMessage> read(OperationContext context) {
        List list = unifiedResponse();
        Set set = Sets.newHashSet();
        list.stream().forEach(e->set.add(e));
        return set;
    }

    public Docket api() {
        List<Parameter> pars = new ArrayList<Parameter>();
        List<Parameter> parameters = unifiedParameter(new ParameterBuilder());
        if(!CollectionUtils.isEmpty(parameters)){
            parameters.stream().filter((e)->e!=null).forEach(e->pars.add(e));
        }


        ApiInfo apiInfo = apiInfo(new ApiInfoBuilder());
        if(apiInfo == null){
            apiInfo = new ApiInfoBuilder().build();
        }

        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .globalOperationParameters(pars)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui.html**")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }



    public  ApiInfo apiInfo(ApiInfoBuilder apiInfoBuilder){
        return null;
    };

    public  List<Parameter> unifiedParameter(ParameterBuilder parameterBuilder){
        return null;
    };

    private List<ResponseMessage> unifiedResponse(){
        List list = Lists.newLinkedList();
        list.add(
                new ResponseMessageBuilder()
                .code(200)
                .message(formJson(E000000000))
                .build());
        list.add(
                new ResponseMessageBuilder()
                .code(500)
                .message(formJson(ES00000000))
                .build());
       /* Arrays.stream(ExceptionEnum.values())
                .forEach(e->
                        list.add(e.getCode().equals(E000000000.getCode())?
                                new ResponseMessageBuilder()
                                        .code(200)
                                        .message(formJson(E000000000))
                                        .examples(Lists.newArrayList(new Example(new LambResponseTemplete())))
                                        .build():
                                new ResponseMessageBuilder()
                                        .code(500)
                                        .message(formJson(e))
                                        .build()
                        ));*/
        return list;
    }
    private String formJson(ExceptionEnum exceptionEnum){
        LambResponseTemplete lambResponseTemplete = new LambResponseTemplete();
        lambResponseTemplete.setServiceCode(exceptionEnum.getCode());
        lambResponseTemplete.setServiceMessage(exceptionEnum.getMessage());

        return JsonUtil.objToString(lambResponseTemplete);
    }
}
