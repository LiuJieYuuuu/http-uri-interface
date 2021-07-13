

### v1.1.0

一个RELEASE版本，较为稳定，但是依赖fastjson

#### 新增及优化功能

1、可进行接口行的注解覆盖，如果方法上没有指明，则使用接口上的注解参数，否则使用默认。

2、使用了预加载接口，调用接口更加便捷。

3、使用方法更加简洁。

#### 使用示例

接口:

```java
@ParamUri(url = "http://localhost:8080/mvc")
public interface UserDao {

    @ParamUri(url = "/getGetController",requestType = HttpEnum.GET)
    Map getUserInfo(@ParamKey(key = "name") String name);

    @ParamUri(url = "/getPostController",requestType = HttpEnum.POST)
    Map getUserPostInfo(@ParamKey(key = "name") String name);

    @ParamUri(url = "/getPostControllerJson",requestType = HttpEnum.POST,contentType = HttpConstant.APPLICATION_JSON_UTF8)
    Map getUserInfo(Map param);

}

```

使用方式：

1、创建资源定位符的配置类，并且将需扫描包的接口路径带入（可后续设置）
HttpUriConf conf = new HttpUriConf("com.httpuri.iagent.dao");

2、可直接根据接口class获取代理对象，直接访问接口上注解的HTTP请求
UserDao uri = conf.getUri(UserDao.class);

