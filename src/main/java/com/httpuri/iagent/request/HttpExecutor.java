package com.httpuri.iagent.request;

import com.httpuri.iagent.builder.HttpUriBean;

/**
 * <b>Common Http Request Interface</b>
 */
public interface HttpExecutor {

    /**
     * <b>handle http request url path params</b>
     * @param bean
     * @param args
     * @return
     */
    String handlePathKey(HttpUriBean bean, Object[] args);

    /**
     * specific http request
     * @param bean
     * @param args
     * @return
     */
    Object sendHttp(HttpUriBean bean,Object[] args);

}
