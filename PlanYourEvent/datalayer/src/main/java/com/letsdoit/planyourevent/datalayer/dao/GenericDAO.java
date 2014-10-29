package com.letsdoit.planyourevent.datalayer.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.letsdoit.planyourevent.commons.exceptions.ServerException;
import com.letsdoit.planyourevent.datalayer.entities.BaseEntity;

public interface GenericDAO<T extends BaseEntity, PK extends Serializable> {

	public List<T> getAll();

	public List<T> getAll(String paramString, boolean paramBoolean);

	public <E extends BaseEntity> List<E> getAll(Class<E> paramClass);

	public T getByIdentifier(PK paramPK) throws ServerException;

	public T getByIdentifier(PK paramPK, boolean paramBoolean) throws ServerException;

	public <E extends BaseEntity, EPK> E getByIdentifier(Class<E> paramClass, EPK paramEPK, boolean paramBoolean) throws ServerException;

	public List<T> getByProperty(String paramString, Object paramObject) throws ServerException;

	public <E extends BaseEntity> List<E> getByProperty(Class<E> paramClass, String paramString, Object paramObject) throws ServerException;

	public T getByPropertySingleResult(String paramString, Object paramObject) throws ServerException;

	public <E extends BaseEntity> E getByPropertySingleResult(Class<E> paramClass, String paramString, Object paramObject) throws ServerException;

	public <E extends BaseEntity> E save(E paramE) throws ServerException;

	public <E extends BaseEntity> List<E> saveAll(Collection<E> paramCollection) throws ServerException;

	public <E extends BaseEntity> void delete(E paramE) throws ServerException;

	public <E extends BaseEntity> void delete(Collection<E> paramCollection) throws ServerException;

	public void deleteByIdentifier(PK paramPK) throws ServerException;

	public <E extends BaseEntity> void deleteByIdentifier(Class<E> paramClass, PK paramPK) throws ServerException;

	public <E extends BaseEntity> void detach(E paramE);

	public void clearPersistenceContext();
}
