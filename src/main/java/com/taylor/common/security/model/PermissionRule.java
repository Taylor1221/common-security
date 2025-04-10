package com.taylor.common.security.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 存储 URL-权限映射
 *
 * @author loveCamille
 * @date 2025-04-03 14:06:36
 */
@Getter
@Setter
@Accessors(chain = true)
@RequiredArgsConstructor
@AllArgsConstructor
public class PermissionRule {

    /**
     * 访问路径（支持 /admin/**）
     */
    private String urlPattern;

    /**
     * 需要的角色列表
     */
    private List<String> roles;

}
