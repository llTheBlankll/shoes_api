package com.***REMOVED***.dss.models.dto;

import com.***REMOVED***.dss.models.enums.CodeStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class MessageDTO {
	private String message;
	private CodeStatus status;
}