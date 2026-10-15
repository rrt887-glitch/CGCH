package cn.iocoder.yudao.module.crm.controller.admin.tag;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.crm.controller.admin.tag.vo.CrmTagRespVO;
import cn.iocoder.yudao.module.crm.controller.admin.tag.vo.CrmTagSaveReqVO;
import cn.iocoder.yudao.module.crm.dal.dataobject.tag.CrmTagDO;
import cn.iocoder.yudao.module.crm.dal.mysql.tag.CrmTagMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.module.crm.enums.ErrorCodeConstants.*;

@Tag(name = "管理后台 - CRM 标签")
@RestController
@RequestMapping("/crm/tag")
@Validated
public class CrmTagController {

    @Resource
    private CrmTagMapper mapper;

    @PostMapping("/create")
    @Operation(summary = "创建CRM 标签")
    @PreAuthorize("@ss.hasPermission('crm:tag:create')")
    public CommonResult<Long> create(@Valid @RequestBody CrmTagSaveReqVO reqVO) {
        CrmTagDO obj = BeanUtils.toBean(reqVO, CrmTagDO.class).setId(null);
        normalize(obj);
        validate(obj);
        mapper.insert(obj);
        return success(obj.getId());
    }

    @PutMapping("/update")
    @Operation(summary = "更新CRM 标签")
    @PreAuthorize("@ss.hasPermission('crm:tag:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody CrmTagSaveReqVO reqVO) {
        if (reqVO.getId() == null || mapper.selectById(reqVO.getId()) == null) {
            throw exception(TAG_NOT_EXISTS);
        }
        CrmTagDO obj = BeanUtils.toBean(reqVO, CrmTagDO.class);
        normalize(obj);
        if (Objects.equals(obj.getParentId(), obj.getId())) {
            throw exception(TAG_PARENT_ERROR);
        }
        validate(obj);
        mapper.updateById(obj);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除CRM 标签")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('crm:tag:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        if (mapper.selectById(id) == null) {
            throw exception(TAG_NOT_EXISTS);
        }
        if (mapper.selectCountByParentId(id) > 0) {
            throw exception(TAG_HAS_CHILDREN);
        }
        mapper.deleteById(id);
        return success(true);
    }

    @GetMapping("/list")
    @Operation(summary = "获得CRM 标签列表，前端按 parentId 组装成树")
    @PreAuthorize("@ss.hasPermission('crm:tag:query')")
    public CommonResult<List<CrmTagRespVO>> list() {
        return success(BeanUtils.toBean(mapper.selectListOrdered(), CrmTagRespVO.class));
    }

    private void normalize(CrmTagDO obj) {
        if (obj.getParentId() == null) {
            obj.setParentId(CrmTagDO.PARENT_ID_ROOT);
        }
        if (obj.getSort() == null) {
            obj.setSort(0);
        }
    }

    private void validate(CrmTagDO obj) {
        if (!CrmTagDO.PARENT_ID_ROOT.equals(obj.getParentId()) && mapper.selectById(obj.getParentId()) == null) {
            throw exception(TAG_PARENT_NOT_EXISTS);
        }
        CrmTagDO same = mapper.selectByParentIdAndName(obj.getParentId(), obj.getName());
        if (same != null && !Objects.equals(same.getId(), obj.getId())) {
            throw exception(TAG_NAME_DUPLICATE);
        }
    }

}
