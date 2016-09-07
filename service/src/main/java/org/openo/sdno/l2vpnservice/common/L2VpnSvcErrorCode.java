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

package org.openo.sdno.l2vpnservice.common;

import org.openo.sdno.wanvpn.util.error.ErrorCodeUtils;

/**
 * Error code class, defining the error code in l2vpn service.<br/>
 * 
 * @author
 * @version SDNO 0.5 Aug 8, 2016
 */
public class L2VpnSvcErrorCode {

    /**
     * app name
     */
    public static final String APP_NAME = "l2vpnservice";

    /**
     * create l2vpn not two site
     */
    public static final String CREATE_L2VPN_NOT_TWO_SITE =
            ErrorCodeUtils.getErrorCode(L2VpnSvcErrorCode.APP_NAME, "vpn", "create_l2vpn_not_two_site");

    /**
     * delete active vpn
     */
    public static final String DELETE_ACTIVE_VPN =
            ErrorCodeUtils.getErrorCode(L2VpnSvcErrorCode.APP_NAME, "vpn", "delete_active_vpn");

    /**
     * vpn not exist
     */
    public static final String VPN_NOT_EXIST =
            ErrorCodeUtils.getErrorCode(L2VpnSvcErrorCode.APP_NAME, "vpn", "vpn_not_exist");

    /**
     * tp not exist
     */
    public static final String TP_NOT_EXIST =
            ErrorCodeUtils.getErrorCode(L2VpnSvcErrorCode.APP_NAME, "tp", "tp_not_exist");

    /**
     * service type error
     */
    public static final String VPN_SERVICETYPE_ERROR =
            ErrorCodeUtils.getErrorCode(L2VpnSvcErrorCode.APP_NAME, "vpn", "service_type_error");

    /**
     * topology type error
     */
    public static final String VPN_TOPOLOGYTYPE_ERROR =
            ErrorCodeUtils.getErrorCode(L2VpnSvcErrorCode.APP_NAME, "vpn", "topology_type_error");

    /**
     * tp um error
     */
    public static final String VPN_TP_NUM_ERROR =
            ErrorCodeUtils.getErrorCode(L2VpnSvcErrorCode.APP_NAME, "vpn", "tp_num_error");

    /**
     * vpn name duplicate
     */
    public static final String L2VPN_NAME_DUPLICATE =
            ErrorCodeUtils.getErrorCode(L2VpnSvcErrorCode.APP_NAME, "vpn", "vpn_name_duplicate");

    /**
     * update vpn desc controller fail
     */
    public static final String UPDATEDESC_CONTROLLER_FAIL =
            ErrorCodeUtils.getErrorCode(L2VpnSvcErrorCode.APP_NAME, "vpn", "update_vpn_desc_controller_fail");

    /**
     * create vpn controller fail
     */
    public static final String CREATE_CONTROLLER_FAIL =
            ErrorCodeUtils.getErrorCode(L2VpnSvcErrorCode.APP_NAME, "vpn", "create_vpn_controller_fail");

    /**
     * delete vpn controller fail
     */
    public static final String DELETE_CONTROLLER_FAIL =
            ErrorCodeUtils.getErrorCode(L2VpnSvcErrorCode.APP_NAME, "vpn", "delete_vpn_controller_fail");

    /**
     * update vpn status controller fail
     */
    public static final String UPDATE_STATUS_CONTROLLER_FAIL =
            ErrorCodeUtils.getErrorCode(L2VpnSvcErrorCode.APP_NAME, "vpn_status", "update_vpn_status_controller_fail");

    /**
     * get vpn status controller fail
     */
    public static final String GET_STATUS_CONTROLLER_FAIL =
            ErrorCodeUtils.getErrorCode(L2VpnSvcErrorCode.APP_NAME, "vpn_status", "get_vpn_status_controller_fail");

    /**
     * get vpn controller fail
     */
    public static final String GET_CONTROLLER_FAIL =
            ErrorCodeUtils.getErrorCode(L2VpnSvcErrorCode.APP_NAME, "vpn", "get_vpn_controller_fail");

    /**
     * tp not bind with vpn
     */
    public static final String TP_NOT_BIND_WITH_VPN =
            ErrorCodeUtils.getErrorCode(L2VpnSvcErrorCode.APP_NAME, "tp", "tp_not_bind_with_vpn");

    /**
     * modify qos para error
     */
    public static final String CAR_PARA_ERROR =
            ErrorCodeUtils.getErrorCode(L2VpnSvcErrorCode.APP_NAME, "tp", "modify_qos_para_error");

    /**
     * not support
     */
    public static final String OPERATION_NOT_SUPPORT =
            ErrorCodeUtils.getErrorCode(L2VpnSvcErrorCode.APP_NAME, "oper", "not_support");

    /**
     * get te controller return fail
     */
    public static final String GET_TE_CONTROLLER_FAIL =
            ErrorCodeUtils.getErrorCode(L2VpnSvcErrorCode.APP_NAME, "te", "get_te_controller_return_fail");

    /**
     * create vpn tp controller fail
     */
    public static final String L2VPN_CREATE_TP_CONTROLLER_FAIL =
            ErrorCodeUtils.getErrorCode(APP_NAME, "vpn", "create_vpn_tp_controller_fail");

    /**
     * delete vpn tp controller fail
     */
    public static final String L2VPN_DELETE_TP_CONTROLLER_FAIL =
            ErrorCodeUtils.getErrorCode(APP_NAME, "vpn", "delete_vpn_tp_controller_fail");

    /**
     * update vpn tp status controller fail
     */
    public static final String L2VPN_UPDATE_TP_STATUS_CONTROLLER_FAIL =
            ErrorCodeUtils.getErrorCode(APP_NAME, "vpn", "update_vpn_tp_status_controller_fail");

    private L2VpnSvcErrorCode() {
    }
}
