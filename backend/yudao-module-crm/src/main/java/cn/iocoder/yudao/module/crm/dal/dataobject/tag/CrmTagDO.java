package cn.iocoder.yudao.module.crm.dal.dataobject.tag;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * CRM 标签 DO，树形结构，parentId = 0 表示顶级
 */
@TableName("crm_tag")
@KeySequence("crm_tag_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrmTagDO extends BaseDO {

    public static final Long PARENT_ID_ROOT = 0L;

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 父级编号
     */
    private Long parentId;
    /**
     * 排序，越小越靠前
     */
    private Integer sort;

    /**
     * 显示颜色，例如 #1677ff
     */
    private String color;

}
