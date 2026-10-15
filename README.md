# CGCH · 跨境 B2B AI 数字员工平台

多渠道询盘由 AI 自动接待，沉淀为 CRM 线索、客户、商机与订单。功能规格见项目文档《AI数字员工平台 功能规格与重建方案》。

## 目录结构

| 目录 | 说明 |
| --- | --- |
| `backend/` | Java 17 + Spring Boot 3 后端，基于开源 ruoyi-vue-pro（MIT），已保留 system、infra、crm、pay、ai 模块 |
| `frontend/` | Vue 3 + Vite + Ant Design Vue 前端，基于开源 yudao-ui-admin-vben（MIT），只保留 `apps/web-antd` |
| `docker-compose.yml` | 本地开发用 MySQL 8 与 Redis 7 |

## 本地启动

需要：JDK 17、Maven 3.9、Node 22、pnpm 11、Docker。

1. 启动数据库与缓存：

   ```bash
   docker compose up -d
   ```

   首次启动会自动导入系统基础表。CRM、支付、AI 模块的建表脚本不在上游代码仓库里，需要按上游文档单独导入：
   - CRM：<https://doc.iocoder.cn/crm/build/>
   - 支付：<https://doc.iocoder.cn/pay/build/>
   - AI：<https://doc.iocoder.cn/ai/build/>

2. 启动后端（默认端口 48080）：

   ```bash
   cd backend
   mvn clean install -DskipTests
   cd yudao-server && mvn spring-boot:run -Dspring-boot.run.profiles=local
   ```

   数据库连接在 `backend/yudao-server/src/main/resources/application-local.yaml`，默认 `root / 123456`。

3. 启动前端（默认端口 5666）：

   ```bash
   cd frontend
   pnpm install
   pnpm dev:antd
   ```

   浏览器打开 <http://localhost:5666>，默认账号 `admin / admin123`。

## 开发计划

1. 基础底座：多租户、登录、组织与权限、数据字典、导航（本阶段）
2. AI-CRM：线索、客户、公海、查重、联系人、商机看板、订单
3. 邮件与消息工作台：IMAP/SMTP、Gmail、Outlook、WhatsApp、Meta
4. AI 数字员工：知识库、人设、自动接待、违禁词、人工转接、Skill、智点计费
5. CRM Claw、深度背调、客户唤醒、看板与报告、订阅支付、其他社媒渠道

## 第三方许可

见 [THIRD_PARTY_NOTICES.md](THIRD_PARTY_NOTICES.md)。
