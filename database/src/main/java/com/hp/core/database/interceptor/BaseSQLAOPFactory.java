/**
 * 
 */
package com.hp.core.database.interceptor;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.core.common.utils.NameDefineUtil;
import com.hp.core.database.annotation.QueryType;
import com.hp.core.database.bean.DAOInterfaceInfoBean;
import com.hp.core.database.bean.DynamicColumnBean;
import com.hp.core.database.bean.DynamicEntityBean;
import com.hp.core.database.bean.GenericParadigmBean;
import com.hp.core.database.enums.QueryTypeEnum;
import com.hp.core.database.exceptions.EntityHaveNotExistsPrimaryKeyException;

/**
 * 基础的sqlmap  provider工厂
 * @author huangping 2018年5月22日
 */
public class BaseSQLAOPFactory {
	
	private static Logger log = LoggerFactory.getLogger(BaseSQLAOPFactory.class);

	/**
	 * 私有构造方法保证单例
	 */
	private BaseSQLAOPFactory() {
	}

	private static BaseSQLAOPFactory instance = new BaseSQLAOPFactory();

	/**
	 * 存放所有dao继承的父类时候的泛型
	 */
	private volatile Map<String, GenericParadigmBean> genericParadigmClassMap = new ConcurrentHashMap<>();

	/**
	 * 存放所有的需要持久化的实体的对象
	 */
	private static volatile Map<String, DynamicEntityBean> entityBeanMap = new ConcurrentHashMap<>();

	
	/**
	 * 获取dal对象实体
	 * @return
	 */
	public static DynamicEntityBean getEntity() {
		DAOInterfaceInfoBean info = DAOMethodInterceptorHandle.getRouteDAOInfo();
		
		//获取泛型对象
		GenericParadigmBean genericParadigmBean = instance.getGenericParadigmByClass(info.getClassName());
		
		//获取表名和所有字段
		DynamicEntityBean entity = instance.getDynamicEntityByClass(genericParadigmBean.getTargetModelClassName());
		return entity;
	}
	
	
	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static BaseSQLAOPFactory getInstance() {
		return instance;
	}

	/**
	 * 获取dao对象的继承的父对象泛型
	 * 
	 * @param targetClass
	 * @return
	 */
	public GenericParadigmBean getGenericParadigmByClass(Class<?> targetClass) {
		if (targetClass == null) {
			return null;
		}

		GenericParadigmBean bean = genericParadigmClassMap.get(targetClass.getName());
		if (bean != null) {
			return bean;
		}
		
		//锁这个class对象，保证
		synchronized (targetClass) {
			//双重校验
			//类似单例里面的双重校验锁
			bean = genericParadigmClassMap.get(targetClass.getName());
			if (bean != null) {
				return bean;
			}
			log.info("begin to call getGenericParadigmByClass with targetClass={}", targetClass);
			// 获取这个接口继承的父类的泛型
			Type[] genType = targetClass.getGenericInterfaces();
			if (ArrayUtils.isEmpty(genType)) {
				return null;
			}
			// 这里约定，只取第一个继承的接口泛型。
			Type[] genericType = ((ParameterizedType) genType[0]).getActualTypeArguments();
			if (ArrayUtils.isEmpty(genericType)) {
				return null;
			}
			bean = new GenericParadigmBean((Class<?>) genericType[0]);
			genericParadigmClassMap.put(targetClass.getName(), bean);
			return bean;
		}
	}

	/**
	 * 根据实体bean的class，获取对应的表，字段
	 * 
	 * @param targetClass
	 * @return
	 */
	public DynamicEntityBean getDynamicEntityByClass(Class<?> targetClass) {
		if (targetClass == null) {
			return null;
		}
		
		DynamicEntityBean entity = entityBeanMap.get(targetClass.getName());
		if (entity != null) {
			return entity;
		}
		
		//缓存没有，则锁这个class对象，解析字段
		//按照targetClass对象  加锁
		synchronized (targetClass) {
			//双重校验
			//类似单例里面的双重校验锁
			entity = entityBeanMap.get(targetClass.getName());
			if (entity != null) {
				return entity;
			}
			log.info("begin to call getDynamicEntityByClass with targetClass={}", targetClass);
			entity = new DynamicEntityBean();
			entity.setClassName(targetClass);
			setTableAndAllColumns(targetClass, entity);
			//如果该实体对象没有主键，则不允许使用该插件
			if (StringUtils.isEmpty(entity.getPrimaryKeyColumnName())) {
				log.error("entity have not exists primary key. with class={}", targetClass);
				throw new EntityHaveNotExistsPrimaryKeyException(targetClass);
			}
			
			entity.dealColumns();
			
			//缓存起来
			entityBeanMap.put(targetClass.getName(), entity);
			return entity;
		}
	}
	
	/**
	 * 设置主键和字段信息
	 * 
	 * @param targetClass
	 * @param entity
	 */
	private void setTableAndAllColumns(Class<?> targetClass, DynamicEntityBean entity) {
		Table table = targetClass.getAnnotation(Table.class);
		
		String tableName = "";
		if (table != null && StringUtils.isNotEmpty(table.name())) {
			//如果有注解，则取注解的表名
			tableName = table.name();
		} else {
			//否则表名使用驼峰转下划线
			tableName = NameDefineUtil.camelCaseToUnderline(targetClass.getSimpleName());
		}
		
		entity.setTableName(tableName);
		
		List<Field> fields = getAllFields(targetClass);
		List<DynamicColumnBean> columnsList = new ArrayList<>();
		DynamicColumnBean column = null;
		// 遍历字段
		for (Field field : fields) {
			if (!Modifier.isPrivate(field.getModifiers())) {
				// 仅仅遍历private修饰的字段进行映射
				continue;
			}
			if (Modifier.isStatic(field.getModifiers())) {
				//static的字段不解析
				continue;
			}
			column = getColumnByField(field);
			if(column.isPrimaryKey()) {
				entity.setPrimaryKeyFieldName(column.getFieldName());
				entity.setPrimaryKeyColumnName(column.getColumnName());
				entity.setPrimaryKeyJavaType(column.getJavaType());
				entity.setPrimaryKeyJdbcType(column.getJdbcType());
			}
			columnsList.add(column);
		}
		entity.setColumns(columnsList);
	}

	/**
	 * 根据对象属性名称，获取数据库字段信息
	 * @param field
	 * @return
	 */
	private DynamicColumnBean getColumnByField(Field field) {
		DynamicColumnBean bean = new DynamicColumnBean();
		setColumn(field, bean);
		bean.setFieldName(field.getName());
		//bean.setGenerationType(generationType);
		bean.setPrimaryKey(checkPrimaryKey(field));
		bean.setQueryType(getQueryType(field));
		
		bean.setJavaType(field.getType());
		//bean.setJdbcType(jdbcType);
		
		bean.setPersistence(field.getAnnotation(Transient.class) == null);
		
		return bean;
	}
	
	/**
	 * 取数据库列名
	 * @param field
	 * @return
	 */
	private void setColumn(Field field, DynamicColumnBean bean) {
		Column column = field.getAnnotation(Column.class);
		//默认字段名为，驼峰转下划线
		String columnName = NameDefineUtil.camelCaseToUnderline(field.getName());
		boolean uniqueKey = false, nullable = true, insertable = true, updatable = true;
		int length = 0;
		//字段名
		if (column != null) {
			//如果有注解，且name不为空，则取注解对应的字段名
			if (StringUtils.isNotEmpty(column.name())) {
				columnName = column.name();
			}
			uniqueKey = column.unique();
			nullable = column.nullable();
			insertable = column.insertable();
			updatable = column.updatable();
			length = column.length();
		}
		bean.setColumnName(columnName);
		bean.setUniqueKey(uniqueKey);
		bean.setNullable(nullable);
		bean.setInsertable(insertable);
		bean.setUpdatable(updatable);
		bean.setLength(length);
	}
	
	/**
	 * 检查是否是主键
	 * @param field
	 * @return
	 */
	private boolean checkPrimaryKey(Field field) {
		Id id = field.getAnnotation(Id.class);
		return id != null;
	}
	
	/**
	 * 获取字段的匹配方式
	 * @param field
	 * @return
	 */
	private QueryTypeEnum getQueryType(Field field) {
		QueryType q = field.getAnnotation(QueryType.class);
		if (q == null) {
			return QueryTypeEnum.EQUALS;
		}
		return q.value();
	}
	
	/**
	 * 获取类中所有的字段（包含父类里面的字段）
	 * 
	 * @param clazz
	 * @return
	 */
	private List<Field> getAllFields(Class<?> clazz) {
		List<Field> result = new LinkedList<>();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			result.add(field);
		}
		Class<?> superClass = clazz.getSuperclass();
		if (superClass.equals(Object.class)) {
			return result;
		}
		result.addAll(getAllFields(superClass));
		return result;
	}
}
