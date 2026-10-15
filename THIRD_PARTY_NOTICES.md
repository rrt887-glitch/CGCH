# 第三方开源组件

本项目基于以下 MIT 协议开源项目二次开发。各项目的原始许可文件保留在对应目录中。

| 组件 | 来源 | 许可 | 许可文件 |
| --- | --- | --- | --- |
| ruoyi-vue-pro（master-jdk17 分支） | https://github.com/YunaiV/ruoyi-vue-pro | MIT | `backend/LICENSE` |
| yudao-ui-admin-vben | https://github.com/yudaocode/yudao-ui-admin-vben | MIT | `frontend/LICENSE` |

裁剪说明：

- 后端仅保留 `yudao-framework`、`yudao-dependencies`、`yudao-server` 以及 `system`、`infra`、`bpm`、`crm`、`pay`、`ai` 六个业务模块（`bpm` 是 CRM 合同审批的依赖），其余模块已移除。
- 前端仅保留 `apps/web-antd`（Ant Design Vue 版本），其余 UI 实现已移除。前端的 FMS 财务页面因上游缺文件无法打包，已一并移除。
