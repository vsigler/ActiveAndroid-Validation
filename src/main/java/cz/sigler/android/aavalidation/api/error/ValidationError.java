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

package cz.sigler.android.aavalidation.api.error;

/**
 * Simple POJO to hold validation error information.
 */
public class ValidationError {

	/** Database column name. */
	private String columnName;

	/** Error message. */
	private String message;

	/** Name of model field that caused the validation error. */
	private String fieldName;

	/**
	 * Constructor.
	 *
	 * @param columnName
	 *			database column name
	 * @param fieldName
	 *			model field name
	 * @param message
	 *			error message
	 */
	public ValidationError(final String columnName, final String fieldName, final String message) {
		this.columnName = columnName;
		this.message = message;
		this.fieldName = fieldName;
	}

	/**
	 * @return name of model field that caused the validation error
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * @return name of database column that is bound to the invalid field
	 */
	public String getColumnName() {
		return columnName;
	}

	/**
	 * @return error message describing the error
	 */
	public String getMessage() {
		return message;
	}
}
