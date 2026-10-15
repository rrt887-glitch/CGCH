package cn.iocoder.yudao.module.crm.dal.mysql.tag;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.crm.dal.dataobject.tag.CrmTagDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CrmTagMapper extends BaseMapperX<CrmTagDO> {

    default List<CrmTagDO> selectListOrdered() {
        return selectList(new LambdaQueryWrapperX<CrmTagDO>()
                .orderByAsc(CrmTagDO::getSort).orderByAsc(CrmTagDO::getId));
    }

    default Long selectCountByParentId(Long parentId) {
        return selectCount(CrmTagDO::getParentId, parentId);
    }

    default CrmTagDO selectByParentIdAndName(Long parentId, String name) {
        return selectOne(CrmTagDO::getParentId, parentId, CrmTagDO::getName, name);
    }

}
