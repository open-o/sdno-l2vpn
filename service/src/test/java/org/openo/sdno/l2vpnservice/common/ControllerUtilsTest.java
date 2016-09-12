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

package org.openo.sdno.l2vpnservice.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.model.servicemodel.brs.ControllerMO;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.vpn.Vpn;

/**
 * ControllerUtilsTest class.<br>
 * 
 * @author
 * @version SDNO 0.5 2016年8月11日
 */
public class ControllerUtilsTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGetControllerTypeWithNullInput() throws ServiceException {
        Vpn vpn = null;
        assertNull(ControllerUtils.getControllerType(vpn));
    }

    @Test
    public void testGetControllerTypeWithNullMo() throws ServiceException {
        Vpn vpn = new Vpn();
        assertNull(ControllerUtils.getControllerType(vpn));
    }

    @Test
    public void testGetControllerType() throws ServiceException {
        Vpn vpn = new Vpn();
        List<Tp> list = new ArrayList<>();
        Tp tp = new Tp();
        ControllerMO contollerMO = new ControllerMO();
        contollerMO.setProductName("productName");
        tp.setContollerMO(contollerMO);
        list.add(tp);
        vpn.setAccessPointList(list);
        assertEquals("productName", ControllerUtils.getControllerType(vpn));
    }

    @Test
    public void testGetControllerTypeTpWithNullInput() throws ServiceException {
        Tp tp = null;
        assertNull(ControllerUtils.getControllerType(tp));
    }

    @Test
    public void testGetControllerTypeTpWithNullMo() throws ServiceException {
        Tp tp = new Tp();
        assertNull(ControllerUtils.getControllerType(tp));
    }

    @Test
    public void testGetControllerTypeTp() throws ServiceException {
        Tp tp = new Tp();
        ControllerMO contollerMO = new ControllerMO();
        contollerMO.setProductName("productName");
        tp.setContollerMO(contollerMO);

        assertEquals("productName", ControllerUtils.getControllerType(tp));
    }

    @Test
    public void testGetControllerMoWithNullInput() throws ServiceException {
        Tp tp = null;

        assertNull(ControllerUtils.getControllerMO(tp));
    }

    @Test
    public void testGetControllerUUIDReturnNull() throws ServiceException {
        Vpn vpn = new Vpn();
        assertNull(ControllerUtils.getControllerUUID(vpn));
    }

    @Test
    public void testGetControllerUUIDReturnNull2() throws ServiceException {
        Vpn vpn = new Vpn();
        List<Tp> list = new ArrayList<>();
        Tp tp = new Tp();

        list.add(tp);
        vpn.setAccessPointList(list);
        assertNull(ControllerUtils.getControllerUUID(vpn));
    }

    @Test
    public void testGetControllerUUID() throws ServiceException {
        Vpn vpn = new Vpn();
        List<Tp> list = new ArrayList<>();
        Tp tp = new Tp();
        ControllerMO contollerMO = new ControllerMO();
        contollerMO.setProductName("productName");
        tp.setContollerMO(contollerMO);
        list.add(tp);
        vpn.setAccessPointList(list);
        assertNull(ControllerUtils.getControllerUUID(vpn));
    }

    @Test
    public void testGetControllerUUIDTpReturnNull() throws ServiceException {
        Tp tp = new Tp();
        assertNull(ControllerUtils.getControllerUUID(tp));
    }

    @Test
    public void testGetControllerUUIDTp() throws ServiceException {
        Tp tp = new Tp();
        ControllerMO contollerMO = new ControllerMO();
        contollerMO.setObjectId("objectId");
        tp.setContollerMO(contollerMO);

        assertEquals("objectId", ControllerUtils.getControllerUUID(tp));
    }
}
