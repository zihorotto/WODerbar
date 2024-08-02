package com.woderbar.core.repository.boundary.mapper;

import java.util.List;

public interface BaseMapper<TO, FROM> {

    List<TO> map(List<FROM> froms);

    TO map(FROM from);
}
