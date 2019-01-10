# lambframework
org lamb devlopment framework

自己写的框架代码,整合了spring boot2 webflux  , spring security ，swagger,spring data redis 和 webflux 的handle级别全局异常拦截和规范
持久层框架使用了mybatis tk.mapper作为插件，减少开发成本

# 框架核心配置
在你的父pom中 依赖框架



    <parent>
        <groupId>org.lamb.framework</groupId>
        <artifactId>lamb-framework</artifactId>
        <version>0.0.1-SNAPSHOT</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>.
	
	
	
	
	
	
	
	并加入依赖lamb的核心组件
	    <dependency>
            <groupId>org.lamb.framework</groupId>
            <artifactId>lamb-framework-core</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>
		
		
		

#框架配置
#自定义自定义异常
全局异常父类
org.lamb.framework.common.exception.basic.GlobalException
对于子项目来说 你的自定义异常应该继承这个父类，才会被拦截
public class ProcessException extends GlobalException {

    public ProcessException(ProcessExceptionEnum error) {
        super(error.getCode(),error.getMessage());
    }
}
这是一个例子,重载父类的构造方法,已枚举的形式去抛出异常,会显得更加优雅
例如
throw new EventException(EA00000007)

#swagger配置 
在spring boot 启动类上加入注解
@EnableLambSwagger来开启注解
如果需要自定义配置请先重载org.lamb.framework.core.config.LambSwaggerConfig父类

    public  ApiInfo apiInfo(ApiInfoBuilder apiInfoBuilder){
        return null;
    };

    public  List<Parameter> unifiedParameter(ParameterBuilder parameterBuilder){
        return null;
    };
	这2个方法
	
	第一个方法是swagger 首页信息 包括了 作者  开源免费声明  名称等等
	第二个方法是全局请求参数,如果需要,可以加入

#接口返回统一参数
对于webflux来说接口既有路由的形式也有传统MVC的写法
不管是使用哪种 都先在接口层继承 org.lamb.framework.core.handler.LambHandler	

    protected Mono<ServerResponse> reactive(Object data){
        LambResponseTemplete lambResponseTemplete = new LambResponseTemplete(data);
        return ServerResponse.ok().contentType(APPLICATION_JSON).body(Mono.just(lambResponseTemplete),LambResponseTemplete.class);
    }

    protected Mono<ServerResponse> reactive(){
        LambResponseTemplete lambResponseTemplete = new LambResponseTemplete();
        return ServerResponse.ok().contentType(APPLICATION_JSON).body(Mono.just(lambResponseTemplete),LambResponseTemplete.class);
    }

    protected Mono<LambResponseTemplete> returning(Object data){
        return Mono.just(new LambResponseTemplete(data));
    }

    protected Mono<LambResponseTemplete> returning(){
        return Mono.just(new LambResponseTemplete());
    }
	
	reactive 是针对路由形式的返回 
	returning是针对传统形式的返回 
	
	例如  传统形式 
	
	@RequestMapping(value= ApiContract.I000000000,method = RequestMethod.POST,consumes= MediaType.APPLICATION_JSON_VALUE)
    public Mono<LambResponseTemplete> validate(@RequestBody @Valid @NotNull OrderStateMachineParamDTO param){
        LambPrincipalFactoryContainer.getPrincipal(PrincipalModelEnum.class);
        return returning(orderService.execute(param));
    }
	
	这是我写的一个父类的controller 的例子
	
	public abstract class FoundationController extends LambHandler {
    public <T>Mono<LambResponseTemplete> returning(Class clazz,T data){
        if(data != null){
            try {
                return returning(clazz.newInstance());
            }catch (IllegalAccessException e) {
                throw new EventException(ES00000002);
            } catch (InstantiationException e) {
                throw new EventException(ES00000002);
            }
        };
        return returning(BeanPlasticityUtill.copy(clazz,data));
    }
}

对框架中的方法再度封装 对于service 或者Biz层返回给 前端的参数 应该从DTO转VO
这一步应该自动化处理
所有的接口层返回值 应该使用 Mono<LambResponseTemplete>  就会统一返回参数 

#service的参数校验
在service层放上引入@LambVaild 
	@Override
    @LambValid
    public <T extends DisbOrderCreateParamDTO> DisbOrderCreateResultDTO create(@Valid @NotNull OrderStateMachineTransitionParamDTO<T> param)
在方法参数上 加入 @Valid @NotNull 这种 java.vaild标准的参数校验注解

#微服务统一授权




#GUID




#状态机
	