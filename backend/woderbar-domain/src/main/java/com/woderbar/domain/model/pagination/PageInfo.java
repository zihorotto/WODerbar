package com.woderbar.domain.model.pagination;

import lombok.Data;

import java.util.List;

@Data
public class PageInfo<T> {

    private final long total;
    private final int page;
    private final int pageSize;
    private final boolean hasNext;
    private final boolean hasPrevious;
    private final List<T> results;

}