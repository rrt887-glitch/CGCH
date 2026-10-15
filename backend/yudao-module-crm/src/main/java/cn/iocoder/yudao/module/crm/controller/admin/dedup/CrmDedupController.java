package cn.iocoder.yudao.module.crm.controller.admin.dedup;

import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.crm.controller.admin.dedup.vo.CrmDedupRespVO;
import cn.iocoder.yudao.module.crm.dal.dataobject.clue.CrmClueDO;
import cn.iocoder.yudao.module.crm.dal.dataobject.customer.CrmCustomerDO;
import cn.iocoder.yudao.module.crm.dal.mysql.clue.CrmClueMapper;
import cn.iocoder.yudao.module.crm.dal.mysql.customer.CrmCustomerMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * 查重：按名称、公司、邮箱、电话、官网、WhatsApp 在客户与线索中查找可能重复的记录。
 * 不受负责人数据权限限制，便于发现其他同事或公海里已有的客户。
 */
@Tag(name = "管理后台 - CRM 查重")
@RestController
@RequestMapping("/crm/dedup")
@Validated
public class CrmDedupController {

    private static final int LIMIT = 50;

    @Resource
    private CrmCustomerMapper customerMapper;
    @Resource
    private CrmClueMapper clueMapper;

    @GetMapping("/search")
    @Operation(summary = "查重搜索")
    @Parameter(name = "keyword", description = "关键字，至少 2 个字符", required = true, example = "acme")
    @Parameter(name = "scope", description = "范围：all 全部，customer 客户，clue 线索", example = "all")
    @PreAuthorize("@ss.hasPermission('crm:dedup:query')")
    public CommonResult<List<CrmDedupRespVO>> search(@RequestParam("keyword") String keyword,
                                                     @RequestParam(value = "scope", defaultValue = "all") String scope) {
        String kw = StrUtil.trim(keyword);
        List<CrmDedupRespVO> result = new ArrayList<>();
        if (StrUtil.length(kw) < 2) {
            return success(result);
        }
        if (!"clue".equals(scope)) {
            List<CrmCustomerDO> customers = customerMapper.selectList(new LambdaQueryWrapperX<CrmCustomerDO>()
                    .and(w -> w.like(CrmCustomerDO::getName, kw).or().like(CrmCustomerDO::getCompanyName, kw)
                            .or().like(CrmCustomerDO::getEmail, kw).or().like(CrmCustomerDO::getMobile, kw)
                            .or().like(CrmCustomerDO::getTelephone, kw).or().like(CrmCustomerDO::getWebsite, kw)
                            .or().like(CrmCustomerDO::getWhatsapp, kw))
                    .orderByDesc(CrmCustomerDO::getId).last("LIMIT " + LIMIT));
            for (CrmCustomerDO c : customers) {
                result.add(toResp("customer", c.getId(), c.getName(), c.getCompanyName(), c.getEmail(), c.getMobile(),
                        c.getCountryCode(), c.getOwnerUserId(), kw, fields(c.getName(), c.getCompanyName(), c.getEmail(),
                                c.getMobile(), c.getTelephone(), c.getWebsite(), c.getWhatsapp())));
            }
        }
        if (!"customer".equals(scope)) {
            List<CrmClueDO> clues = clueMapper.selectList(new LambdaQueryWrapperX<CrmClueDO>()
                    .and(w -> w.like(CrmClueDO::getName, kw).or().like(CrmClueDO::getCompanyName, kw)
                            .or().like(CrmClueDO::getEmail, kw).or().like(CrmClueDO::getMobile, kw)
                            .or().like(CrmClueDO::getTelephone, kw).or().like(CrmClueDO::getWebsite, kw)
                            .or().like(CrmClueDO::getWhatsapp, kw))
                    .orderByDesc(CrmClueDO::getId).last("LIMIT " + LIMIT));
            for (CrmClueDO c : clues) {
                result.add(toResp("clue", c.getId(), c.getName(), c.getCompanyName(), c.getEmail(), c.getMobile(),
                        c.getCountryCode(), c.getOwnerUserId(), kw, fields(c.getName(), c.getCompanyName(), c.getEmail(),
                                c.getMobile(), c.getTelephone(), c.getWebsite(), c.getWhatsapp())));
            }
        }
        return success(result);
    }

    private static Map<String, String> fields(String name, String companyName, String email, String mobile,
                                              String telephone, String website, String whatsapp) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("name", name);
        map.put("companyName", companyName);
        map.put("email", email);
        map.put("mobile", mobile);
        map.put("telephone", telephone);
        map.put("website", website);
        map.put("whatsapp", whatsapp);
        return map;
    }

    private static CrmDedupRespVO toResp(String type, Long id, String name, String companyName, String email,
                                         String mobile, String countryCode, Long ownerUserId, String kw,
                                         Map<String, String> values) {
        List<String> matched = new ArrayList<>();
        values.forEach((field, value) -> {
            if (StrUtil.containsIgnoreCase(value, kw)) {
                matched.add(field);
            }
        });
        CrmDedupRespVO vo = new CrmDedupRespVO();
        vo.setType(type);
        vo.setId(id);
        vo.setName(name);
        vo.setCompanyName(companyName);
        vo.setEmail(email);
        vo.setMobile(mobile);
        vo.setCountryCode(countryCode);
        vo.setOwnerUserId(ownerUserId);
        vo.setMatchedFields(matched);
        return vo;
    }

}
