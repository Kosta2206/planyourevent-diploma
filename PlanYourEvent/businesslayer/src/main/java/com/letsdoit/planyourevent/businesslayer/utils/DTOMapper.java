package com.letsdoit.planyourevent.businesslayer.utils;

import java.util.ArrayList;
import java.util.List;

import com.letsdoit.planyourevent.datalayer.entities.TestEntity;
import com.letsdoit.planyourevent.model.dto.TestDTO;

public class DTOMapper {

	public static TestEntity convert(TestDTO testDTO) {
		TestEntity testEntity = new TestEntity();
		testEntity.setName(testDTO.getName());
		testEntity.setNumber(testDTO.getNumber());
		return testEntity;
	}

	public static TestDTO convert(TestEntity testEntity) {
		TestDTO testDTO = new TestDTO();
		testDTO.setName(testEntity.getName());
		testDTO.setNumber(testEntity.getNumber());
		return testDTO;
	}

	public static List<TestDTO> convertTestEntityList(List<TestEntity> testEntities) {
		List<TestDTO> testDTOs = new ArrayList<TestDTO>();
		for (TestEntity testEntity : testEntities) {
			testDTOs.add(convert(testEntity));
		}
		return testDTOs;
	}
}
