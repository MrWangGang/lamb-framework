package org.lamb.framework.core.supper;

import org.springframework.data.repository.PagingAndSortingRepository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @program: decisionsupportsystem
 * @description: dao 基础类
 * @author: Mr.WangGang
 * @create: 2018-08-24 14:34
 **/
public interface LambRepository<T>  extends Mapper<T> {


}
