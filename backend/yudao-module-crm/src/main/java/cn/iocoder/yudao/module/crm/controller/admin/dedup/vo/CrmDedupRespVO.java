package cn.iocoder.yudao.module.crm.controller.admin.dedup.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "管理后台 - CRM 查重结果 Response VO")
@Data
public class CrmDedupRespVO {

    @Schema(description = "记录类型：customer 客户，clue 线索", example = "customer")
    private String type;

    @Schema(description = "记录编号", example = "1")
    private Long id;

    @Schema(description = "名称", example = "Acme")
    private String name;

    @Schema(description = "公司名称", example = "Acme Inc.")
    private String companyName;

    @Schema(description = "邮箱", example = "buyer@acme.example")
    private String email;

    @Schema(description = "手机", example = "+14155550100")
    private String mobile;

    @Schema(description = "国家/地区二字码", example = "US")
    private String countryCode;

    @Schema(description = "负责人编号，为空表示在公海", example = "1")
    private Long ownerUserId;

    @Schema(description = "命中的字段", example = "[\"email\"]")
    private List<String> matchedFields;

}
