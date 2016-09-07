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

import org.junit.Test;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.model.servicemodel.vpn.VpnBasicInfo;
import org.openo.sdno.model.uniformsbi.comnontypes.enums.AdminStatus;
import org.openo.sdno.model.uniformsbi.comnontypes.enums.OperStatus;
import org.openo.sdno.model.uniformsbi.l2vpn.L2Ac;
import org.openo.sdno.model.uniformsbi.l2vpn.L2Acs;
import org.openo.sdno.model.uniformsbi.l2vpn.L2Vpn;

public class L2VpnQueryServiceImplTest {

    @Test
    public void testRefreshVpnAdminStatus() throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        L2VpnQueryServiceImpl demo = new L2VpnQueryServiceImpl();
        Class[] args = new Class[] {Vpn.class, L2Vpn.class};
        Method method = demo.getClass().getDeclaredMethod("refreshVpnAdminStatus", args);
        method.setAccessible(true);
        Vpn vpn = new Vpn();
        L2Vpn l2vpn = new L2Vpn();
        VpnBasicInfo basicinfo = new VpnBasicInfo();
        vpn.setVpnBasicInfo(basicinfo);
        method.invoke(demo, vpn, l2vpn);
        assertTrue(true);
    }

    @Test
    public void testRefreshVpnAdminStatusAdminDown() throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        L2VpnQueryServiceImpl demo = new L2VpnQueryServiceImpl();
        Class[] args = new Class[] {Vpn.class, L2Vpn.class};
        Method method = demo.getClass().getDeclaredMethod("refreshVpnAdminStatus", args);
        method.setAccessible(true);
        Vpn vpn = new Vpn();
        L2Vpn l2vpn = new L2Vpn();
        VpnBasicInfo basicinfo = new VpnBasicInfo();
        vpn.setVpnBasicInfo(basicinfo);
        AdminStatus adminStatus = AdminStatus.ADMIN_DOWN;
        l2vpn.setAdminStatus(adminStatus);
        method.invoke(demo, vpn, l2vpn);
        assertTrue(vpn.getVpnBasicInfo().getAdminStatus().equals("inactive"));
    }

    @Test
    public void testRefreshVpnAdminStatusAdminUp() throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        L2VpnQueryServiceImpl demo = new L2VpnQueryServiceImpl();
        Class[] args = new Class[] {Vpn.class, L2Vpn.class};
        Method method = demo.getClass().getDeclaredMethod("refreshVpnAdminStatus", args);
        method.setAccessible(true);
        Vpn vpn = new Vpn();
        L2Vpn l2vpn = new L2Vpn();
        VpnBasicInfo basicinfo = new VpnBasicInfo();
        vpn.setVpnBasicInfo(basicinfo);
        AdminStatus adminStatus = AdminStatus.ADMIN_UP;
        l2vpn.setAdminStatus(adminStatus);
        method.invoke(demo, vpn, l2vpn);
        assertTrue(vpn.getVpnBasicInfo().getAdminStatus().equals("active"));
    }

    @Test
    public void testRefreshVpnOperStatusNull() throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        L2VpnQueryServiceImpl demo = new L2VpnQueryServiceImpl();
        Class[] args = new Class[] {Vpn.class, L2Vpn.class};
        Method method = demo.getClass().getDeclaredMethod("refreshVpnOperStatus", args);
        method.setAccessible(true);
        Vpn vpn = new Vpn();
        L2Vpn l2vpn = new L2Vpn();
        vpn.setOperStatus("down");
        method.invoke(demo, vpn, l2vpn);
        assertTrue(vpn.getOperStatus().equals("down"));
    }

    @Test
    public void testRefreshVpnOperStatusDown() throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        L2VpnQueryServiceImpl demo = new L2VpnQueryServiceImpl();
        Class[] args = new Class[] {Vpn.class, L2Vpn.class};
        Method method = demo.getClass().getDeclaredMethod("refreshVpnOperStatus", args);
        method.setAccessible(true);
        Vpn vpn = new Vpn();
        L2Vpn l2vpn = new L2Vpn();
        vpn.setOperStatus("down");
        OperStatus operStatus = OperStatus.OPERATE_DOWN;
        l2vpn.setOperStatus(operStatus);
        method.invoke(demo, vpn, l2vpn);
        assertTrue(vpn.getOperStatus().equals("down"));
    }

    @Test
    public void testRefreshVpnOperStatusUp() throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        L2VpnQueryServiceImpl demo = new L2VpnQueryServiceImpl();
        Class[] args = new Class[] {Vpn.class, L2Vpn.class};
        Method method = demo.getClass().getDeclaredMethod("refreshVpnOperStatus", args);
        method.setAccessible(true);
        Vpn vpn = new Vpn();
        L2Vpn l2vpn = new L2Vpn();
        vpn.setOperStatus("down");
        OperStatus operStatus = OperStatus.OPERATE_UP;
        l2vpn.setOperStatus(operStatus);
        method.invoke(demo, vpn, l2vpn);
        assertTrue(vpn.getOperStatus().equals("up"));
    }

    @Test
    public void testRefreshTpAdminStatus() throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        L2VpnQueryServiceImpl demo = new L2VpnQueryServiceImpl();
        Class[] args = new Class[] {Tp.class, L2Ac.class};
        Method method = demo.getClass().getDeclaredMethod("refreshTpAdminStatus", args);
        method.setAccessible(true);
        Tp tp = new Tp();
        L2Ac l2ac = new L2Ac();
        method.invoke(demo, tp, l2ac);
        assertTrue(tp.getAdminStatus().equals("inactive"));
    }

    @Test
    public void testRefreshTpAdminStatusAdminDown() throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        L2VpnQueryServiceImpl demo = new L2VpnQueryServiceImpl();
        Class[] args = new Class[] {Tp.class, L2Ac.class};
        Method method = demo.getClass().getDeclaredMethod("refreshTpAdminStatus", args);
        method.setAccessible(true);
        Tp tp = new Tp();
        L2Ac l2ac = new L2Ac();
        AdminStatus adminStatus = AdminStatus.ADMIN_DOWN;
        l2ac.setAdminStatus(adminStatus);
        method.invoke(demo, tp, l2ac);
        assertTrue(tp.getAdminStatus().equals("inactive"));
    }

    @Test
    public void testRefreshTpAdminStatusAdminUp() throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        L2VpnQueryServiceImpl demo = new L2VpnQueryServiceImpl();
        Class[] args = new Class[] {Tp.class, L2Ac.class};
        Method method = demo.getClass().getDeclaredMethod("refreshTpAdminStatus", args);
        method.setAccessible(true);
        Tp tp = new Tp();
        L2Ac l2ac = new L2Ac();
        AdminStatus adminStatus = AdminStatus.ADMIN_UP;
        l2ac.setAdminStatus(adminStatus);
        method.invoke(demo, tp, l2ac);
        assertTrue(tp.getAdminStatus().equals("active"));
    }

    @Test
    public void testRefreshTpOperateStatusNull() throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        L2VpnQueryServiceImpl demo = new L2VpnQueryServiceImpl();
        Class[] args = new Class[] {Tp.class, L2Ac.class};
        Method method = demo.getClass().getDeclaredMethod("refreshTpOperateStatus", args);
        method.setAccessible(true);
        Tp tp = new Tp();
        L2Ac l2ac = new L2Ac();
        tp.setOperStatus("down");
        method.invoke(demo, tp, l2ac);
        assertTrue(tp.getOperStatus().equals("down"));
    }

    @Test
    public void testRefreshTpOperateStatusUp() throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        L2VpnQueryServiceImpl demo = new L2VpnQueryServiceImpl();
        Class[] args = new Class[] {Tp.class, L2Ac.class};
        Method method = demo.getClass().getDeclaredMethod("refreshTpOperateStatus", args);
        method.setAccessible(true);
        Tp tp = new Tp();
        L2Ac l2ac = new L2Ac();
        tp.setOperStatus("down");
        OperStatus operStatus = OperStatus.OPERATE_UP;
        l2ac.setOperStatus(operStatus);
        method.invoke(demo, tp, l2ac);
        assertTrue(tp.getOperStatus().equals("up"));
    }

    @Test
    public void testRefreshTpOperateStatusDown() throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        L2VpnQueryServiceImpl demo = new L2VpnQueryServiceImpl();
        Class[] args = new Class[] {Tp.class, L2Ac.class};
        Method method = demo.getClass().getDeclaredMethod("refreshTpOperateStatus", args);
        method.setAccessible(true);
        Tp tp = new Tp();
        L2Ac l2ac = new L2Ac();
        tp.setOperStatus("down");
        OperStatus operStatus = OperStatus.OPERATE_DOWN;
        l2ac.setOperStatus(operStatus);
        method.invoke(demo, tp, l2ac);
        assertTrue(tp.getOperStatus().equals("down"));
    }

    @Test
    public void testRrefreshTpNull() throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        L2VpnQueryServiceImpl demo = new L2VpnQueryServiceImpl();
        Class[] args = new Class[] {Vpn.class, L2Vpn.class};
        Method method = demo.getClass().getDeclaredMethod("refreshTp", args);
        method.setAccessible(true);
        Vpn vpn = new Vpn();
        L2Vpn l2vpn = new L2Vpn();
        method.invoke(demo, vpn, l2vpn);
        assertTrue(true);
    }

    @Test
    public void testRrefreshTp() throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        L2VpnQueryServiceImpl demo = new L2VpnQueryServiceImpl();
        Class[] args = new Class[] {Vpn.class, L2Vpn.class};
        Method method = demo.getClass().getDeclaredMethod("refreshTp", args);
        method.setAccessible(true);
        Vpn vpn = new Vpn();
        L2Vpn l2vpn = new L2Vpn();
        L2Acs l2ac = new L2Acs();
        l2vpn.setL2Acs(l2ac);
        method.invoke(demo, vpn, l2vpn);
        assertTrue(true);
    }

}
