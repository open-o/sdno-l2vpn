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

package org.openo.sdno.l2vpnservice.service.impl;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.model.servicemodel.vpn.VpnBasicInfo;

import mockit.Mocked;

public class L2VpnDeleteServiceImplTest {

    @Mocked
    HttpServletRequest request;

    @Test(expected = ServiceException.class)
    public void testDeleteNull() throws ServiceException {
        L2VpnDeleteServiceImpl demo = new L2VpnDeleteServiceImpl();
        demo.delete(null, request);
    }

    @Test
    public void testCheckDataStatusNull() throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        L2VpnDeleteServiceImpl demo = new L2VpnDeleteServiceImpl();
        Method method = demo.getClass().getDeclaredMethod("checkDataStatus", Vpn.class);
        method.setAccessible(true);
        Vpn vpn = new Vpn();
        method.invoke(demo, vpn);
        assertTrue(true);

    }

    @Test
    public void testCheckDataStatusNotNull() throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        L2VpnDeleteServiceImpl demo = new L2VpnDeleteServiceImpl();
        Method method = demo.getClass().getDeclaredMethod("checkDataStatus", Vpn.class);
        method.setAccessible(true);
        Vpn vpn = new Vpn();
        VpnBasicInfo basicInfo = new VpnBasicInfo();
        vpn.setVpnBasicInfo(basicInfo);
        method.invoke(demo, vpn);
        assertTrue(true);
    }

}