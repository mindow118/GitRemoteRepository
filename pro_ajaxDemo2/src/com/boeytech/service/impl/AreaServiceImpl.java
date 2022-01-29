package com.boeytech.service.impl;

import com.boeytech.dao.AreaDao;
import com.boeytech.dao.impl.AreaDaoImpl;
import com.boeytech.pojo.Area;
import com.boeytech.service.AreaService;

import java.util.List;

public class AreaServiceImpl implements AreaService {
    private AreaDao areaDao = new AreaDaoImpl();

    @Override
    public List<Area> findByParentID(Integer parentID) {
        List<Area> areas = areaDao.findByParentID(parentID);
        return areas;
    }
}
