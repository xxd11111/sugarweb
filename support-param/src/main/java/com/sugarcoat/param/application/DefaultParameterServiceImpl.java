package com.sugarcoat.param.application;

import com.querydsl.core.types.dsl.Expressions;
import com.sugarcoat.api.exception.ValidateException;
import com.sugarcoat.param.domain.QSugarcoatParameter;
import com.sugarcoat.param.domain.SugarcoatParameter;
import com.sugarcoat.param.domain.SugarcoatParameterRepository;
import com.sugarcoat.api.common.PageData;
import com.sugarcoat.api.common.PageParameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Set;

/**
 * @author xxd
 * @description 默认缓存实现方法
 * @date 2022-11-19
 */
@RequiredArgsConstructor
public class DefaultParameterServiceImpl implements ParameterService {

	private final SugarcoatParameterRepository sugarcoatParameterRepository;

	@Override
	public void save(ParameterDTO parameterDTO) {
		SugarcoatParameter entity = new SugarcoatParameter();
		if (parameterDTO.getId() != null || parameterDTO.getId().isEmpty()) {
			entity.setCode(parameterDTO.getCode());
			entity.setName(parameterDTO.getName());
			entity.setValue(parameterDTO.getValue());
			entity.setDefaultValue(parameterDTO.getDefaultValue());
			entity.setComment(parameterDTO.getComment());
		}
		else {
			entity.setId(parameterDTO.getId());
			entity.setName(parameterDTO.getName());
			entity.setValue(parameterDTO.getValue());
			entity.setComment(parameterDTO.getComment());
		}
		sugarcoatParameterRepository.save(entity);
	}

	@Override
	public void remove(String id) {
		sugarcoatParameterRepository.deleteById(id);
	}

	@Override
	public void reset(String id) {
		SugarcoatParameter sugarcoatParam = sugarcoatParameterRepository.findById(id)
				.orElseThrow(() -> new ValidateException("param not find"));
		sugarcoatParam.resetValue();
		sugarcoatParameterRepository.save(sugarcoatParam);
	}

	@Override
	public ParameterDTO findByCode(String code) {
		SugarcoatParameter sugarcoatParam = sugarcoatParameterRepository
				.findOne(QSugarcoatParameter.sugarcoatParameter.code.eq(code))
				.orElseThrow(() -> new ValidateException("param not find"));
		return SugarcoatParameterConvert.getParamDTO(sugarcoatParam);
	}

	@Override
	public ParameterDTO findById(String id) {
		SugarcoatParameter sugarcoatParam = sugarcoatParameterRepository.findById(id)
				.orElseThrow(() -> new ValidateException("param not find"));
		return SugarcoatParameterConvert.getParamDTO(sugarcoatParam);
	}

	@Override
	public PageData<ParameterDTO> findPage(PageParameter pageParameter, ParamQueryCmd cmd) {
		PageRequest pageRequest = PageRequest.of(pageParameter.getPageNum(), pageParameter.getPageSize());
		Page<ParameterDTO> page = sugarcoatParameterRepository.findAll(Expressions.TRUE, pageRequest)
				.map(SugarcoatParameterConvert::getParamDTO);
		return new PageData<>(page.getContent(), page.getTotalElements(), page.getNumber(), page.getSize());
	}

	@Override
	public void remove(Set<String> ids) {
		sugarcoatParameterRepository.deleteAllById(ids);
	}

	@Override
	public void reset(Set<String> ids) {
		Iterable<SugarcoatParameter> params = sugarcoatParameterRepository.findAllById(ids);
		params.forEach(SugarcoatParameter::resetValue);
		sugarcoatParameterRepository.saveAll(params);
	}

}
