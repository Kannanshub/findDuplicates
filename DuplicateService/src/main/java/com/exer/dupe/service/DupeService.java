package com.exer.dupe.service;

import org.springframework.web.multipart.MultipartFile;


public interface DupeService {

	void findDuplicates(MultipartFile file);

}
