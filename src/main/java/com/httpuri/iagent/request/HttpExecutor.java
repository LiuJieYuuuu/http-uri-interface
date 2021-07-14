package com.httpuri.iagent.request;

import com.httpuri.iagent.builder.HttpUriBean;

public interface HttpExecutor {

    Object sendHttp(HttpUriBean bean);

}
