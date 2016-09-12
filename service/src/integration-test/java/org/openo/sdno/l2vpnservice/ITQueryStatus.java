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
import org.openo.sdno.testframework.http.model.HttpModelUtils;
import org.openo.sdno.testframework.http.model.HttpRequest;
import org.openo.sdno.testframework.http.model.HttpResponse;
import org.openo.sdno.testframework.http.model.HttpRquestResponse;
import org.openo.sdno.testframework.replace.PathReplace;
import org.openo.sdno.testframework.testmanager.TestManager;
import org.openo.sdno.testframework.topology.ResourceType;
import org.openo.sdno.testframework.topology.Topology;

public class ITQueryStatus extends TestManager {

    private static final String CREATE_L2VPN_TESTCASE =
            "src/integration-test/resources/testcase/createl2vpn/createl2vpn.json";

    private static final String DELETE_L2VPN_TESTCASE =
            "src/integration-test/resources/testcase/deletel2vpn/deletel2vpn.json";

    private static final String QUERY_STATUS_TESTCASE =
            "src/integration-test/resources/testcase/querystatus/querystatus.json";

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
    public void testQueryStatus() throws ServiceException {

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

        // Query Status
        HttpRquestResponse httpQueryStatusObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(QUERY_STATUS_TESTCASE);
        HttpRequest queryStatusRequest = httpQueryStatusObject.getRequest();
        queryStatusRequest.setUri(PathReplace.replaceUuid("l2vpnid", queryStatusRequest.getUri(), createdVpnUUId));
        execTestCase(queryStatusRequest, new SuccessChecker());

        // Delete L2vpn
        HttpRquestResponse httpDeleteObject = HttpModelUtils.praseHttpRquestResponseFromFile(DELETE_L2VPN_TESTCASE);
        HttpRequest deleteRequest = httpDeleteObject.getRequest();
        deleteRequest.setUri(PathReplace.replaceUuid("l2vpnid", deleteRequest.getUri(), createdVpnUUId));
        execTestCase(deleteRequest, new SuccessChecker());
    }

    @Test
    public void testQueryStatusIdNotExist() throws ServiceException {
        HttpRquestResponse httpQueryStatusObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(QUERY_STATUS_TESTCASE);
        HttpRequest queryStatusRequest = httpQueryStatusObject.getRequest();

        queryStatusRequest.setUri(PathReplace.replaceUuid("l2vpnid", queryStatusRequest.getUri(), "NotExistId"));

        execTestCase(queryStatusRequest, new FailChecker());
    }

    @Test
    public void testQueryStatusIdFormatError() throws ServiceException {
        HttpRquestResponse httpQueryStatusObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(QUERY_STATUS_TESTCASE);
        HttpRequest queryStatusRequest = httpQueryStatusObject.getRequest();

        queryStatusRequest.setUri(PathReplace.replaceUuid("l2vpnid", queryStatusRequest.getUri(), "%3333&#"));

        execTestCase(queryStatusRequest, new FailChecker());
    }
}
