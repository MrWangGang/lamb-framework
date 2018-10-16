package org.lamb.lambframework.core.supper;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @program: decisionsupportsystem
 * @description: dao 基础类
 * @author: Mr.WangGang
 * @create: 2018-08-24 14:34
 **/
public interface LambDORepository<T> extends PagingAndSortingRepository<T, Object>, JpaSpecificationExecutor<T> {
}
