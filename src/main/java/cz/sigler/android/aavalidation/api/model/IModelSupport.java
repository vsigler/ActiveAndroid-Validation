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

package cz.sigler.android.aavalidation.api.model;

import android.database.Cursor;
import com.activeandroid.Model;

/**
 * Interface to ease creating validated model wrappers.
 */
public interface IModelSupport <T extends Model> extends IValidatingModel {

	/** @return model id. */
	Long getId();

	/**
	 * Gets field value from underlying model.
	 *
	 * @param name
	 * @return field value
	 * @throws IllegalArgumentException
	 *					in case of a invalid field name
	 */
	Object getFieldValue(String name);

	/**
	 * Sets field value on the underlying model.
	 *
	 * @param name
	 *					field name
	 * @param value
	 *					value to set
	 * @throws IllegalArgumentException if field name is invalid
	 */
	void setFieldValue(String name, Object value);

	/**
	 * Delegates the save call to the underlying model.
	 */
	void save();

	/**
	 * Delegates the delete save call to the underlying model.
	 */
	void delete();

	/**
	 * Delegates the loadFromCursor call to the underlying model.
	 */
	void loadFromCursor(Cursor cursor);

	/**
	 * @return underlying model
	 */
	T getModel();
}
