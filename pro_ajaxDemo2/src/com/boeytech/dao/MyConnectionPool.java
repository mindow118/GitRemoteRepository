package com.boeytech.dao;

import com.boeytech.util.PropertiesUtil;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

public class MyConnectionPool {
    private static String driver;
    private static String url;
    private static String user;
    private static String password;
    private static int initSize;
    private static int maxSize;

    private static LinkedList<Connection> pool;

    static {
        //初始化参数
        PropertiesUtil propertiesUtil = new PropertiesUtil("/jdbc.properties");
        driver = propertiesUtil.getProperties("driver");
        url = propertiesUtil.getProperties("url");
        user = propertiesUtil.getProperties("user");
        password = propertiesUtil.getProperties("password");
        initSize = Integer.parseInt(propertiesUtil.getProperties("initSize"));
        maxSize = Integer.parseInt(propertiesUtil.getProperties("maxSize"));

        //加载驱动
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //初始化pool
        pool = new LinkedList<Connection>();

        //创建5个链接对象
        for (int i = 0; i < initSize; i++) {
            Connection connection = initConnection();
            if (null != connection) {
                pool.add(connection);
            }
        }
    }

    //初始化一个链接对象的方法
    private static Connection initConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //向外界提供链接对象的方法
    public static Connection getConnection() {
        Connection connection = null;
        if (pool.size() > 0) {
            connection = pool.removeFirst();//移除集合中的第一个元素
        } else {
            connection = initConnection();
        }
        return connection;
    }

    //向外界连接池归还链接对象的方法
    public static void returnConnection(Connection connection) {
        if (null != connection) {
            try {
                if (!connection.isClosed()) {
                    if (pool.size() < maxSize) {
                        connection.setAutoCommit(true);//调整事务状态
                        pool.addLast(connection);
                    } else {
                        connection.close();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}