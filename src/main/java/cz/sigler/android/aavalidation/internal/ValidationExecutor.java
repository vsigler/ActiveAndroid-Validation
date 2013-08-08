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

import com.activeandroid.Model;
import cz.sigler.android.aavalidation.api.error.ValidationError;
import cz.sigler.android.aavalidation.api.IConstraintValidator;
import cz.sigler.android.aavalidation.api.IValueDescriptor;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

/**
 * Runner that performs all validations for given model instance.
 */
public class ValidationExecutor {

	/** Target model to be validated. */
	public Model target;

	/**
	 * Constructor.
	 *
	 * @param target
	 *					target to be validated
	 */
	public ValidationExecutor(final Model target) {
		this.target = target;
	}

	/**
	 * Executes validations for the model.
	 *
	 * @return list of validation errors or empty list of there were none
	 */
	public List<ValidationError> execute() {
		List<ValidationError> errors = new LinkedList<ValidationError>();

		for (final ValidationDefinition descriptor : ValidationInfo.getValidationsForModel(target.getClass())) {
			final Object value = FieldUtils.getFieldValue(target, descriptor.getFieldName());
			IConstraintValidator validator = descriptor.getValidator();
			IValueDescriptor valDesc = new IValueDescriptor() {
				@Override
				public Object getValue() {
					return value;
				}

				@Override
				public String getFieldName() {
					return descriptor.getFieldName();
				}

				@Override
				public String getColumnName() {
					return descriptor.getColumnName();
				}

				@Override
				public Model getModel() {
					return target;
				}
			};
			if (!validator.isValid(valDesc)) {
				errors.add(createError(descriptor));
			}
		}

		return errors;
	}

	/**
	 * Creates a validation error.
	 *
	 * @param definition
	 *					validation definition
	 * @return validation error
	 */
	protected ValidationError createError(final ValidationDefinition definition) {
		String errorMessage;

		if (definition.getMessageResId() != 0) {
			errorMessage = getStringResource(definition.getMessageResId());
		} else {
			errorMessage = definition.getMessage();
		}

		return new ValidationError(definition.getColumnName(), definition.getFieldName(), errorMessage);
	}

	/**
	 * Returns a message from the given resource id.
	 *
	 * @param resId
	 *					resource id
	 * @return string message
	 */
	private String getStringResource(final int resId) {
		return String.valueOf(resId); // TODO: to be implemented
	}

}
