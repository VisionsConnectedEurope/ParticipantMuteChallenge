package com.vc.assignment.mcubrokerlight.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/")
public class IndexController {

	@Autowired
	private MessageSource messageSource;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView entry() {
		return new ModelAndView("index");
	}

	@RequestMapping(value = "/status.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Map<String, String> entryJson() {
		Map<String, String> map = new HashMap<>();
		map.put("title", messageSource.getMessage("app.title", null, "no title found", Locale.getDefault()));
		map.put("version", messageSource.getMessage("version.nr", null, "no version found", Locale.getDefault()));

		return map;
	}
}
