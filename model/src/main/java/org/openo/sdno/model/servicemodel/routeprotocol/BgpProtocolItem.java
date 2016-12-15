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

package org.openo.sdno.model.servicemodel.routeprotocol;

import org.openo.sdno.wanvpn.util.paradesc.EnumDesc;
import org.openo.sdno.wanvpn.util.paradesc.IPDesc;
import org.openo.sdno.wanvpn.util.paradesc.IntegerDesc;
import org.openo.sdno.wanvpn.util.paradesc.NoPrintField;
import org.openo.sdno.wanvpn.util.paradesc.StringDesc;
import org.openo.sdno.model.servicemodel.AbstractSvcModel;
import org.openo.sdno.model.servicemodel.common.enumeration.BooleanType;

/**
 * Routing protocol item class.<br>
 * 
 * @author
 * @version SDNO 0.5 August 4, 2016
 */
public class BgpProtocolItem extends AbstractSvcModel {

    @StringDesc(maxLen = 36)
    private String id;

    private Integer idx;

    private Integer peerAsNumber;

    @IntegerDesc(minVal = 0, maxVal = 21845)
    private Integer keepAliveTime;

    @IntegerDesc(minVal = 0, maxVal = 65535)
    private Integer holdTime;

    @StringDesc(maxLen = 200)
    @NoPrintField
    private String password;

    private Integer bgpMaxPrefix;

    private Integer bgpMaxPrefixAlarm;

    @IPDesc(hasMask = false)
    private String peerIp;

    @StringDesc(maxLen = 200)
    @EnumDesc(BooleanType.class)
    private String community;

    @StringDesc(maxLen = 200)
    @EnumDesc(BooleanType.class)
    private String extCommunity;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(final Integer idx) {
        this.idx = idx;
    }

    public Integer getPeerAsNumber() {
        return peerAsNumber;
    }

    public void setPeerAsNumber(final Integer peerAsNumber) {
        this.peerAsNumber = peerAsNumber;
    }

    public Integer getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(final Integer keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public Integer getHoldTime() {
        return holdTime;
    }

    public void setHoldTime(final Integer holdTime) {
        this.holdTime = holdTime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public Integer getBgpMaxPrefix() {
        return bgpMaxPrefix;
    }

    public void setBgpMaxPrefix(final Integer bgpMaxPrefix) {
        this.bgpMaxPrefix = bgpMaxPrefix;
    }

    public Integer getBgpMaxPrefixAlarm() {
        return bgpMaxPrefixAlarm;
    }

    public void setBgpMaxPrefixAlarm(final Integer bgpMaxPrefixAlarm) {
        this.bgpMaxPrefixAlarm = bgpMaxPrefixAlarm;
    }

    public String getPeerIp() {
        return peerIp;
    }

    public void setPeerIp(final String peerIp) {
        this.peerIp = peerIp;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(final String community) {
        this.community = community;
    }

    public String getExtCommunity() {
        return extCommunity;
    }

    public void setExtCommunity(final String extCommunity) {
        this.extCommunity = extCommunity;
    }

    @Override
    public String getUuid() {
        return id;
    }

    @Override
    public void setUuid(final String uuid) {
        this.id = uuid;
    }

    /**
     * Set BGP protocol fields value.<br>
     * 
     * @param bgpProtocolItem bgpProtocolItem
     * @since SDNO 0.5
     */
    public void refDefinedBgp(final BgpProtocolItem bgpProtocolItem) {
        this.extCommunity = bgpProtocolItem.getExtCommunity();
        this.community = bgpProtocolItem.getCommunity();
        this.bgpMaxPrefixAlarm = bgpProtocolItem.getBgpMaxPrefixAlarm();
        this.bgpMaxPrefix = bgpProtocolItem.getBgpMaxPrefix();
        this.password = bgpProtocolItem.getPassword();
        this.holdTime = bgpProtocolItem.getHoldTime();
        this.keepAliveTime = bgpProtocolItem.getKeepAliveTime();
    }

}
