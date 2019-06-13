package com.exer.dupe.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.exer.dupe.service.DupeService;

import io.swagger.annotations.ApiParam;

@RestController
public class DupeController {
	
	@Autowired DupeService dupeService;

	@RequestMapping(value = "/findDuplicates", method = RequestMethod.POST, produces ="application/json" )
    @Consumes(MediaType.MULTIPART_FORM_DATA)
	public String findDuplicates(    		
    		@ApiParam(name = "file", value = "select the file to upload", required = true)
    		@RequestPart("file") final MultipartFile file) {
        dupeService.findDuplicates(file);		
		return "Results printed on System console";
    }
}
