package cn.iocoder.yudao.module.crm.controller.admin.tag.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(description = "管理后台 - CRM 标签新增/修改 Request VO")
@Data
public class CrmTagSaveReqVO {

    @Schema(description = "编号，修改时必填", example = "1")
    private Long id;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "重点客户")
    @NotBlank(message = "名称不能为空")
    @Size(max = 64, message = "名称不能超过 64 个字符")
    private String name;

    @Schema(description = "父级编号，0 表示顶级", example = "0")
    private Long parentId;

    @Schema(description = "排序", example = "0")
    private Integer sort;

    @Schema(description = "显示颜色", example = "#1677ff")
    private String color;

}
