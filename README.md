wiki:
封装了访问HTTP请求

v1.0.0:

可直接使用接口即可访问接口上配置的HTTP请求

使用示例：

1、创建资源定位符的配置类，并且将需扫描包的接口路径带入
HttpUriConf conf = new HttpUriConf("com.httpuri.iagent.dao");

2、加载资源定位符的包配置
conf.loadUriPathPackage();

3、可直接根据接口class获取代理对象，直接访问接口上注解的HTTP请求
UserDao uri = conf.getUri(UserDao.class);

v1.0.1:

调整了底层类所在包路径，为下一个版本做准备
用法还是一致的，有些类的路径产生了一些变化，可调整。
