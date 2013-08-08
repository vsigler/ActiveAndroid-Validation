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

import cz.sigler.android.aavalidation.api.error.ValidationProcessingException;
import java.lang.reflect.Field;

/**
 * Utility class to handle getting and setting field values.
 */
class FieldUtils {

	/**
	 * To prevent creating instances of this class.
	 */
	private FieldUtils() { }
	
	/**
	 * Gets field value using reflection.
	 *
	 * @param target
	 *					object to get field value from
	 * @param fieldName
	 *					field name
	 * @return field value or null if reflection failed
	 */
	public static Object getFieldValue(final Object target, final String fieldName) {
		try {
			Class clazz = target.getClass();
			Field field = clazz.getDeclaredField(fieldName);

			if (field == null) {
				throw new IllegalArgumentException("Field '" + fieldName + "' not found.");
			}

			field.setAccessible(true);
			return field.get(target);
		} catch (Exception e) { //there are way too many thrown exceptions to handle each separately
			throw new ValidationProcessingException("Failed to get value of field '" + fieldName + "'", e);
		}
	}

	/**
	 * Sets field value using reflection.
	 *
	 * @param target
	 *					class to set field value on
	 * @param fieldName
	 *					field name
	 * @param fieldValue
	 *					value to set
	 * @return field value or null if reflection failed
	 */
	public static void setFieldValue(final Object target, final String fieldName, final Object fieldValue) {
		try {
			Class clazz = target.getClass();
			Field field = clazz.getDeclaredField(fieldName);

			if (field == null) {
				throw new IllegalArgumentException("Field '" + fieldName + "' not found.");
			}

			field.setAccessible(true);
			field.set(target, fieldValue);
		} catch (Exception e) { //there are way too many thrown exceptions to handle each separately
			throw new ValidationProcessingException("Failed to set value of field '" + fieldName + "'", e);
		}
	}
}
