# mybatis插件使用说明  

1. 在您的spring 的 applicationContext.xml配置文件中增加以下配置
	
```xml?linenums=true
	<import resource="classpath*:META-INF/spring/spring-hp-core-common.xml"/>
	<import resource="classpath*:META-INF/spring/spring-hp-core-mybatis.xml"/>
	<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
		<property name="basePackage" value="mybatisDAOLocation" />
	</bean>
```
`其中mybatisDAOLocation就是你的mybatis的dao的package，这个跟正常使用mybatis一样`  
`另外mybatis的mapper文件，是约定放在 src/main/resources/META-INF/mybatis目录下面。就是那么一堆xml文件`   

** 在你的properties文件里面增加配置   
```
hp.core.dao.interceptor.expression=execution(* com.hp.core.test.dal.*DAO.*(..))  
```
这个配置用来指定你的DAO所在位置，框架里面拦截器按照这个方式去拦截数据库请求   

** 在src/main/resources目录下增加数据库配置文件 database.yml
```
datasources:
   eie:						//数据库名称（第一个是默认数据库）
      servers:
         master:				//数据库主库的配置（可以配置多个）
           - 127.0.0.1:3306
	 slave:					//数据库从库配置（可以配置多个。slave可以不配置，这样就全走主库）
	   - 127.0.0.1:3306
      username: yh_test
      password: yh_test
   dababase2:					//第二个数据库
      servers:
        master:
           - 127.0.0.1:3306
      username: yh_test
      password: yh_test
      daos:					//该数据库对应的dao路径（该下面的dao全部自动路由到dababase2的库下，如果一个dao不在这个下面，则自动路由到第一个数据库下）
        - com.hp.core.test.dal.ITablesDAO
。。。。（继续第三个数据源配置）


```


### 2. 您的modal文件里面需要有几个地方需要注意的
![avatar](http://imgboys1.yohobuy.com/cmsimg01/2018/06/06/14/55/017fea5ab5828b098a7b652c0a5bbf8161.png?imageView2/2/w/600)   

  #### 2.1. @Table注解  指定model与数据库表名之间的映射（没有注解或为空，则默认驼峰转下划线的）   
  #### 2.2. @column注解  指定每个字段跟数据库字段映射关系（没有注解或为空，则默认驼峰转下划线的）   
   - insertable   为空或不指定，则默认该字段在insert的时候，参与sql组装  
   - updatable    为空或不指定，则默认该字段在update的时候，参与sql组装   
  #### 2.3. @Transient注解	 如果字段加上该注解，则说明该字段非数据库字段，不参与任何sql的组装	 
  #### 2.4. @QueryType注解，指定该字段的查询批量方式，默认为等于，可选 like 
	 
### 3. 您的DAO，需要继承  extends BaseMapper<T>   并且指定泛型类型   
			示例：public interface ITestTableDAO extends BaseMapper<TestTable>   
			继承了BaseMapper 就直接拥有了基本增删改查的接口，并且支持多条件查询
	 	
	
