package com.boeytech.dao;

import com.boeytech.pojo.Area;

import java.util.List;

public interface AreaDao {
    public List<Area> findByParentID(int ParentID);
}
