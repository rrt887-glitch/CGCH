import type { VbenFormSchema } from '#/adapter/form';
import type { VxeTableGridOptions } from '#/adapter/vxe-table';

import { markRaw } from 'vue';

import { z } from '@vben/common-ui';
import { DICT_TYPE } from '@vben/constants';
import { getDictOptions } from '@vben/hooks';
import { useUserStore } from '@vben/stores';

import { getSimpleUserList } from '#/api/system/user';
import { AreaCascader } from '#/components/area';
import { getRangePickerDefaultProps } from '#/utils';
import { getCustomerGroupList, getTagList } from '#/api/crm/cgch';
import { COUNTRY_OPTIONS, countryName } from '#/views/cgch/crm/shared/countries';

/** 新增/修改的表单 */
export function useFormSchema(): VbenFormSchema[] {
  const userStore = useUserStore();
  return [
    {
      fieldName: 'id',
      component: 'Input',
      dependencies: {
        triggerFields: [''],
        show: () => false,
      },
    },
    {
      fieldName: 'name',
      label: '客户名称',
      component: 'Input',
      rules: 'required',
      componentProps: {
        placeholder: '请输入客户名称',
        allowClear: true,
      },
    },
    // ===== CGCH 外贸字段 =====
    {
      fieldName: 'companyName',
      label: '公司名称',
      component: 'Input',
      componentProps: { placeholder: '请输入公司名称', allowClear: true },
    },
    {
      fieldName: 'countryCode',
      label: '国家/地区',
      component: 'Select',
      componentProps: {
        options: COUNTRY_OPTIONS,
        placeholder: '请选择国家/地区',
        showSearch: true,
        optionFilterProp: 'label',
        allowClear: true,
      },
    },
    {
      fieldName: 'website',
      label: '公司官网',
      component: 'Input',
      componentProps: { placeholder: 'https://', allowClear: true },
    },
    {
      fieldName: 'whatsapp',
      label: 'WhatsApp',
      component: 'Input',
      componentProps: { placeholder: '含国际区号，如 +14155550100', allowClear: true },
    },
    {
      fieldName: 'groupId',
      label: '客户分组',
      component: 'ApiSelect',
      componentProps: {
        api: getCustomerGroupList,
        labelField: 'name',
        valueField: 'id',
        placeholder: '请选择客户分组',
        allowClear: true,
      },
    },
    {
      fieldName: 'tagIds',
      label: '标签',
      component: 'ApiSelect',
      componentProps: {
        api: getTagList,
        labelField: 'name',
        valueField: 'id',
        mode: 'multiple',
        placeholder: '请选择标签',
        allowClear: true,
      },
    },
    {
      fieldName: 'aiHosted',
      label: 'AI 托管',
      component: 'Switch',
      defaultValue: false,
      help: '开启后由 AI 数字员工接待该客户的渠道消息',
    },
    {
      fieldName: 'source',
      label: '客户来源',
      component: 'Select',
      componentProps: {
        options: getDictOptions(DICT_TYPE.CRM_CUSTOMER_SOURCE, 'number'),
        placeholder: '请选择客户来源',
        allowClear: true,
      },
    },
    {
      fieldName: 'mobile',
      label: '手机',
      component: 'Input',
      componentProps: {
        placeholder: '请输入手机',
        allowClear: true,
      },
    },
    {
      fieldName: 'ownerUserId',
      label: '负责人',
      component: 'ApiSelect',
      dependencies: {
        triggerFields: ['id'],
        disabled: (values) => values.id,
      },
      componentProps: {
        api: getSimpleUserList,
        labelField: 'nickname',
        valueField: 'id',
        placeholder: '请选择负责人',
        allowClear: true,
      },
      defaultValue: userStore.userInfo?.id,
      rules: 'required',
    },
    {
      fieldName: 'telephone',
      label: '电话',
      component: 'Input',
      componentProps: {
        placeholder: '请输入电话',
        allowClear: true,
      },
    },
    {
      fieldName: 'email',
      label: '邮箱',
      component: 'Input',
      componentProps: {
        placeholder: '请输入邮箱',
        allowClear: true,
      },
    },
    {
      fieldName: 'wechat',
      label: '微信',
      component: 'Input',
      componentProps: {
        placeholder: '请输入微信',
        allowClear: true,
      },
    },
    {
      fieldName: 'qq',
      label: 'QQ',
      component: 'Input',
      componentProps: {
        placeholder: '请输入QQ',
        allowClear: true,
      },
    },
    {
      fieldName: 'industryId',
      label: '客户行业',
      component: 'Select',
      componentProps: {
        options: getDictOptions(DICT_TYPE.CRM_CUSTOMER_INDUSTRY, 'number'),
        placeholder: '请选择客户行业',
        allowClear: true,
      },
    },
    {
      fieldName: 'level',
      label: '客户级别',
      component: 'Select',
      componentProps: {
        options: getDictOptions(DICT_TYPE.CRM_CUSTOMER_LEVEL, 'number'),
        placeholder: '请选择客户级别',
        allowClear: true,
      },
    },
    {
      fieldName: 'areaId',
      label: '地址',
      component: markRaw(AreaCascader),
      componentProps: {
        allowClear: true,
        class: '!w-full',
        placeholder: '请选择地址',
        showSearch: true,
      },
    },
    {
      fieldName: 'detailAddress',
      label: '详细地址',
      component: 'Input',
      componentProps: {
        placeholder: '请输入详细地址',
        allowClear: true,
      },
    },
    {
      fieldName: 'contactNextTime',
      label: '下次联系时间',
      component: 'DatePicker',
      componentProps: {
        showTime: true,
        format: 'YYYY-MM-DD HH:mm:ss',
        valueFormat: 'x',
        placeholder: '请选择下次联系时间',
        allowClear: true,
      },
    },
    {
      fieldName: 'remark',
      label: '备注',
      component: 'Textarea',
      componentProps: {
        placeholder: '请输入备注',
        allowClear: true,
      },
    },
  ];
}

/** 列表的搜索表单 */
export function useGridFormSchema(): VbenFormSchema[] {
  return [
    {
      fieldName: 'name',
      label: '客户名称',
      component: 'Input',
      componentProps: {
        placeholder: '请输入客户名称',
        allowClear: true,
      },
    },
    // ===== CGCH 外贸筛选 =====
    {
      fieldName: 'countryCode',
      label: '国家/地区',
      component: 'Select',
      componentProps: {
        options: COUNTRY_OPTIONS,
        placeholder: '请选择国家/地区',
        showSearch: true,
        optionFilterProp: 'label',
        allowClear: true,
      },
    },
    {
      fieldName: 'aiHosted',
      label: 'AI 托管',
      component: 'Select',
      componentProps: {
        options: [
          { label: '托管中', value: true },
          { label: '未托管', value: false },
        ],
        placeholder: '全部',
        allowClear: true,
      },
    },
    {
      fieldName: 'mobile',
      label: '手机号',
      component: 'Input',
      componentProps: {
        placeholder: '请输入手机号',
        allowClear: true,
      },
    },
    {
      fieldName: 'telephone',
      label: '电话',
      component: 'Input',
      componentProps: {
        placeholder: '请输入电话',
        allowClear: true,
      },
    },
    {
      fieldName: 'createTime',
      label: '创建时间',
      component: 'RangePicker',
      componentProps: {
        ...getRangePickerDefaultProps(),
        placeholder: ['开始日期', '结束日期'],
        allowClear: true,
      },
    },
  ];
}

/** 导入客户的表单 */
export function useImportFormSchema(): VbenFormSchema[] {
  return [
    {
      fieldName: 'ownerUserId',
      label: '负责人',
      component: 'ApiSelect',
      componentProps: {
        api: getSimpleUserList,
        labelField: 'nickname',
        valueField: 'id',
        placeholder: '请选择负责人',
        allowClear: true,
        class: 'w-full',
      },
      dependencies: {
        triggerFields: ['id'],
        disabled: (values) => values.id,
      },
      rules: 'required',
    },
    {
      fieldName: 'file',
      label: '客户数据',
      component: 'Upload',
      rules: 'required',
      help: '仅允许导入 xls、xlsx 格式文件',
    },
    {
      fieldName: 'updateSupport',
      label: '是否覆盖',
      component: 'Switch',
      componentProps: {
        checkedChildren: '是',
        unCheckedChildren: '否',
      },
      rules: z.boolean().default(false),
      help: '是否更新已经存在的客户数据',
    },
  ];
}

/** 列表的字段 */
export function useGridColumns(): VxeTableGridOptions['columns'] {
  return [
    {
      field: 'name',
      title: '客户名称',
      fixed: 'left',
      minWidth: 160,
      slots: { default: 'name' },
    },
    {
      field: 'source',
      title: '客户来源',
      minWidth: 100,
      cellRender: {
        name: 'CellDict',
        props: { type: DICT_TYPE.CRM_CUSTOMER_SOURCE },
      },
    },
    {
      field: 'mobile',
      title: '手机',
      minWidth: 120,
    },
    // ===== CGCH 外贸字段 =====
    {
      field: 'countryCode',
      title: '国家/地区',
      minWidth: 110,
      formatter: ({ cellValue }) => countryName(cellValue),
    },
    {
      field: 'companyName',
      title: '公司名称',
      minWidth: 160,
    },
    {
      field: 'whatsapp',
      title: 'WhatsApp',
      minWidth: 140,
    },
    {
      field: 'aiHosted',
      title: 'AI 托管',
      minWidth: 90,
      formatter: ({ cellValue }) => (cellValue ? '托管中' : '未托管'),
    },
    {
      field: 'telephone',
      title: '电话',
      minWidth: 130,
    },
    {
      field: 'email',
      title: '邮箱',
      minWidth: 180,
    },
    {
      field: 'level',
      title: '客户级别',
      minWidth: 135,
      cellRender: {
        name: 'CellDict',
        props: { type: DICT_TYPE.CRM_CUSTOMER_LEVEL },
      },
    },
    {
      field: 'industryId',
      title: '客户行业',
      minWidth: 100,
      cellRender: {
        name: 'CellDict',
        props: { type: DICT_TYPE.CRM_CUSTOMER_INDUSTRY },
      },
    },
    {
      field: 'contactNextTime',
      title: '下次联系时间',
      formatter: 'formatDateTime',
      minWidth: 180,
    },
    {
      field: 'remark',
      title: '备注',
      minWidth: 200,
    },
    {
      field: 'lockStatus',
      title: '锁定状态',
      minWidth: 120,
      cellRender: {
        name: 'CellDict',
        props: { type: DICT_TYPE.INFRA_BOOLEAN_STRING },
      },
    },
    {
      field: 'dealStatus',
      title: '成交状态',
      minWidth: 120,
      cellRender: {
        name: 'CellDict',
        props: { type: DICT_TYPE.INFRA_BOOLEAN_STRING },
      },
    },
    {
      field: 'contactLastTime',
      title: '最后跟进时间',
      formatter: 'formatDateTime',
      minWidth: 180,
    },
    {
      field: 'contactLastContent',
      title: '最后跟进记录',
      minWidth: 200,
    },
    {
      field: 'detailAddress',
      title: '地址',
      minWidth: 180,
    },
    {
      field: 'poolDay',
      title: '距离进入公海天数',
      minWidth: 140,
      formatter: ({ cellValue }) =>
        cellValue === null ? '-' : `${cellValue} 天`,
    },
    {
      field: 'ownerUserName',
      title: '负责人',
      minWidth: 100,
    },
    {
      field: 'ownerUserDeptName',
      title: '所属部门',
      minWidth: 100,
    },
    {
      field: 'updateTime',
      title: '更新时间',
      formatter: 'formatDateTime',
      minWidth: 180,
    },
    {
      field: 'createTime',
      title: '创建时间',
      formatter: 'formatDateTime',
      minWidth: 180,
    },
    {
      field: 'creatorName',
      title: '创建人',
      minWidth: 100,
    },
    {
      title: '操作',
      width: 130,
      fixed: 'right',
      slots: { default: 'actions' },
    },
  ];
}
