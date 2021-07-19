package com.httpuri.iagent.request;

import com.httpuri.iagent.builder.HttpUriBean;

public interface HttpExecutor {

    String handlePathKey(HttpUriBean bean, Object[] args);

    Object sendHttp(HttpUriBean bean,Object[] args);

}
