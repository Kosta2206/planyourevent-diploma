package com.letsdoit.planyourevent.datalayer.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;

import com.letsdoit.planyourevent.commons.exceptions.ServerException;
import com.letsdoit.planyourevent.commons.verification.ArgumentPreconditions;
import com.letsdoit.planyourevent.datalayer.dao.GenericDAO;
import com.letsdoit.planyourevent.datalayer.entities.BaseEntity;

public class GenericDAOImpl<T extends BaseEntity, PK extends Serializable> implements GenericDAO<T, PK> {

	@PersistenceContext
	protected EntityManager entityManager;

	protected Class<T> type;

	private static final Logger LOGGER = Logger.getLogger(GenericDAOImpl.class);

	public GenericDAOImpl(Class<T> type) {
		this.type = type;
	}

	protected Query makeQueryCacheable(Query query) {
		query.setHint("org.hibernate.cacheable", true);
		// this hint will have effect only on queries that select entities
		// marked as cacheable (persistence-context.xml) - with the rest of the
		// queries it will be silently ignored
		return query;
	}

	protected <TT> TypedQuery<TT> makeQueryCacheable(TypedQuery<TT> query) {
		makeQueryCacheable((Query) query);
		return query;
	}

	@Override
	public List<T> getAll() {
		CriteriaQuery<T> criteria = defaultCriteria();
		criteria.select(criteria.from(this.type));

		return resultList(criteria);
	}

	@Override
	public List<T> getAll(final String orderBy, final boolean ascending) {
		return new CriteriaExecutor<T>(type) {
			@Override
			public void conditions(CriteriaBuilder builder, Root<T> root, CriteriaQuery<T> criteria, List<Predicate> predicates) {
				Path<Object> propertyPath = root.get(orderBy);
				criteria.orderBy(ascending ? builder.asc(propertyPath) : builder.desc(propertyPath));
			}
		}.resultList();
	}

	@Override
	public <E extends BaseEntity> List<E> getAll(Class<E> entityClass) {
		CriteriaQuery<E> criteria = newCriteria(entityClass);
		criteria.select(criteria.from(entityClass));

		return resultList(criteria);
	}

	@Override
	public T getByIdentifier(PK identifier) throws ServerException {
		ArgumentPreconditions.checkNotNullArgument(identifier, this.type.getSimpleName() + " ID", LOGGER);

		return getByIdentifier(identifier, true);
	}

	@Override
	public T getByIdentifier(PK identifier, boolean allowNull) throws ServerException {
		ArgumentPreconditions.checkNotNullArgument(identifier, this.type.getSimpleName() + " ID", LOGGER);

		return getByIdentifier(this.type, identifier, allowNull);
	}

	@Override
	public <E extends BaseEntity, EPK> E getByIdentifier(Class<E> resultClass, EPK identifier, boolean allowNull) throws ServerException {
		ArgumentPreconditions.checkNotNullArgument(identifier, this.type.getSimpleName() + " ID", LOGGER);

		E entity = this.entityManager.find(resultClass, identifier);
		if ((!allowNull) && (entity == null)) {
			throw new EntityNotFoundException("The '" + resultClass.getSimpleName() + "' object with identifier '" + identifier + "' not found");
		}
		return entity;
	}

	@Override
	public List<T> getByProperty(String attributeName, Object attributeValue) throws ServerException {
		return createGetByPropertyCriteriaExecutor(this.type, attributeName, attributeValue).resultList();
	}

	@Override
	public <E extends BaseEntity> List<E> getByProperty(Class<E> entityClass, String attributeName, Object attributeValue) throws ServerException {
		return createGetByPropertyCriteriaExecutor(entityClass, attributeName, attributeValue).resultList();
	}

	@Override
	public T getByPropertySingleResult(String attributeName, Object attributeValue) throws ServerException {
		return createGetByPropertyCriteriaExecutor(this.type, attributeName, attributeValue).singleResult();
	}

	@Override
	public <E extends BaseEntity> E getByPropertySingleResult(Class<E> entityClass, String attributeName, Object attributeValue) throws ServerException {
		return createGetByPropertyCriteriaExecutor(entityClass, attributeName, attributeValue).singleResult();
	}

	@Override
	public <E extends BaseEntity> E save(E entity) throws ServerException {
		ArgumentPreconditions.checkNotNullArgument(entity, this.type.getSimpleName(), LOGGER);
		return this.entityManager.merge(entity);
	}

	@Override
	public <E extends BaseEntity> List<E> saveAll(Collection<E> entities) throws ServerException {
		ArgumentPreconditions.checkNotNullArgument(entities, this.type.getSimpleName() + "s", LOGGER);

		List<E> saved = new ArrayList<E>(entities.size());
		for (E entity : entities) {
			saved.add(save(entity));
		}
		return saved;
	}

	private <E extends BaseEntity> CriteriaExecutor<E> createGetByPropertyCriteriaExecutor(Class<E> entityClass, final String attributeName,
			final Object attributeValue) throws ServerException {
		ArgumentPreconditions.checkNotNullArgument(attributeValue, entityClass.getSimpleName() + " attribute value", LOGGER);

		CriteriaExecutor<E> criteriaExecutor = new CriteriaExecutor<E>(entityClass) {
			@Override
			public void conditions(CriteriaBuilder builder, Root<E> root, CriteriaQuery<E> criteria, List<Predicate> predicates) {
				predicates.add(builder.equal(root.get(attributeName), attributeValue));
			}
		};

		return criteriaExecutor;
	}

	@Override
	public void deleteByIdentifier(PK identifier) throws ServerException {
		ArgumentPreconditions.checkNotNullArgument(identifier, this.type.getSimpleName() + " ID", LOGGER);

		this.entityManager.remove(getByIdentifier(identifier));
	}

	@Override
	public <E extends BaseEntity> void deleteByIdentifier(Class<E> entityClass, PK identifier) throws ServerException {
		ArgumentPreconditions.checkNotNullArgument(entityClass, "Entity class", LOGGER);
		ArgumentPreconditions.checkNotNullArgument(identifier, "ID", LOGGER);

		this.entityManager.remove(getByIdentifier(entityClass, identifier, false));
	}

	@Override
	public <E extends BaseEntity> void detach(E entity) {
		this.entityManager.detach(entity);
	}

	@Override
	public <E extends BaseEntity> void delete(E entity) throws ServerException {
		ArgumentPreconditions.checkNotNullArgument(entity, "Entity", LOGGER);

		this.entityManager.remove(entity);
		this.entityManager.flush();
	}

	@Override
	public <E extends BaseEntity> void delete(Collection<E> entities) throws ServerException {
		ArgumentPreconditions.checkNotNullArgument(entities, this.type.getSimpleName() + "s", LOGGER);
		for (E entity : entities) {
			this.entityManager.remove(entity);
		}
	}

	@Override
	public void clearPersistenceContext() {
		this.entityManager.clear();
	}

	protected CriteriaBuilder getCriteriaBuilder() {
		return this.entityManager.getCriteriaBuilder();
	}

	protected <E> CriteriaQuery<E> newCriteria(Class<E> resultClass) {
		return getCriteriaBuilder().createQuery(resultClass);
	}

	protected CriteriaQuery<T> defaultCriteria() {
		return newCriteria(this.type);
	}

	protected <E> TypedQuery<E> createQuery(CriteriaQuery<E> criteria) {
		return this.entityManager.createQuery(criteria);
	}

	protected <E> List<E> resultList(CriteriaQuery<E> criteria) {
		return makeQueryCacheable(createQuery(criteria)).getResultList();
	}

	protected <E> E singleResult(CriteriaQuery<E> criteria) {
		try {
			return makeQueryCacheable(this.entityManager.createQuery(criteria)).getSingleResult();
		} catch (NoResultException e) {
		}
		return null;
	}

	public abstract class CriteriaExecutor<E> {
		private Class<E> type;

		protected CriteriaExecutor(Class<E> type) {
			this.type = type;
		}

		public E singleResult() {
			try {
				return makeQueryCacheable(entityManager.createQuery(prepareCriteria())).getSingleResult();
			} catch (NoResultException nre) {
				return null;
			}
		}

		public List<E> resultList() {
			return makeQueryCacheable(entityManager.createQuery(prepareCriteria())).getResultList();
		}

		private CriteriaQuery<E> prepareCriteria() {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<E> criteria = builder.createQuery(type);
			Root<E> root = criteria.from(type);
			criteria.select(root);

			List<Predicate> predicates = new ArrayList<Predicate>();
			conditions(builder, root, criteria, predicates);

			if (!predicates.isEmpty()) {
				criteria.where(predicates.toArray(new Predicate[predicates.size()]));
			}

			return criteria;
		}

		public abstract void conditions(CriteriaBuilder builder, Root<E> root, CriteriaQuery<E> criteria, List<Predicate> predicates);
	}
}
