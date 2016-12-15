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

package org.openo.sdno.wanvpn.inventory.sdk.result;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.openo.sdno.exception.HttpCode;
import org.openo.sdno.result.ErrorCodeInfo;
import org.openo.sdno.wanvpn.inventory.sdk.common.ErrorCode;

/**
 * The class of result response.<br>
 * 
 * @param <T> Generic paradigm
 * @author
 * @version SDNO 0.5 2016-6-3
 */
@SuppressWarnings("serial")
public class ResultRsp<T> implements Serializable {

    protected String errorCode = ErrorCode.UNDERLAYVPN_SUCCESS;

    protected T data;

    /**
     * Description of error.
     */
    protected String descArg = "";

    /**
     * Reason of error.
     */
    protected String reasonArg = "";

    /**
     * Detail information of error.
     */
    protected String detailArg = "";

    /**
     * Advice.
     */
    protected String adviceArg = "";

    /**
     * HttpCode OK.
     */
    protected int httpCode = HttpCode.RESPOND_OK;

    /**
     * Message.
     */
    protected String message = "";

    private long total;

    private List<ErrorCodeInfo> smallErrorCodeList;

    private List<T> success = null;

    private List<FailData<T>> fail = null;

    /**
     * Constructor.<br>
     * 
     * @since SDNO 0.5
     */
    public ResultRsp() {
        super();
    }

    /**
     * Constructor.<br>
     * 
     * @since SDNO 0.5
     * @param errorCode error code to set
     */
    public ResultRsp(final String errorCode) {
        super();
        this.errorCode = errorCode;
    }

    /**
     * Constructor.<br>
     * 
     * @since SDNO 0.5
     * @param result ResultRsp to set
     */
    public ResultRsp(final ResultRsp<?> result) {
        if(null != result) {
            this.errorCode = result.getErrorCode();
            this.descArg = result.getDescArg();
            this.reasonArg = result.getReasonArg();
            this.detailArg = result.getDetailArg();
            this.adviceArg = result.getAdviceArg();
            this.smallErrorCodeList = result.smallErrorCodeList;
        }
    }

    /**
     * Constructor.<br>
     * 
     * @since SDNO 0.5
     * @param result ResultRsp to set
     * @param data data to set
     */
    public ResultRsp(final ResultRsp<?> result, final T data) {
        this(result);
        this.data = data;
    }

    /**
     * Build the success ResultRsp object.<br>
     * 
     * @return The success ResultRsp object
     * @since SDNO 0.5
     */
    public static ResultRsp rspSuccess() {
        return new ResultRsp(ErrorCode.UNDERLAYVPN_SUCCESS);
    }

    /**
     * Build the failed ResultRsp object.<br>
     * 
     * @return The failed ResultRsp object
     * @since SDNO 0.5
     */
    public static ResultRsp failed() {
        return new ResultRsp(ErrorCode.UNDERLAYVPN_FAILED);
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(final long total) {
        this.total = total;
    }

    /**
     * @return get the errorCode.
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * @return Returns the errorCode.
     */
    public void setErrorCode(final String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @return Returns the data.
     */
    public T getData() {
        return data;
    }

    /**
     * @param data The data to set.
     */
    public void setData(final T data) {
        this.data = data;
    }

    public String getDescArg() {
        return descArg;
    }

    public void setDescArg(final String descArg) {
        this.descArg = descArg;
    }

    public String getReasonArg() {
        return reasonArg;
    }

    public void setReasonArg(final String reasonArg) {
        this.reasonArg = reasonArg;
    }

    public String getDetailArg() {
        return detailArg;
    }

    public void setDetailArg(final String detailArg) {
        this.detailArg = detailArg;
    }

    public String getAdviceArg() {
        return adviceArg;
    }

    public void setAdviceArg(final String adviceArg) {
        this.adviceArg = adviceArg;
    }

    /**
     * Check whether is valid.<br>
     * 
     * @return true when is valid
     *         false when is in valid
     * @since SDNO 0.5
     */
    @JsonIgnore
    public boolean isValid() {
        return isSuccess() && this.getData() != null;
    }

    /**
     * @return Returns the.
     */
    public int getHttpCode() {
        return httpCode;
    }

    /**
     * @param httpCode The httpCode to set.
     */
    public void setHttpCode(final int httpCode) {
        this.httpCode = httpCode;
    }

    /**
     * @return Returns the.
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message The message to set.
     */
    public void setMessage(final String message) {
        this.message = message;
    }

    /**
     * Check whether is success.<br>
     * 
     * @return True when is success
     *         false when is failed
     * @since SDNO 0.5
     */
    @JsonIgnore
    public boolean isSuccess() {
        return ErrorCode.UNDERLAYVPN_SUCCESS.equals(errorCode);
    }

    /**
     * @return Returns the smallErrorCodeList.
     */
    public List<ErrorCodeInfo> getSmallErrorCodeList() {
        return smallErrorCodeList;
    }

    /**
     * @param smallErrorCodeList The smallErrorCodeList to set.
     */
    public void setSmallErrorCodeList(final List<ErrorCodeInfo> smallErrorCodeList) {
        this.smallErrorCodeList = smallErrorCodeList;
    }

    /**
     * @return Returns the success.
     */
    public List<T> getSuccess() {
        return success;
    }

    /**
     * @param success The success to set.
     */
    public void setSuccess(final List<T> success) {
        this.success = success;
    }

    /**
     * @return Returns the fail.
     */
    public List<FailData<T>> getFail() {
        return fail;
    }

    /**
     * @param fail The fail to set.
     */
    public void setFail(final List<FailData<T>> fail) {
        this.fail = fail;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(512);
        sb.append("errorcode = [").append(errorCode);
        sb.append("], descArg = [").append(descArg);
        sb.append("], reasonArg = [").append(reasonArg);
        sb.append("], detailArg = [").append(detailArg);
        sb.append("], adviceArg = [").append(adviceArg);
        sb.append("], message = [").append(message).append(']');
        return sb.toString();
    }
}
