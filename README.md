### v1.0.1

可直接使用接口即可访问接口上配置的HTTP请求

#### 使用示例

接口：

```java
public interface UserDao {

    @ParamUri(url = "http://localhost:8080/mvc/getGetController",requestType = HttpEnum.GET)
    Map getUserInfo(@ParamKey(key = "name") String name);

    @ParamUri(url = "http://localhost:8080/mvc/getPostController",requestType = HttpEnum.POST)
    Map getUserPostInfo(@ParamKey(key = "name") String name);
    
    @ParamUri(url = "http://localhost:8080/mvc/getPostControllerJson",requestType = HttpEnum.POST,contentType = HttpConstant.APPLICATION_JSON_UTF8)
    Map getUserInfo(Map param);

}

```


1、创建资源定位符的配置类，并且将需扫描包的接口路径带入
HttpUriConf conf = new HttpUriConf("com.httpuri.iagent.dao");

2、加载资源定位符的包配置
conf.loadUriPathPackage();

3、可直接根据接口class获取代理对象，直接访问接口上注解的HTTP请求
UserDao uri = conf.getUri(UserDao.class);

