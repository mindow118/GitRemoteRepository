package com.boeytech.dao.impl;

import com.boeytech.dao.AreaDao;
import com.boeytech.dao.BaseDao;
import com.boeytech.pojo.Area;

import java.util.List;

public class AreaDaoImpl extends BaseDao implements AreaDao {
    @Override
    public List<Area> findByParentID(int ParentID) {
        String sql = "select * from area where parentid = ?";
        List<Area> areas = baseQuery(Area.class, sql, ParentID);
        return areas;
    }
}
