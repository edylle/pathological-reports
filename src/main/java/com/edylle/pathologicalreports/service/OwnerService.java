package com.edylle.pathologicalreports.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.edylle.pathologicalreports.exception.CrudException;
import com.edylle.pathologicalreports.model.dto.FindOwnerDTO;
import com.edylle.pathologicalreports.model.entity.Owner;
import com.edylle.pathologicalreports.model.entity.User;
import com.edylle.pathologicalreports.repository.OwnerRepository;
import com.edylle.pathologicalreports.utils.Messages;
import com.edylle.pathologicalreports.wrapper.PageWrapper;

@Service
public class OwnerService {

	@Autowired
	private OwnerRepository ownerRepository;
	@Autowired
	private Messages messages;

	public Owner save(Owner owner) {
		Map<String, String> rejectedValues = new HashMap<>();

		if (owner.isNewRegistry()) {
			if (ownerRepository.findOne(owner.getId()) != null) {
				rejectedValues.put("id", messages.getMessageBy("message.param.duplicated", messages.getMessageBy("label.id")));
			}

			if (!rejectedValues.isEmpty()) {
				throw new CrudException(rejectedValues);
			}
		}

		return ownerRepository.save(owner);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PageWrapper<User> findBy(FindOwnerDTO dto, int page, int size) {
		String id;
		String name;

		id = StringUtils.isEmpty(dto.getId()) ? "%%" : "%".concat(dto.getId().toLowerCase()).concat("%");
		name = StringUtils.isEmpty(dto.getName()) ? "%%" : "%".concat(dto.getName().toLowerCase()).concat("%");

		Pageable pageable = new PageRequest(page, size);
		Page<Owner> pageInterface = ownerRepository.findBy(id, name, pageable);

		return new PageWrapper(pageInterface.getContent(), pageable, pageInterface.getTotalElements());
	}
}
