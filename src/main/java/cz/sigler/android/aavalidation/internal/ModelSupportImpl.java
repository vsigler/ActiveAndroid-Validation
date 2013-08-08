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

import android.database.Cursor;
import com.activeandroid.Model;
import cz.sigler.android.aavalidation.api.error.ValidationError;
import cz.sigler.android.aavalidation.api.error.ValidationException;
import cz.sigler.android.aavalidation.api.model.IModelSupport;
import java.util.LinkedList;
import java.util.List;

/**
 * Decorator that wraps an existing model adding validation functionality
 * at runtime.
 */
public class ModelSupportImpl<T extends Model> implements IModelSupport<T> {

	/** Wrapped model. */
	private T model;

	/** List of validation errors. */
	private List<ValidationError> errors = new LinkedList<ValidationError>();

	/**
	 * Constructor.
	 *
	 * @param model
	 *					model to wrap
	 */
	public ModelSupportImpl(final T model) {
		this.model = model;
	}

	/**
	 * {@inheritDoc }
	 */
	@Override
	public boolean isValid() {
		ValidationExecutor executor = new ValidationExecutor(model);

		errors = executor.execute();
		return errors.isEmpty();
	}

	/**
	 * {@inheritDoc }
	 */
	@Override
	public void saveOrThrow() throws ValidationException {
		if (isValid()) {
			model.save();
		} else {
			throw new ValidationException(getErrors());
		}
	}

	/**
	 * {@inheritDoc }
	 */
	@Override
	public List<ValidationError> getErrors() {
		return new LinkedList<ValidationError>(errors);
	}

	@Override
	public Long getId() {
		return getModel().getId();
	}

	@Override
	public Object getFieldValue(final String name) {
		return FieldUtils.getFieldValue(getModel(), name);
	}

	@Override
	public void setFieldValue(final String name, final Object value) {
		FieldUtils.setFieldValue(getModel(), name, value);
	}

	@Override
	public void save() {
		getModel().save();
	}

	@Override
	public void delete() {
		getModel().delete();
	}

	@Override
	public void loadFromCursor(Cursor cursor) {
		getModel().loadFromCursor(cursor);
	}

	@Override
	public T getModel() {
		return model;
	}
	
}
