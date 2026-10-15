package cn.iocoder.yudao.module.crm.controller.admin.tag.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - CRM 标签 Response VO")
@Data
public class CrmTagRespVO {

    @Schema(description = "编号", example = "1")
    private Long id;

    @Schema(description = "名称", example = "重点客户")
    private String name;

    @Schema(description = "父级编号", example = "0")
    private Long parentId;

    @Schema(description = "排序", example = "0")
    private Integer sort;

    @Schema(description = "显示颜色", example = "#1677ff")
    private String color;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}
