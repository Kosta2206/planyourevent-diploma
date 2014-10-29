package com.letsdoit.planyourevent.businesslayer.service;

import java.util.List;

import com.letsdoit.planyourevent.commons.exceptions.ServerException;
import com.letsdoit.planyourevent.datalayer.entities.TestEntity;
import com.letsdoit.planyourevent.model.dto.TestDTO;

public interface TestService {

	public void create(TestEntity paramTestEntity) throws ServerException;

	public List<TestDTO> getAllTests();
}
