/*
 * Copyright 2016 Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openo.sdno.model.uniformsbi.base;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.openo.baseservice.remoteservice.exception.ExceptionArgs;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.model.uniformsbi.error.Errors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * adapter response information model class.<br>
 * 
 * @author
 * @version SDNO 0.5 August 4, 2016
 */
public class AdapterResponseInfo {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdapterResponseInfo.class);

    /**
     * HttpStatus code
     */
    private int ret = 0;

    private String format = "json";

    private String msg;

    private boolean success = true;

    @JsonIgnore
    private Errors errors = null;

    private Map<String, String> respHeaders;

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     */
    public AdapterResponseInfo() {
        // a empty constructor that construct a object without set any thing
    }

    /**
     * Constructor<br>
     * 
     * @param msg Message to set
     * @since SDNO 0.5
     */
    public AdapterResponseInfo(String msg) {
        this.msg = msg;
    }

    /**
     * Constructor<br>
     * 
     * @param ret Ret to set
     * @param msg Message to set
     * @since SDNO 0.5
     */
    public AdapterResponseInfo(int ret, String msg) {
        this.ret = ret;
        this.msg = msg;
    }

    /**
     * Constructor<br>
     * 
     * @param ret Ret to set
     * @param format format to set
     * @param msg Message to set
     * @since SDNO 0.5
     */
    public AdapterResponseInfo(int ret, String format, String msg) {
        this.ret = ret;
        this.format = format;
        this.msg = msg;
    }

    /**
     * Constructor<br>
     * 
     * @param ret Ret to set
     * @param format format to set
     * @param msg Message to set
     * @param respHeaders respHeaders to set
     * @since SDNO 0.5
     */
    public AdapterResponseInfo(int ret, String format, String msg, Map<String, String> respHeaders) {
        this.ret = ret;
        this.format = format;
        this.msg = msg;
        this.respHeaders = respHeaders;
    }

    /**
     * set Ret
     * 
     * @param ret The ret to set.
     */
    public void setRet(int ret) {
        this.ret = ret;
    }

    public int getRet() {
        return ret;
    }

    /**
     * get Message
     * 
     * @return Returns the Message.
     */
    public String getMsg() {
        return msg;
    }

    /**
     * set Message
     * 
     * @param msg The Message to set.
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setRespHeaders(Map<String, String> respHeaders) {
        this.respHeaders = respHeaders;
    }

    public Map<String, String> getRespHeaders() {
        return respHeaders;
    }

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    /**
     * check whether is success.<br>
     * 
     * @return True when success is true
     * @since SDNO 0.5
     */
    @JsonIgnore
    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Get ServiceExceotion.<br>
     * 
     * @param controllerFailCode The controller failed code
     * @return The ServiceExceotion object
     * @since SDNO 0.5
     */
    public ServiceException getServiceException(final String controllerFailCode) {
        String[] details = new String[] {String.valueOf(this.getRet()), this.getMsg()};
        LOGGER.info("Parse ErrorMeg detail from controller: " + this.getMsg());
        ExceptionArgs exceptionArgs = new ExceptionArgs(new String[] {}, new String[] {}, details, new String[] {});

        return new ServiceException(controllerFailCode, EMPTY, exceptionArgs);
    }

    /**
     * check whether the controller httpStatus is success.<br>
     * 
     * @return true when ret is in [200,299]
     * @since SDNO 0.5
     */
    @JsonIgnore
    public boolean isControllerSuccess() {
        return ret / 200 == 1;
    }

    /**
     * check whether the controller httpStatus is 404.<br>
     * 
     * @return true when ret is 404
     * @since SDNO 0.5
     */
    @JsonIgnore
    public boolean isController404() {
        return ret == 404;
    }

    @Override
    public String toString() {
        return "AdapterResponseInfo ret=" + ret;
    }
}
