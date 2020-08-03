## 为什么使用它
- 配置简单，快速上手
- 实现读写分离，并且多数据源可以自动路由，避免代码中硬编码选择数据源
- 避免每张表中对应的 XXDAO.java 和 XXMapper.xml里面一堆重复代码
- 可以一键生成代码，避免重复劳动
- 只做增强，不做修改，代码耦合度低

## 功能包含
1. 数据库读写分离
2. 多数据源自动路由
3. 提供BaseMapper，可以0代码实现常用的增删改查等操作，并且提供可以自由包装的SQLBuilder类
4. 提供代码自动生成器。一键生成 controller，service，dao，model等；如果是企业级应用软件，直接可以生成基于freemarker的前端页面代码。真正的实现了一键生成页面增删改查功能。

## 注意
    由于jar包还没有上传至maven中央仓库，所以目前只能把代码下载下来，然后打包依赖。等以后上传至中央仓库后，可以直接使用maven依赖就可以使用。

## 使用方法
###### 1. model类
```java
package com.xxx.dal.model;
import javax.persistence.Id;
import com.hp.core.common.beans.BaseBean;
public class TestTable extends BaseBean {

	@Id
	private Integer id;
	
	private String title;

	//get set
}
```
##### 注意点
1、类名，字段名，默认采用下划线转驼峰格式。  比如数据库字段 user_name 则对应model类中 userName。   
2、数据库的主键字段名建议使用id，model中id字段加@Id 注解，标明是主键   
3、如果类名与表名不符合下划线转驼峰格式，那需要在类上增加注解 javax.persistence.Table 来指定表名   
4、如果model类中字段名与表中字段名不符合下划线转驼峰格式，那需要在相应的字段上增加注解 javax.persistence.Column 来指定字段名   
5、框架默认model类中所有字段对应数据库中的字段，如果某个字段不是数据库对应的字段，请在该字段上加上注解 javax.persistence.Transient  
6、dabatase.yml 中 第一个database默认为主数据源，其余认为是关联的其他数据源    
7、读写分离查询是按照dao里面的方法命名来自动路由，查询，新增，修改，删除，请分别使用 select,insert,update,delete 打头。如果dao里面方法命名匹配不到上面规则，则全部路由到master数据库    
8、所有新增，修改，删除一律路由到master数据库，查询是随机取主从。    
9、database.yml 里面的  daos配置项，表示这个dao下的所有数据库操作都路由到它指定的database上面      
10、如果有mybatis的 mapper文件，默认放在 classpath:META-INF/spring/mybatis-config.xml 目录下，如果有不一样，请在配置文件里面设置       
 - hp.core.mybatis.config:classpath=META-INF/spring/mybatis-config.xml


###### 2. DAO类
```java
package com.xx.dal;

import com.eie.dal.model.TestTable;
import com.hp.core.mybatis.mapper.BaseMapper;

public interface ITestTableDAO extends BaseMapper<TestTable, Integer> {
}
```

##### 注意点
1、自己的dao只要继承BaseMapper 然后提供泛型对应。 第一个是对应的model类，第二个是主键类型    
2、如果只是一些常用的操作数据库，这样就可以了。BaseMapper中已经提供了大部分的操作方法。    

###### 3. Mapper
如果没有特殊的查询，那mapper文件完全可以不要。（就是这么任性）


###### 4. database.yml
在我们的项目的 resources 目录下增加 database.yml文件 （数据库配置文件）
```yaml
datasources:
   databasename1:                                           ##数据库名（必须要填写）
      servers:
         master:
           - 192.168.0.1:3306                               ##主库配置
         slave:
           - 192.168.0.2:3306                               ##从库1
           - 192.168.0.3:3306                               ##从库2
           - 192.168.0.4:3306                               ##从库3
      username: test
      password: test
      poolName: DBCP                                        ##使用的连接池，可选（DBCP，DRUID）。默认DBCP
      minIdle: 0                                            ##最小空闲数
      maxTotal: 50                                          ##最大连接数
      maxIdle: 5                                            ##最大空闲数
      initialSize: 5                                        ##初始化连接数
      maxWaitMillis: 5000                                   ##最大等待毫秒数
      ## 等等，常用的连接池配置
   databasename2:                                           ##数据库名（必须要填写）
      servers:
         master:
           - 192.168.0.1:3306                               ##主库配置
         slave:
           - 192.168.0.2:3306                               ##从库1
      username: test
      password: test
      daos:
       - com.xxx.dao.Table1DAO
       - com.xxx.dao.Table2DAO
      ## 等等，常用的连接池配置
   databasename3:                                           ##数据库名（必须要填写）
      servers:
         master:
           - 192.168.0.1:3306                               ##主库配置
         slave:
           - 192.168.0.2:3306                               ##从库1
      username: test
      password: test
      daos:
       - com.xxx.dao.Table3DAO
       - com.xxx.dao.Table4DAO
      ## 等等，常用的连接池配置
```


###### 5. springboot启动类配置
启动类增加注解    
    @ImportResource(locations = {"classpath\*:META-INF/spring/spring-\*.xml"})        
会加载jar包中所有匹配的xml文件          
在项目的配置文件中增加拦截你的dao的配置corn表达式       
    hp.core.database.interceptor.expression=(execution(\* com.hp.webtest.dal.\*.\*(..)))  这里改成你所存放dao的package就可以了     
这个是拦截你的dao文件，进行读写分离自动路由的 


ok大功告成，可以使用了



## 代码自动生成
1. 依赖jar包，启动项目
2. 使用postman或其他工具，发送post请求
Content-Type: application/json
url : http://ip:port/项目名称/AutoCreateRest/create
content :
```json
{
	"mainPathDir" : "/workspace/project",//项目根路径，最好写绝对路径
	"tableNameList" : [//需要自动生成代码的表名，是个数组
		"table_name1", "table_name2"
	],
	"serviceMavenModule" : "mvc",//service代码所在的maven module包名
	"controllerMavenModule" : "mvc",//controller代码所在的maven module包名
	"projectPackage" : "com.xx.xxx",//项目的package路径
	"createService" : true,//是否生成service
	"createController" : true,//是否生成controller
	"createFtl" : false//是否生成freekmarker的页面文件
}
```
执行就可以了。项目路径下就会出现对应的文件。数据库的dao，model，mapper都一起自动生成

