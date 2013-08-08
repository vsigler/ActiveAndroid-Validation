/*
 * Copyright (C) 2013 Vojtech Sigler.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cz.sigler.android.aavalidation.internal;

import cz.sigler.android.aavalidation.api.IConstraintValidator;

/**
 *
 * @author sigi
 */
public class ValidationDefinition {
	private String fieldName;

	private String columnName;

	private IConstraintValidator validator;

	private String message;

	private int messageResId;

	public ValidationDefinition(String fieldName, String columnName, IConstraintValidator validator, String message, int messageResId) {
		this.fieldName = fieldName;
		this.columnName = columnName;
		this.validator = validator;
		this.message = message;
		this.messageResId = messageResId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getColumnName() {
		return columnName;
	}

	public IConstraintValidator getValidator() {
		return validator;
	}

	public String getMessage() {
		return message;
	}

	public int getMessageResId() {
		return messageResId;
	}

}
