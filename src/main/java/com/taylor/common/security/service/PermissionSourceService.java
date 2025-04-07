package com.taylor.common.security.service;

import com.taylor.common.security.constant.PermissionType;
import com.taylor.common.security.model.PermissionRule;

import java.util.List;

/**
 * 资源权限获取接口
 *
 * @author loveCamille
 * @date 2025-04-03 14:06:36
 */
public interface PermissionSourceService {

    /**
     * 获取系统所有资源的权限
     * @author loveCamille
     * @param permissionType 权限类型
     * @return {@link List<PermissionRule>} 所有资源对应的权限
    */
    List<PermissionRule> getAllPermissions(PermissionType permissionType);

}
