package org.cloud.ssm.common.base;

import java.util.List;
import java.util.Optional;

public interface BaseService<T> {

    Long getCount(T entity);

    Optional<T> getById(Long id);

    List<T> getAll();

    List<T> getAll(int pageNum, int pageSize);

    long save(T entity);

    long deleteById(Long id);
}
