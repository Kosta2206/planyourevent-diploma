package com.letsdoit.planyourevent.businesslayer.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.letsdoit.planyourevent.businesslayer.service.TestService;
import com.letsdoit.planyourevent.businesslayer.utils.DTOMapper;
import com.letsdoit.planyourevent.commons.exceptions.ServerException;
import com.letsdoit.planyourevent.datalayer.dao.TestDAO;
import com.letsdoit.planyourevent.datalayer.entities.TestEntity;
import com.letsdoit.planyourevent.model.dto.TestDTO;

@Service
@Transactional
public class TestServiceImpl implements TestService {

	private static final Logger LOGGER = Logger.getLogger(TestServiceImpl.class);

	@Autowired
	TestDAO testDAO;

	@Override
	public void create(TestEntity testEntity) throws ServerException {
		this.testDAO.save(testEntity);
	}

	@Override
	public List<TestDTO> getAllTests() {
		return DTOMapper.convertTestEntityList(this.testDAO.getAll());
	}
}
