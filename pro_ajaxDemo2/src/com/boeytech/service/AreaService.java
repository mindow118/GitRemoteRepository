package com.boeytech.service;

import com.boeytech.pojo.Area;

import java.util.List;

public interface AreaService {
    List<Area> findByParentID(Integer parentID);
}
