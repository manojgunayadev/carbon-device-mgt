/*
*  Copyright (c) 2015 WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/

package org.wso2.carbon.policy.mgt.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.device.mgt.common.DeviceIdentifier;
import org.wso2.carbon.device.mgt.common.Feature;
import org.wso2.carbon.policy.mgt.common.FeatureManagementException;
import org.wso2.carbon.policy.mgt.common.Policy;
import org.wso2.carbon.policy.mgt.common.PolicyAdministratorPoint;
import org.wso2.carbon.policy.mgt.common.PolicyEvaluationException;
import org.wso2.carbon.policy.mgt.common.PolicyEvaluationPoint;
import org.wso2.carbon.policy.mgt.common.PolicyInformationPoint;
import org.wso2.carbon.policy.mgt.common.PolicyManagementException;
import org.wso2.carbon.policy.mgt.common.Profile;
import org.wso2.carbon.policy.mgt.common.ProfileFeature;
import org.wso2.carbon.policy.mgt.core.impl.PolicyAdministratorPointImpl;
import org.wso2.carbon.policy.mgt.core.impl.PolicyInformationPointImpl;
import org.wso2.carbon.policy.mgt.core.internal.PolicyManagementDataHolder;

import java.util.List;

public class PolicyManagerServiceImpl implements PolicyManagerService {

    private static final Log log = LogFactory.getLog(PolicyManagerServiceImpl.class);

    PolicyAdministratorPointImpl policyAdministratorPoint;

    public PolicyManagerServiceImpl() {
        policyAdministratorPoint = new PolicyAdministratorPointImpl();
    }

    @Override
    public Feature addFeature(Feature feature) throws FeatureManagementException {
        return policyAdministratorPoint.addFeature(feature);
    }

    @Override
    public Feature updateFeature(Feature feature) throws FeatureManagementException {
        return policyAdministratorPoint.updateFeature(feature);
    }

    @Override
    public Profile addProfile(Profile profile) throws PolicyManagementException {
        return policyAdministratorPoint.addProfile(profile);
    }

    @Override
    public Profile updateProfile(Profile profile) throws PolicyManagementException {
        return policyAdministratorPoint.updateProfile(profile);
    }

    @Override
    public Policy addPolicy(Policy policy) throws PolicyManagementException {
        return policyAdministratorPoint.addPolicy(policy);
    }

    @Override
    public Policy updatePolicy(Policy policy) throws PolicyManagementException {
        return policyAdministratorPoint.updatePolicy(policy);
    }

    @Override
    public boolean deletePolicy(Policy policy) throws PolicyManagementException {
        return policyAdministratorPoint.deletePolicy(policy);
    }

    @Override
    public Policy getEffectivePolicy(DeviceIdentifier deviceIdentifier) throws PolicyManagementException {
        try {
            return PolicyManagementDataHolder.getInstance().getPolicyEvaluationPoint().
                    getEffectivePolicy(deviceIdentifier);
        } catch (PolicyEvaluationException e) {
            String msg = "Error occurred while getting the effective policies from the PEP service for device " +
                    deviceIdentifier.getId() + " - " + deviceIdentifier.getType();
            log.error(msg, e);
            throw new PolicyManagementException(msg, e);
        }
    }

    @Override
    public List<ProfileFeature> getEffectiveFeatures(DeviceIdentifier deviceIdentifier) throws
            FeatureManagementException {
        try {
            return PolicyManagementDataHolder.getInstance().getPolicyEvaluationPoint().
                    getEffectiveFeatures(deviceIdentifier);
        } catch (PolicyEvaluationException e) {
            String msg = "Error occurred while getting the effective features from the PEP service " +
                    deviceIdentifier.getId() + " - " + deviceIdentifier.getType();
            log.error(msg, e);
            throw new FeatureManagementException(msg, e);
        }
    }

    @Override
    public List<Policy> getPolicies(String deviceType) throws PolicyManagementException {
        return policyAdministratorPoint.getPoliciesOfDeviceType(deviceType);
    }

    @Override
    public List<Feature> getFeatures() throws FeatureManagementException {
        return null;
    }

    @Override
    public PolicyAdministratorPoint getPAP() throws PolicyManagementException {
        return new PolicyAdministratorPointImpl();
    }

    @Override
    public PolicyInformationPoint getPIP() throws PolicyManagementException {
        return new PolicyInformationPointImpl();
    }

    @Override
    public PolicyEvaluationPoint getPEP() throws PolicyManagementException {
        return PolicyManagementDataHolder.getInstance().getPolicyEvaluationPoint();
    }
}
