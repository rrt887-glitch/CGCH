package cn.iocoder.yudao.module.crm.dal.mysql.group;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.crm.dal.dataobject.group.CrmCustomerGroupDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CrmCustomerGroupMapper extends BaseMapperX<CrmCustomerGroupDO> {

    default List<CrmCustomerGroupDO> selectListOrdered() {
        return selectList(new LambdaQueryWrapperX<CrmCustomerGroupDO>()
                .orderByAsc(CrmCustomerGroupDO::getSort).orderByAsc(CrmCustomerGroupDO::getId));
    }

    default Long selectCountByParentId(Long parentId) {
        return selectCount(CrmCustomerGroupDO::getParentId, parentId);
    }

    default CrmCustomerGroupDO selectByParentIdAndName(Long parentId, String name) {
        return selectOne(CrmCustomerGroupDO::getParentId, parentId, CrmCustomerGroupDO::getName, name);
    }

}
