package com.sosin.jussapi.api.dto.response;

import lombok.Data;

@Data
public class ResponseDataDto<T> {
	private String responseCode;
	private T response;
	private String msg;
}