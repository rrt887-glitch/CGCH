/** 常用外贸国家/地区（ISO 3166-1 二字码），按区域大致排序 */
export const COUNTRIES: { code: string; name: string }[] = [
  { code: 'US', name: '美国' }, { code: 'CA', name: '加拿大' }, { code: 'MX', name: '墨西哥' },
  { code: 'BR', name: '巴西' }, { code: 'AR', name: '阿根廷' }, { code: 'CL', name: '智利' },
  { code: 'CO', name: '哥伦比亚' }, { code: 'PE', name: '秘鲁' },
  { code: 'GB', name: '英国' }, { code: 'DE', name: '德国' }, { code: 'FR', name: '法国' },
  { code: 'IT', name: '意大利' }, { code: 'ES', name: '西班牙' }, { code: 'PT', name: '葡萄牙' },
  { code: 'NL', name: '荷兰' }, { code: 'BE', name: '比利时' }, { code: 'CH', name: '瑞士' },
  { code: 'AT', name: '奥地利' }, { code: 'SE', name: '瑞典' }, { code: 'NO', name: '挪威' },
  { code: 'DK', name: '丹麦' }, { code: 'FI', name: '芬兰' }, { code: 'PL', name: '波兰' },
  { code: 'CZ', name: '捷克' }, { code: 'GR', name: '希腊' }, { code: 'IE', name: '爱尔兰' },
  { code: 'RU', name: '俄罗斯' }, { code: 'UA', name: '乌克兰' }, { code: 'TR', name: '土耳其' },
  { code: 'AE', name: '阿联酋' }, { code: 'SA', name: '沙特阿拉伯' }, { code: 'QA', name: '卡塔尔' },
  { code: 'KW', name: '科威特' }, { code: 'IL', name: '以色列' }, { code: 'IR', name: '伊朗' },
  { code: 'EG', name: '埃及' }, { code: 'NG', name: '尼日利亚' }, { code: 'ZA', name: '南非' },
  { code: 'KE', name: '肯尼亚' }, { code: 'MA', name: '摩洛哥' }, { code: 'GH', name: '加纳' },
  { code: 'IN', name: '印度' }, { code: 'PK', name: '巴基斯坦' }, { code: 'BD', name: '孟加拉国' },
  { code: 'LK', name: '斯里兰卡' }, { code: 'JP', name: '日本' }, { code: 'KR', name: '韩国' },
  { code: 'HK', name: '中国香港' }, { code: 'TW', name: '中国台湾' }, { code: 'CN', name: '中国大陆' },
  { code: 'SG', name: '新加坡' }, { code: 'MY', name: '马来西亚' }, { code: 'TH', name: '泰国' },
  { code: 'VN', name: '越南' }, { code: 'ID', name: '印度尼西亚' }, { code: 'PH', name: '菲律宾' },
  { code: 'AU', name: '澳大利亚' }, { code: 'NZ', name: '新西兰' },
];

export const COUNTRY_OPTIONS = COUNTRIES.map((c) => ({
  label: `${c.name} (${c.code})`,
  value: c.code,
}));

export function countryName(code?: string) {
  if (!code) return '';
  return COUNTRIES.find((c) => c.code === code)?.name ?? code;
}
