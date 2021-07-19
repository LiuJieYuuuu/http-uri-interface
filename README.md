

### v1.1.2

包含v1.1.1的所有功能

#### 新增及优化功能

1、新增注解@PathKey，即支持路径上可填充参数

2、修改了HttpExecutor自定义请求类的修改

#### 使用示例

使用接口:

```java
@ParamUri(url = "http://localhost:8080/mvc",httpExecutor = SimpleHttpExecutor.class)
public interface UserDao {
	//除url之外，其他参数方法级别可覆盖类级别
    @ParamUri(url = "/getGetController",requestType = HttpEnum.GET,httpExecutor = MyHttpExecutor.class)
    Map getUserInfo(@ParamKey(key = "name") String name);

    @ParamUri(url = "/getPostController",requestType = HttpEnum.POST)
    Map getUserPostInfo(@ParamKey(key = "name") String name);

    @ParamUri(url = "/getPostControllerJson",requestType = HttpEnum.POST,contentType = HttpConstant.APPLICATION_JSON_UTF8)
    Map getUserInfo(Map param);
    
    @ParamUri(url = "/getPathVariable/{id}")
    Map getPathVariable(@PathKey(key = "id") String key,@ParamKey(key="age")Integer age);
    //可自动查找Map参数在路径上的参数名称
    @ParamUri(url = "/getPathVariable/{id}")
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

#### 使用方式

```java
//1、创建资源定位符的配置类，并且将需扫描包的接口路径带入（可后续设置）
HttpUriConf conf = new HttpUriConf("com.httpuri.iagent.dao");
//2、可直接根据接口class获取代理对象，直接访问接口上注解的HTTP请求
UserDao uri = conf.getUri(UserDao.class);
//3、可直接调用接口传输参数即可
uri.getUserInfo("test");

```

