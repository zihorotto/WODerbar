package com.woderbar.core.repository.boundary.mapper;

import com.woderbar.domain.model.pagination.PageInfo;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;


public interface BasePageMapper<TO, FROM> extends BaseMapper<TO, FROM> {

    @Mapping(target = "total", source = "totalElements")
    @Mapping(target = "page", source = "number")
    @Mapping(target = "pageSize", source = "size")
    @Mapping(target = "hasNext", expression = "java(pageInfo.hasNext())")
    @Mapping(target = "hasPrevious", expression = "java(pageInfo.hasPrevious())")
    @Mapping(target = "results", source = "content")
    PageInfo<TO> map(Page<FROM> pageInfo);

}