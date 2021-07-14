

### v1.1.1

包含v1.1.0的所有功能

#### 新增及优化功能

1、解决了返回值类型为String报错问题

2、可支持自定义HTTP请求工具类，默认为自带的SimpleHttpExecutor

#### 使用示例

接口:

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

}

```

自定义HttpExecutor

```java
public class MyHttpExecutor implements HttpExecutor {

	public Object sendHttp(HttpUriBean arg0) {
		
		return "{'a':'v'}";
	}

}
```

使用方式：

1、创建资源定位符的配置类，并且将需扫描包的接口路径带入（可后续设置）
HttpUriConf conf = new HttpUriConf("com.httpuri.iagent.dao");

2、可直接根据接口class获取代理对象，直接访问接口上注解的HTTP请求
UserDao uri = conf.getUri(UserDao.class);

