封装了访问HTTP请求

可直接使用接口即可访问接口上配置的HTTP请求

使用示例：
1、创建资源定位符的配置类，并且将需扫描包的接口路径带入
HttpUriConf conf = new HttpUriConf("com.httpuri.iagent.dao");
2、加载资源定位符的包配置
conf.loadUriPathPackage();
3、可直接根据接口class获取代理对象，直接访问接口上注解的HTTP请求
UserDao uri = conf.getUri(UserDao.class);

@ParamUri:指定uri以及请求方式，Content-Type
@ParamKey:指定访问参数，如果使用Map无需指定ParamKey

接口示例：
@ParamUri(url = "http://localhost:8080/mvc/getGetController",requestType = HttpEnum.GET)
Map getUserInfo(@ParamKey(key = "name") String name);


