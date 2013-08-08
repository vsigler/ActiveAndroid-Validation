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

package cz.sigler.android.aavalidation.api;

import com.activeandroid.Model;

/**
 * Interface describing a value to be validated, including context data - field name,
 * column name and model containing the field.
 */
public interface IValueDescriptor<T> {
	/**
	 * @return the actual value to be validated
	 */
	T getValue();

	/**
	 * @return validated field name
	 */
	String getFieldName();

	/**
	 * @return validated column name
	 */
	String getColumnName();

	/**
	 * @return model being validated
	 */
	Model getModel();
}
