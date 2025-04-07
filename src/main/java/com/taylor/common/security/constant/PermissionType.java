package com.taylor.common.security.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 权限类型枚举
 *
 * @author loveCamille
 * @date 2025-04-05 16:16:38
 */
@Getter
@AllArgsConstructor
public enum PermissionType {

    FUNC_PERMISSION("function_permission", "功能权限"),

    DATA_PERMISSION("data_permission", "数据权限"),

    ;

    private final String type;

    private final String description;

}
