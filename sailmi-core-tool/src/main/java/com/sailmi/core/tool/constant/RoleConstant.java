/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sailmi.core.tool.constant;

/**
 * 系统默认角色
 *
 * @author Chill
 */
public class RoleConstant {
	//UCS超级管理员
	public static final String ADMIN = "administrator";
	//UCS账户中心管理员
	public static final String SAILPLATADMIN = "sailplat_administrator";
	//租户管理员
	public static final String TENANTADMIN = "tenant_administrator";
	//企业管理员
	public static final String ENTERPRISEADMIN = "enterprise_administrator";
	//具有超级管理员权限
	public static final String HAS_ROLE_ADMIN = "hasRole('" + ADMIN + "')";
	//具有平台管理员权限
	public static final String HAS_ROLE_SAILPLAT_ADMIN = "hasRole('" + SAILPLATADMIN + "')";
	//具有租户管理员权限
	public static final String HAS_ROLE_TENANT_ADMIN = "hasRole('" + TENANTADMIN + "')";
	//具有企业管理员权限
	public static final String HAS_ROLE_ENTERPRISE_ADMIN = "hasRole('" + ENTERPRISEADMIN + "')";

	//普通用户角色
	public static final String USER = "user";

	public static final String HAS_ROLE_USER = "hasRole('" + USER + "')";

	public static final String TEST = "test";

	public static final String HAS_ROLE_TEST = "hasRole('" + TEST + "')";

}
