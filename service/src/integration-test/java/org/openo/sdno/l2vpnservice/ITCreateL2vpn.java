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

package org.openo.sdno.l2vpnservice;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.l2vpnservice.checker.FailChecker;
import org.openo.sdno.l2vpnservice.checker.SuccessChecker;
import org.openo.sdno.l2vpnservice.mocoserver.L2vpnSbiAdapterServer;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.model.servicemodel.vpn.VpnVo;
import org.openo.sdno.testframework.checker.ServiceExceptionChecker;
import org.openo.sdno.testframework.http.model.HttpModelUtils;
import org.openo.sdno.testframework.http.model.HttpRequest;
import org.openo.sdno.testframework.http.model.HttpResponse;
import org.openo.sdno.testframework.http.model.HttpRquestResponse;
import org.openo.sdno.testframework.replace.PathReplace;
import org.openo.sdno.testframework.testmanager.TestManager;
import org.openo.sdno.testframework.topology.ResourceType;
import org.openo.sdno.testframework.topology.Topology;
import org.openo.sdno.wanvpn.util.error.ErrorCodeUtils;

public class ITCreateL2vpn extends TestManager {

    private static final String CREATE_L2VPN_TESTCASE =
            "src/integration-test/resources/testcase/createl2vpn/createl2vpn.json";

    private static final String QUERY_L2VPN_TESTCASE =
            "src/integration-test/resources/testcase/queryl2vpn/queryl2vpn.json";

    private static final String DELETE_L2VPN_TESTCASE =
            "src/integration-test/resources/testcase/deletel2vpn/deletel2vpn.json";

    private static final String UPDATE_L2VPN_TESTCASE =
            "src/integration-test/resources/testcase/updatel2vpn/updatel2vpn.json";

    private static final String CREATE_L2VPN_ID_ERROR_TESTCASE =
            "src/integration-test/resources/testcase/createl2vpn/createl2vpniderror.json";

    private static final String CREATE_L2VPN_NE_NOTEXIST_TESTCASE =
            "src/integration-test/resources/testcase/createl2vpn/createl2vpnidnotexist.json";

    private static final String TOPODATA_PATH = "src/integration-test/resources/topodata";

    private static Topology topo = new Topology(TOPODATA_PATH);

    private static L2vpnSbiAdapterServer adapterServer = new L2vpnSbiAdapterServer();

    @BeforeClass
    public static void setup() throws ServiceException {
        topo.createInvTopology();
        adapterServer.start();
    }

    @AfterClass
    public static void tearDown() throws ServiceException {
        topo.clearInvTopology();
        adapterServer.stop();
    }

    @Test
    public void TestL2vpnSucess() throws ServiceException, InterruptedException {

        HttpRquestResponse httpCreateObject = HttpModelUtils.praseHttpRquestResponseFromFile(CREATE_L2VPN_TESTCASE);
        HttpRequest createRequest = httpCreateObject.getRequest();
        VpnVo vpnData = JsonUtil.fromJson(createRequest.getData(), VpnVo.class);
        List<Tp> tpList = vpnData.getVpn().getAccessPointList();
        tpList.get(0).setNeId(topo.getResourceUuid(ResourceType.NETWORKELEMENT, "Ne1"));
        tpList.get(1).setNeId(topo.getResourceUuid(ResourceType.NETWORKELEMENT, "Ne2"));
        createRequest.setData(JsonUtil.toJson(vpnData));
        HttpResponse createResponse = execTestCase(createRequest, new SuccessChecker());
        Vpn createdVpn = JsonUtil.fromJson(createResponse.getData(), Vpn.class);
        String createdVpnUUId = createdVpn.getUuid();

        HttpRquestResponse httpQueryObject = HttpModelUtils.praseHttpRquestResponseFromFile(QUERY_L2VPN_TESTCASE);
        HttpRequest queryRequest = httpQueryObject.getRequest();

        queryRequest.setUri(PathReplace.replaceUuid("l2vpnid", queryRequest.getUri(), createdVpnUUId));

        execTestCase(queryRequest, new SuccessChecker());

        HttpRquestResponse httpUpdateObject = HttpModelUtils.praseHttpRquestResponseFromFile(UPDATE_L2VPN_TESTCASE);
        HttpRequest updateRequest = httpUpdateObject.getRequest();
        updateRequest.setUri(PathReplace.replaceUuid("l2vpnid", updateRequest.getUri(), createdVpnUUId));
        execTestCase(updateRequest, new SuccessChecker());

        execTestCase(queryRequest, new SuccessChecker());

        HttpRquestResponse httpDeleteObject = HttpModelUtils.praseHttpRquestResponseFromFile(DELETE_L2VPN_TESTCASE);
        HttpRequest deleteRequest = httpDeleteObject.getRequest();

        deleteRequest.setUri(PathReplace.replaceUuid("l2vpnid", deleteRequest.getUri(), createdVpnUUId));

        execTestCase(deleteRequest, new SuccessChecker());
    }

    @Test
    public void testCreateL2vpnDataInvalid() throws ServiceException {
        HttpRquestResponse httpCreateObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(CREATE_L2VPN_ID_ERROR_TESTCASE);
        HttpRequest createRequest = httpCreateObject.getRequest();
        VpnVo vpnData = JsonUtil.fromJson(createRequest.getData(), VpnVo.class);
        List<Tp> tpList = vpnData.getVpn().getAccessPointList();
        tpList.get(0).setNeId(topo.getResourceUuid(ResourceType.NETWORKELEMENT, "Ne1"));
        tpList.get(1).setNeId(topo.getResourceUuid(ResourceType.NETWORKELEMENT, "Ne2"));
        createRequest.setData(JsonUtil.toJson(vpnData));
        execTestCase(createRequest,
                new ServiceExceptionChecker(ErrorCodeUtils.getErrorCode("wanvpncommon", "ip", "ip_invalid")));
    }

    @Test
    public void testCreateL2vpnNeNotExist() throws ServiceException {
        HttpRquestResponse httpCreateObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(CREATE_L2VPN_NE_NOTEXIST_TESTCASE);
        HttpRequest createRequest = httpCreateObject.getRequest();
        VpnVo vpnData = JsonUtil.fromJson(createRequest.getData(), VpnVo.class);
        List<Tp> tpList = vpnData.getVpn().getAccessPointList();
        tpList.get(0).setNeId("invalidid");
        tpList.get(1).setNeId("invalidid");
        createRequest.setData(JsonUtil.toJson(vpnData));
        execTestCase(createRequest, new FailChecker());
    }

    @Test
    public void testL2vpnNameDuplicate() throws ServiceException {
        HttpRquestResponse httpCreateObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(CREATE_L2VPN_NE_NOTEXIST_TESTCASE);
        HttpRequest createRequest = httpCreateObject.getRequest();
        VpnVo vpnData = JsonUtil.fromJson(createRequest.getData(), VpnVo.class);
        List<Tp> tpList = vpnData.getVpn().getAccessPointList();
        tpList.get(0).setNeId("invalidid");
        tpList.get(1).setNeId("invalidid");
        createRequest.setData(JsonUtil.toJson(vpnData));
        execTestCase(createRequest, new FailChecker());
    }

    @Test
    public void testQueryL2VpnIdNotExist() throws ServiceException {
        HttpRquestResponse httpQueryObject = HttpModelUtils.praseHttpRquestResponseFromFile(QUERY_L2VPN_TESTCASE);
        HttpRequest queryRequest = httpQueryObject.getRequest();

        queryRequest.setUri(PathReplace.replaceUuid("l2vpnid", queryRequest.getUri(), "NotExistId"));

        execTestCase(queryRequest, new FailChecker());
    }

    @Test
    public void testQueryL2VpnIdFormatError() throws ServiceException {
        HttpRquestResponse httpQueryObject = HttpModelUtils.praseHttpRquestResponseFromFile(QUERY_L2VPN_TESTCASE);
        HttpRequest queryRequest = httpQueryObject.getRequest();

        queryRequest.setUri(PathReplace.replaceUuid("l2vpnid", queryRequest.getUri(), "%3333&#"));

        execTestCase(queryRequest, new FailChecker());
    }

    @Test
    public void testUpdateL2VpnIdNotExist() throws ServiceException {
        HttpRquestResponse httpUpdateObject = HttpModelUtils.praseHttpRquestResponseFromFile(UPDATE_L2VPN_TESTCASE);
        HttpRequest updateRequest = httpUpdateObject.getRequest();

        updateRequest.setUri(PathReplace.replaceUuid("l2vpnid", updateRequest.getUri(), "NotExistId"));

        execTestCase(updateRequest, new FailChecker());
    }

    @Test
    public void testUpdateL2VpnIdFormatError() throws ServiceException {
        HttpRquestResponse httpUpdateObject = HttpModelUtils.praseHttpRquestResponseFromFile(UPDATE_L2VPN_TESTCASE);
        HttpRequest updateRequest = httpUpdateObject.getRequest();

        updateRequest.setUri(PathReplace.replaceUuid("l2vpnid", updateRequest.getUri(), "%3333&#"));

        execTestCase(updateRequest, new FailChecker());
    }

    @Test
    public void testDeleteL2VpnIdNotExist() throws ServiceException {
        HttpRquestResponse httpDeleteObject = HttpModelUtils.praseHttpRquestResponseFromFile(DELETE_L2VPN_TESTCASE);
        HttpRequest deleteRequest = httpDeleteObject.getRequest();

        deleteRequest.setUri(PathReplace.replaceUuid("l2vpnid", deleteRequest.getUri(), "NotExistId"));

        execTestCase(deleteRequest, new FailChecker());
    }

    @Test
    public void testDeleteL2VpnIdFormatError() throws ServiceException {
        HttpRquestResponse httpDeleteObject = HttpModelUtils.praseHttpRquestResponseFromFile(DELETE_L2VPN_TESTCASE);
        HttpRequest deleteRequest = httpDeleteObject.getRequest();

        deleteRequest.setUri(PathReplace.replaceUuid("l2vpnid", deleteRequest.getUri(), "%3333&#"));

        execTestCase(deleteRequest, new FailChecker());
    }
}
