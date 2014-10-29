package com.letsdoit.planyourevent.weblayer.controllers;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.letsdoit.planyourevent.businesslayer.service.TestService;
import com.letsdoit.planyourevent.businesslayer.utils.DTOMapper;
import com.letsdoit.planyourevent.commons.exceptions.ServerException;
import com.letsdoit.planyourevent.model.dto.TestDTO;

@Controller
public class TestController {

	private static final Logger LOGGER = Logger.getLogger(TestController.class);

	@Autowired
	TestService testService;

	@RequestMapping(value = { "/createTest" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public String test(ModelMap model) {
		model.addAttribute("message", "TEST PLANYOUREVENT");
		model.addAttribute("testDTO", new TestDTO());
		return "test";
	}

	@RequestMapping(value = { "/createTest" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public String createTest(@ModelAttribute("testDTO") TestDTO testDTO) throws ServerException {
		this.testService.create(DTOMapper.convert(testDTO));
		return "redirect:/testList";
	}

	@RequestMapping(value = { "/testList" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public ModelAndView getTestList() {
		List<TestDTO> testDTOs = this.testService.getAllTests();
		ModelAndView modelAndView = new ModelAndView("testListPage");
		modelAndView.addObject("testList", testDTOs);
		return modelAndView;
	}

	@RequestMapping(value = { "/welcome" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public String welcome() {
		return "welcome";
	}
}
