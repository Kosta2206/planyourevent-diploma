package com.letsdoit.planyourevent.datalayer.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.letsdoit.planyourevent.datalayer.dao.TestDAO;
import com.letsdoit.planyourevent.datalayer.entities.TestEntity;

@Repository
@Transactional
public class TestDAOImpl extends GenericDAOImpl<TestEntity, Long> implements TestDAO {

	public TestDAOImpl() {
		super(TestEntity.class);
	}
}
