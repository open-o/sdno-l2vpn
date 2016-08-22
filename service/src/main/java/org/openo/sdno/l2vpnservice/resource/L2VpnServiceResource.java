/*
 * Copyright (c) 2016, Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openo.sdno.l2vpnservice.resource;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.util.RestUtils;
import org.openo.sdno.framework.container.service.IResource;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.l2vpnservice.service.inf.L2VpnService;
import org.openo.sdno.model.servicemodel.SvcModel;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.model.servicemodel.vpn.VpnVo;
import org.openo.sdno.wanvpn.inventory.sdk.common.OwnerInfoThreadLocal;
import org.openo.sdno.wanvpn.inventory.sdk.common.ServiceTypeEnum;
import org.openo.sdno.wanvpn.util.checker.ScopeChecker;
import org.openo.sdno.wanvpn.util.error.CommonErrorCode;
import org.openo.sdno.wanvpn.util.error.ServiceExceptionUtil;
import org.openo.sdno.wanvpn.util.executor.ExecutorUtils;
import org.openo.sdno.wanvpn.util.executor.resource.svc.SvcResourceCreateExecutor;
import org.openo.sdno.wanvpn.util.executor.resource.svc.SvcResourceDeleteUUIDExecutor;
import org.openo.sdno.wanvpn.util.executor.resource.svc.SvcResourceQuerySingleExecutor;
import org.openo.sdno.wanvpn.util.executor.resource.svc.SvcResourceUpdateExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * l2vpn service resource class.<br/>
 *
 * @author
 * @version SDNO 0.5 Aug 9, 2016
 */
@Path("/sdnol2vpn/v1/l2vpns")
public class L2VpnServiceResource extends IResource<L2VpnService> {

    private static final int MAXLENGTH = 200;

    private static final Logger LOGGER = LoggerFactory.getLogger(L2VpnServiceResource.class);

    @Override
    public String getResUri() {
        return "/sdnol2vpn/v1/l2vpns";
    }

    /**
     * create l2vpn.<br/>
     *
     * @param request http request context.
     * @return vpn created.
     * @throws ServiceException if data base operation failed.
     * @since SDNO 0.5
     */
    @POST
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Vpn create(@Context final HttpServletRequest request) throws ServiceException {
        return new SvcResourceCreateExecutor<VpnVo, Vpn>(request) {

            @Override
            protected void assertParam(final VpnVo vpnVo) throws ServiceException {
                checkScope(vpnVo.getVpn());
                checkScope(vpnVo.getTunnelSchema());
            }

            @Override
            protected VpnVo extractParam(@Context HttpServletRequest request) throws ServiceException {
                return JsonUtil.fromJson(RestUtils.getRequestBody(request), VpnVo.class);
            }

            @Override
            protected Vpn implementCreate(final VpnVo vpnVo) throws ServiceException {
                setL2vpnOwnerInfoTl(request);
                return service.create(vpnVo, request);
            }
        }.execute().getResult();
    }

    protected void setL2vpnOwnerInfoTl(@Context final HttpServletRequest request) {
        OwnerInfoThreadLocal.setOwnerInfo(request, ServiceTypeEnum.L2VPN);
    }

    /**
     * delete vpn.<br/>
     *
     * @param uuid uuid of the vpn.
     * @param request http request context.
     * @return vpn created.
     * @throws ServiceException if data base operation failed.
     * @since SDNO 0.5
     */
    @DELETE
    @Path("/{uuid}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Vpn delete(@PathParam(value = "uuid") final String uuid, @Context final HttpServletRequest request)
            throws ServiceException {
        return new SvcResourceDeleteUUIDExecutor<Vpn>(uuid) {

            @Override
            protected Vpn implementDelete(final String uuid) throws ServiceException {
                setL2vpnOwnerInfoTl(request);
                final Vpn vpn = getVpnForDelete(uuid, request);
                if(null == vpn) {
                    return getOneDeletedVpn(uuid);
                }
                return service.delete(vpn, request);
            }
        }.execute().getResult();
    }

    private Vpn getOneDeletedVpn(final String uuid) {
        final Vpn result = new Vpn();
        result.setUuid(uuid);
        return result;
    }

    private Vpn getVpnForDelete(final String uuid, @Context final HttpServletRequest request) throws ServiceException {
        try {
            return service.getDetail(uuid, request);
        } catch(ServiceException e) {
            LOGGER.warn("vpn doesn't exist or has been deleted " + uuid);
            throw e;
        }
    }

    /**
     * get detail of one vpn.<br/>
     *
     * @param uuid uuid of the vpn.
     * @param request http request context.
     * @return vpn created.
     * @throws ServiceException if data base operation failed.
     * @since SDNO 0.5
     */
    @GET
    @Path("/{uuid}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Vpn getDetail(@PathParam("uuid") final String uuid, @Context final HttpServletRequest request)
            throws ServiceException {
        return new SvcResourceQuerySingleExecutor<Vpn>(uuid) {

            @Override
            protected Vpn implementQuery(final String uuid) throws ServiceException {
                setL2vpnOwnerInfoTl(request);
                return service.getDetail(uuid, request);
            }
        }.execute().getResult();
    }

    /**
     * get status of one vpn.<br/>
     *
     * @param uuid uuid of the vpn.
     * @param request http request context.
     * @return vpn created.
     * @throws ServiceException if data base operation failed.
     * @since SDNO 0.5
     */
    @GET
    @Path("/{uuid}/status")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Vpn getStatus(@PathParam("uuid") final String uuid, @Context final HttpServletRequest request)
            throws ServiceException {
        return new SvcResourceQuerySingleExecutor<Vpn>(uuid) {

            @Override
            protected Vpn implementQuery(final String uuid) throws ServiceException {
                setL2vpnOwnerInfoTl(request);
                final Vpn vpn = service.getDetail(uuid, request);
                return service.getStatus(vpn, request);
            }
        }.execute().getResult();
    }

    /**
     * update one vpn.<br/>
     *
     * @param uuid uuid of the vpn.
     * @param request http request context.
     * @return vpn created.
     * @throws ServiceException if data base operation failed.
     * @since SDNO 0.5
     */
    @PUT
    @Path("/{uuid}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Vpn updateDesc(@PathParam("uuid") final String uuid, @Context final HttpServletRequest request)
            throws ServiceException {
        return new SvcResourceUpdateExecutor<Vpn, Vpn>(request) {

            @Override
            protected Vpn extractParam(@Context HttpServletRequest request) throws ServiceException {
                return JsonUtil.fromJson(RestUtils.getRequestBody(request), Vpn.class);
            }

            @Override
            protected Vpn implementUpdate(final Vpn vpn) throws ServiceException {
                setL2vpnOwnerInfoTl(request);
                vpn.setUuid(uuid);
                return service.modifyDesc(vpn, request);
            }

            @Override
            protected void assertParam(final Vpn param) throws ServiceException {
                ExecutorUtils.assertUUID(uuid);
                if(null == param.getDescription()) {
                    param.setDescription("");
                }

                if(param.getDescription().length() > MAXLENGTH) {
                    throw ServiceExceptionUtil.getBadRequestServiceExceptionWithCommonArgs(
                            CommonErrorCode.CHECKER_STRING_OVER_LENGTH,
                            new String[] {String.valueOf(param.getDescription())});
                }
            }
        }.execute().getResult();
    }

    private void checkScope(final SvcModel model) throws ServiceException {
        ScopeChecker.checkScope(model);
    }
}
