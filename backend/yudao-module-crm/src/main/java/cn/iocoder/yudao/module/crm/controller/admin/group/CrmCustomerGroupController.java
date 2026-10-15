package cn.iocoder.yudao.module.crm.controller.admin.group;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.crm.controller.admin.group.vo.CrmCustomerGroupRespVO;
import cn.iocoder.yudao.module.crm.controller.admin.group.vo.CrmCustomerGroupSaveReqVO;
import cn.iocoder.yudao.module.crm.dal.dataobject.group.CrmCustomerGroupDO;
import cn.iocoder.yudao.module.crm.dal.mysql.group.CrmCustomerGroupMapper;
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

@Tag(name = "管理后台 - CRM 客户分组")
@RestController
@RequestMapping("/crm/customer-group")
@Validated
public class CrmCustomerGroupController {

    @Resource
    private CrmCustomerGroupMapper mapper;

    @PostMapping("/create")
    @Operation(summary = "创建CRM 客户分组")
    @PreAuthorize("@ss.hasPermission('crm:customer-group:create')")
    public CommonResult<Long> create(@Valid @RequestBody CrmCustomerGroupSaveReqVO reqVO) {
        CrmCustomerGroupDO obj = BeanUtils.toBean(reqVO, CrmCustomerGroupDO.class).setId(null);
        normalize(obj);
        validate(obj);
        mapper.insert(obj);
        return success(obj.getId());
    }

    @PutMapping("/update")
    @Operation(summary = "更新CRM 客户分组")
    @PreAuthorize("@ss.hasPermission('crm:customer-group:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody CrmCustomerGroupSaveReqVO reqVO) {
        if (reqVO.getId() == null || mapper.selectById(reqVO.getId()) == null) {
            throw exception(CUSTOMER_GROUP_NOT_EXISTS);
        }
        CrmCustomerGroupDO obj = BeanUtils.toBean(reqVO, CrmCustomerGroupDO.class);
        normalize(obj);
        if (Objects.equals(obj.getParentId(), obj.getId())) {
            throw exception(CUSTOMER_GROUP_PARENT_ERROR);
        }
        validate(obj);
        mapper.updateById(obj);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除CRM 客户分组")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('crm:customer-group:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        if (mapper.selectById(id) == null) {
            throw exception(CUSTOMER_GROUP_NOT_EXISTS);
        }
        if (mapper.selectCountByParentId(id) > 0) {
            throw exception(CUSTOMER_GROUP_HAS_CHILDREN);
        }
        mapper.deleteById(id);
        return success(true);
    }

    @GetMapping("/list")
    @Operation(summary = "获得CRM 客户分组列表，前端按 parentId 组装成树")
    @PreAuthorize("@ss.hasPermission('crm:customer-group:query')")
    public CommonResult<List<CrmCustomerGroupRespVO>> list() {
        return success(BeanUtils.toBean(mapper.selectListOrdered(), CrmCustomerGroupRespVO.class));
    }

    private void normalize(CrmCustomerGroupDO obj) {
        if (obj.getParentId() == null) {
            obj.setParentId(CrmCustomerGroupDO.PARENT_ID_ROOT);
        }
        if (obj.getSort() == null) {
            obj.setSort(0);
        }
    }

    private void validate(CrmCustomerGroupDO obj) {
        if (!CrmCustomerGroupDO.PARENT_ID_ROOT.equals(obj.getParentId()) && mapper.selectById(obj.getParentId()) == null) {
            throw exception(CUSTOMER_GROUP_PARENT_NOT_EXISTS);
        }
        CrmCustomerGroupDO same = mapper.selectByParentIdAndName(obj.getParentId(), obj.getName());
        if (same != null && !Objects.equals(same.getId(), obj.getId())) {
            throw exception(CUSTOMER_GROUP_NAME_DUPLICATE);
        }
    }

}
