package com.boeytech.dao;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDao {

    public int baseUpdate(String sql, Object... args) {
        //向表中增加一条数据
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int rows = 0;

        try {
            connection = MyConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            //设置参数
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }

            //执行CURD
            rows = preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != preparedStatement) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            MyConnectionPool.returnConnection(connection);
        }
        return rows;
    }

    public List baseQuery(Class clazz, String sql, Object... args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List list = null;
        try {
            connection = MyConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            //设置参数
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }

            //执行CURD
            resultSet = preparedStatement.executeQuery();
            list = new ArrayList();

            //根据字节码获取所有的属性
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);//设置属性可以访问
            }

            while (resultSet.next()) {
                //通过反射创建对象
                Object obj = clazz.newInstance();//默认在通过反射调用对象的空参构造方法
                for (Field field : fields) {//临时用field设置属性
                    String fieldName = field.getName();
                    Object data = resultSet.getObject(fieldName);
                    field.set(obj, data);
                }
                list.add(obj);

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != resultSet) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (null != preparedStatement) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            MyConnectionPool.returnConnection(connection);
        }
        return list;
    }

    public int baseQueryInt(String sql, Object... args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        int count = 0;
        try {
            connection = MyConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            //设置参数
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }

            //执行CURD
            resultSet = preparedStatement.executeQuery();

            //根据字节码获取所有的属性
            while (resultSet.next()) {
                //通过反射创建对象
                count = resultSet.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != resultSet) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (null != preparedStatement) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            MyConnectionPool.returnConnection(connection);
        }
        return count;
    }
}
