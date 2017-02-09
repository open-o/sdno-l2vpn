/*
 * Copyright 2017 Huawei Technologies Co., Ltd.
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

package org.openo.sdno.l2vpnservice.resource;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.baseservice.util.RestUtils;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.l2vpnservice.service.impl.L2vpnServiceImpl;
import org.openo.sdno.model.servicemodel.brs.NetworkElementMO;
import org.openo.sdno.model.servicemodel.mss.BatchAddOrModifyResponse;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.model.servicemodel.vpn.VpnVo;
import org.openo.sdno.model.uniformsbi.l2vpn.L2Vpn;
import org.openo.sdno.result.Result;
import org.openo.sdno.wanvpn.dao.DefaultDao;
import org.openo.sdno.wanvpn.dao.vpn.AbstractVpnDao;
import org.openo.sdno.wanvpn.inventory.sdk.common.OwnerInfoThreadLocal;
import org.openo.sdno.wanvpn.inventory.sdk.common.ServiceTypeEnum;
import org.openo.sdno.wanvpn.util.rest.InventorySDKRestUtil;
import org.openo.sdno.wanvpn.util.rest.RestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/spring/applicationContext.xml",
                "classpath*:META-INF/spring/service.xml", "classpath*:spring/service.xml"})
public class L2VpnServiceResourceTest {

    @Mocked
    HttpServletRequest request;

    @Autowired
    L2VpnServiceResource l2vpnResource;

    private String jsonVpnVo =
            "{\"protectionSchema\":{\"uuid\":\"7d58ddfa-2e77-4eb1-acc6-1493be75e29a\",\"protectType\":\"1:1\",\"layerRate\":null,\"reversionMode\":\"RM_NON_REVERTIVE\",\"wtr\":0},\"pwSchema\":null,\"qosPolicies\":{},\"tunnelSchema\":{\"uuid\":\"905f0215-2ce5-4391-b62c-702e1fb71a3d\",\"tunnelTech\":\"RSVP-TE\",\"tunnelSelectMode\":\"AutoCreate\",\"pwTech\":null,\"tunnelLatency\":12,\"tunnelCreatePolicy\":{\"bestEffort\":\"true\",\"shareMode\":\"N:1\",\"coRoute\":\"false\",\"bfdEnable\":\"true\",\"pathConstraint\":{\"setupPriority\": 5,\"holdupPriority\": 5},\"tunnelProtectpolicy\":{\"uuid\":\"7d58ddfa-2e77-4eb1-acc6-1493be75e29a\",\"protectType\":\"1:1\",\"layerRate\":null,\"reversionMode\":\"RM_REVERTIVE\",\"wtr\":1}}},\"mtu\":null,\"vpn\":{\"id\":\"0de4547b-50b1-4dcd-80a5-a65bd3c4a834\",\"name\":\"l2vpn-te-45-12\",\"description\":\"g234g\",\"vpnBasicInfo\":{\"uuid\":\"fda495ce-d4d1-4d93-b6d2-ef129d595e17\",\"topology\":\"point_to_point\",\"serviceType\":\"l2vpn\",\"technology\":\"mpls\",\"adminStatus\":\"inactive\",\"addtionalInfo\":null},\"operStatus\":null,\"syncStatus\":null,\"accessPointList\":[{\"id\":\"9912eaee-8eda-43b8-a991-6d056f57a454\",\"name\":\"NEPort1\",\"description\":null,\"adminStatus\":null,\"operStatus\":null,\"neId\":\"2d9793c4-02dd-4b7d-975e-53dbc59c6d2b\",\"edgePointRole\":null,\"hubSpoke\":null,\"type\":null,\"workingLayer\":null,\"typeSpecList\":[{\"uuid\":\"50b623a9-f4b6-471b-b949-948998fd5a4b\",\"layerRate\":null,\"ethernetTpSpec\":{\"uuid\":\"3a9efe5c-d6c4-426d-ac8e-141ebfed934d\",\"accessType\":\"dot1q\",\"vlanAction\":null,\"actionValue\":\"KEEP\",\"qinqCvlanList\":null,\"qinqSvlanList\":null,\"dot1qVlanList\":\"43\",\"addtionalInfo\":[]},\"ipTpSpec\":null}],\"peerCeTp\":{\"uuid\":\"0f1d0673-8823-456a-8a13-6c0b28d1a3c0\",\"ceID\":\"d45d9df5-4a1c-4f6f-8be0-dce91394f1c3\",\"ceDirectNeID\":\"2d9793c4-02dd-4b7d-975e-53dbc59c6d2b\",\"ceDirectTPID\":\"3eb1a10d-5d43-468c-80cc-5f067d784b8a\",\"siteName\":\"SiteA\",\"ceName\":\"SiteA\",\"ceIfmasterIp\":\"10.10.10.2\",\"location\":null,\"addtionalInfo\":[]},\"inboundQosPolicyId\":null,\"outboundQosPolicyId\":null,\"inBoundTpCar\":{\"uuid\":\"e437859c-8b62-4155-9703-1be47287ff41\",\"enableCar\":true,\"cir\":4,\"pir\":80,\"cbs\":4,\"pbs\":4},\"outBoundTpCar\":{\"uuid\":\"c4ca48fb-cdbb-4037-b6a7-013ade3f046d\",\"enableCar\":true,\"cir\":4,\"pir\":80,\"cbs\":4,\"pbs\":4},\"routeProtocolSpecs\":[],\"containedMainTP\":\"3eb1a10d-5d43-468c-80cc-5f067d784b8a\",\"addtionalInfo\":[]},{\"id\":\"ff9b2bbb-0db1-414b-83db-cde41990e2b9\",\"name\":\"NEPort2\",\"description\":null,\"adminStatus\":null,\"operStatus\":null,\"neId\":\"e29b2bbb-0db1-414b-83db-cde41990e2b9\",\"edgePointRole\":null,\"hubSpoke\":null,\"type\":null,\"workingLayer\":null,\"typeSpecList\":[{\"uuid\":\"193de5c9-82b0-4d71-b44e-9f12eaab26cb\",\"layerRate\":null,\"ethernetTpSpec\":{\"uuid\":\"de18a6ce-fea3-46ce-b75a-7c02e3853702\",\"accessType\":\"dot1q\",\"vlanAction\":null,\"actionValue\":\"KEEP\",\"qinqCvlanList\":null,\"qinqSvlanList\":null,\"dot1qVlanList\":\"54\",\"addtionalInfo\":[]},\"ipTpSpec\":null}],\"peerCeTp\":{\"uuid\":\"a9edf782-e534-4768-a6a4-9b8022953cea\",\"ceID\":null,\"ceDirectNeID\":null,\"ceDirectTPID\":null,\"siteName\":\"site_t_1\",\"ceName\":\"site_t_1\",\"ceIfmasterIp\":\"11.2.2.11\",\"location\":null,\"addtionalInfo\":[]},\"inboundQosPolicyId\":null,\"outboundQosPolicyId\":null,\"inBoundTpCar\":{\"uuid\":\"feefe322-ad7d-4060-8ea2-a74efeb132ac\",\"enableCar\":true,\"cir\":4,\"pir\":80,\"cbs\":4,\"pbs\":4},\"outBoundTpCar\":{\"uuid\":\"e9cb1fd2-72fe-4a45-aee3-2b355e5f873d\",\"enableCar\":true,\"cir\":4,\"pir\":80,\"cbs\":4,\"pbs\":4},\"routeProtocolSpecs\":[],\"containedMainTP\":\"3d1af9bd-0014-40d7-8412-ab25c92d9941\",\"addtionalInfo\":[]}],\"addtionalInfo\":[]}}";

    private VpnVo vpnVo = buildVpnVo();

    @Before
    public void setUp() throws Exception {
        new MockRestUtil();
        new MockInventorySDKRestUtil();
    }

    @Test
    public void testCreate() throws ServiceException {

        new MockRestUtils();

        Vpn vpn = l2vpnResource.create(request);

        assertTrue(null != vpn);
    }

    @Test
    public void testDelete() throws ServiceException {

        new MockAbstractVpnDao();

        Vpn vpn = l2vpnResource.delete("testuuid", request);

        assertTrue(null != vpn);
    }

    @Test
    public void testGetDetail() throws ServiceException {

        new MockAbstractVpnDao();

        Vpn vpn = l2vpnResource.getDetail("uuid", request);

        assertTrue(null != vpn);
    }

    @Test
    public void testGetStatus() throws ServiceException {

        new MockAbstractVpnDao();
        new MockL2vpnServiceImpl();

        Vpn vpn = l2vpnResource.getStatus("uuid", request);

        assertTrue(null != vpn);
    }

    @Test
    public void testUpdateDesc() throws ServiceException {

        new MockRestUtilsVpn();
        new MockDefaultDao();

        Vpn vpn = l2vpnResource.updateDesc("uuid", request);

        assertTrue(null != vpn);
    }

    private class MockRestUtils extends MockUp<RestUtils> {

        @Mock
        public String getRequestBody(HttpServletRequest request) {
            return jsonVpnVo;
        }
    }

    private class MockRestUtilsVpn extends MockUp<RestUtils> {

        @Mock
        public String getRequestBody(HttpServletRequest request) {
            return JsonUtil.toJson(vpnVo.getVpn());
        }
    }

    private class MockRestUtil extends MockUp<RestUtil> {

        @Mock
        public RestfulResponse get(HttpServletRequest request, String url) throws ServiceException {
            if(url.contains("managed-elements")) {

                NetworkElementMO ne = new NetworkElementMO();
                ne.setNativeID("nativeID");
                ne.setControllerID(Arrays.asList("controlleruuid"));

                Map<String, Object> contentMap = new HashMap<String, Object>();
                contentMap.put("managedElement", ne);

                RestfulResponse response = new RestfulResponse();
                response.setStatus(200);
                response.setResponseJson(JsonUtil.toJson(contentMap));

                return response;
            }

            return new RestfulResponse();
        }

        @Mock
        public RestfulResponse sendGetRequest(String url, RestfulParametes restfulParametes, HttpServletRequest request)
                throws ServiceException {

            Result<String> result = new Result<String>();
            result.setResultObj(JsonUtil.toJson(new L2Vpn()));

            RestfulResponse response = new RestfulResponse();
            response.setStatus(200);
            response.setResponseJson(JsonUtil.toJson(result));

            return response;
        }

        @Mock
        public RestfulResponse sendPutRequest(String url, RestfulParametes restfulParametes, HttpServletRequest request)
                throws ServiceException {

            Result<String> result = new Result<String>();
            result.setResultObj(JsonUtil.toJson(new L2Vpn()));

            RestfulResponse response = new RestfulResponse();
            response.setStatus(200);
            response.setResponseJson(JsonUtil.toJson(result));

            return response;
        }

        @Mock
        public RestfulResponse sendPostRequest(String url, RestfulParametes restfulParametes,
                HttpServletRequest request) throws ServiceException {
            RestfulResponse response = new RestfulResponse();
            response.setStatus(200);

            return response;
        }

        @Mock
        public RestfulResponse sendDeleteRequest(String url, RestfulParametes restfulParametes,
                HttpServletRequest request) throws ServiceException {
            RestfulResponse response = new RestfulResponse();
            response.setStatus(200);
            response.setResponseJson(JsonUtil.toJson(new Result()));

            return response;
        }

    }

    private class MockInventorySDKRestUtil extends MockUp<InventorySDKRestUtil> {

        @Mock
        public RestfulResponse sendPostRequest(String url, RestfulParametes restfulParametes,
                HttpServletRequest request, String owner) throws ServiceException {

            BatchAddOrModifyResponse bModifyResponse = new BatchAddOrModifyResponse();

            RestfulResponse response = new RestfulResponse();
            response.setStatus(200);
            response.setResponseJson(JsonUtil.toJson(bModifyResponse));

            return response;
        }

        @Mock
        public RestfulResponse sendPutRequest(String url, RestfulParametes restfulParametes, HttpServletRequest request,
                String owner) throws ServiceException {

            BatchAddOrModifyResponse bModifyResponse = new BatchAddOrModifyResponse();

            RestfulResponse response = new RestfulResponse();
            response.setStatus(200);
            response.setResponseJson(JsonUtil.toJson(bModifyResponse));

            return response;
        }

        @Mock
        public RestfulResponse sendGetRequest(String url, RestfulParametes restfulParametes, HttpServletRequest request,
                String owner) throws ServiceException {

            // BatchAddOrModifyResponse bModifyResponse = new BatchAddOrModifyResponse();

            Map<String, Object> rsp = new HashMap<String, Object>();
            rsp.put("controllerId", "uuid");

            Map<String, Object> contentMap = new HashMap<String, Object>();
            contentMap.put("object", rsp);

            RestfulResponse response = new RestfulResponse();
            response.setStatus(200);
            response.setResponseJson(JsonUtil.toJson(contentMap));

            return response;
        }

        @Mock
        public RestfulResponse sendDeleteRequest(String url, RestfulParametes restfulParametes,
                HttpServletRequest request, String owner) throws ServiceException {

            RestfulResponse response = new RestfulResponse();
            response.setStatus(200);

            return response;
        }
    }

    private class MockAbstractVpnDao<P> extends MockUp<AbstractVpnDao> {

        @Mock
        public List<Vpn> assembleMo(final List<P> vpnPos) throws ServiceException {
            return Arrays.asList(vpnVo.getVpn());
        }

    }

    private class MockDefaultDao<M> extends MockUp<DefaultDao> {

        @Mock
        public M getMoById(final String uuid) throws ServiceException {
            return (M)vpnVo.getVpn();
        }
    }

    private class MockL2vpnServiceImpl extends MockUp<L2vpnServiceImpl> {

        @Mock
        public Vpn getDetail(String uuid, @Context HttpServletRequest request) throws ServiceException {
            return vpnVo.getVpn();
        }
    }

    private class MockOwnerInfoThreadLocal extends MockUp<OwnerInfoThreadLocal> {

        @Mock
        public void setOwnerInfo(@Context HttpServletRequest request, final ServiceTypeEnum owner) {
        }

        @Mock
        public HttpServletRequest getHttpServletRequest() {
            return request;
        }
    }

    private VpnVo buildVpnVo() {
        return JsonUtil.fromJson(jsonVpnVo, VpnVo.class);
    }

}
