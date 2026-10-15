<script lang="ts" setup>
import { ref } from 'vue';

import { Page } from '@vben/common-ui';

import { Card, TabPane, Tabs } from 'ant-design-vue';

import TreeManager from './tree-manager.vue';

defineOptions({ name: 'CgchCrmSettings' });

const active = ref('tag');
</script>

<template>
  <Page auto-content-height>
    <Card title="CRM 设置" :bordered="false">
      <p class="mb-4 text-gray-500">统一配置客户标签与客户分组，客户和线索的表单里可以直接选用。</p>
      <Tabs v-model:active-key="active">
        <TabPane key="tag" tab="标签管理">
          <TreeManager kind="tag" label="标签" with-color />
        </TabPane>
        <TabPane key="group" tab="客户分组">
          <TreeManager kind="customer-group" label="分组" />
        </TabPane>
      </Tabs>
    </Card>
  </Page>
</template>
