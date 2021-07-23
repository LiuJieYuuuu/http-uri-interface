

### v2.0.0

包含v1.1.3的所有功能

#### 新增以及优化功能

1、支持Spring依赖注入，可直接使用新增的注解进行扫描指定包，即可在Spring容器中直接获取到对象并且调用；支持两种注入到Spring IOC 容器方式。

2、新增自定义异常。

3、新增判断是否扫描加载完毕属性。

4、新增PUT请求方式。

5、修改了注解需指定key进行赋值，可直接使用value赋值即可，使用上更加简洁。

6、将maven引用类型修改为了<scope>provided</scope>，可自由选择版本。

7、修复了部分场景下不能加载到接口问题。

6、注释基本齐全。

#### 使用示例

使用接口:

```java
@ParamUri("http://localhost:8080/mvc")
public interface UserDao {
	//除url之外，其他参数方法级别可覆盖类级别
    @ParamUri(url = "/getGetController",requestType = HttpEnum.GET,httpExecutor = MyHttpExecutor.class)
    Map getUserInfo(@ParamKey("name") String name);

    @ParamUri(url = "/getPostController",requestType = HttpEnum.POST)
    Map getUserPostInfo(@ParamKey("name") String name);

    @ParamUri(url = "/getPostControllerJson",requestType = HttpEnum.POST,contentType = HttpConstant.APPLICATION_JSON_UTF8)
    Map getUserInfo(Map param);
    
    @ParamUri(url = "/getPathVariable/{id}")
    Map getPathVariable(@PathKey("id") String key,@ParamKey("age")Integer age);
    //可自动查找Map参数在路径上的参数名称
    @ParamUri("/getPathVariable/{id}")
    Map getPathVariable(Map map);

}

```

自定义HttpExecutor：需继承AbstractHttpExecutor抽象类

```java
public class MyHttpExecutor extends AbstractHttpExecutor {

	public Object sendHttp(HttpUriBean arg0, Object[] arg1) {

		return "{'a':'v'}";
	}

}

```

#### Spring注入

1、采用注解形式加载；

2、采用手动注入到IOC容器中，并手动指定包名；

```java
//第一种：
@IagentComponentScan("com.test.mybatis.idao")
@Configuration
public class IagentConfig｛
｝
//第二种：
@Configuration
public class IagentConfig {
	@Bean
	public HttpUriConfBean httpUriConfBean() {
		HttpUriConfBean bean = new HttpUriConfBean();
		bean.setBasePackages(new String[]{"com.test.mybatis.idao"});
		return bean;
	}
}
```

个人推荐使用注解方式。

#### 使用方式

不使用Spring:

```java
//1、创建资源定位符的配置类，并且将需扫描包的接口路径带入（可后续设置）
HttpUriConf conf = new HttpUriConf("com.httpuri.iagent.dao");
//2、可直接根据接口class获取代理对象，直接访问接口上注解的HTTP请求
UserDao uri = conf.getUri(UserDao.class);
//3、可直接调用接口传输参数即可
uri.getUserInfo("test");

```

使用Spring：

```java
@Autowired
com.test.mybatis.idao.UserDao iUserDao;

@GetMapping(value="/getIOCBeans")
@ResponseBody
public String getIOCBeans() {
	System.out.println(iUserDao.getUserInfo("test"));
	return "";success
}
```

