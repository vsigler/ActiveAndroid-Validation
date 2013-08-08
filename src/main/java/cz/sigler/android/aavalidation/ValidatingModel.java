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

package cz.sigler.android.aavalidation;

import cz.sigler.android.aavalidation.api.error.ValidationException;
import cz.sigler.android.aavalidation.api.error.ValidationError;
import cz.sigler.android.aavalidation.api.model.IValidatingModel;
import com.activeandroid.Model;
import cz.sigler.android.aavalidation.internal.ValidationExecutor;
import java.util.LinkedList;
import java.util.List;

/**
 * Extended model that adds validation possibilities.
 *
 * Subclass this to add validation functionality in the simplest manner.
 */
public abstract class ValidatingModel extends Model implements IValidatingModel {

	/** List of errors from last validation. */
	private List<ValidationError> errors = new LinkedList<ValidationError>();

	/**
	 * {@inheritDoc }
	 */
	@Override
	public List<ValidationError> getErrors() {
		return new LinkedList<ValidationError>(errors);
	}

	/**
	 * {@inheritDoc }
	 */
	@Override
	public boolean isValid() {
		ValidationExecutor executor = new ValidationExecutor(this);
		errors = executor.execute();

		return errors.isEmpty();
	}

	/**
	 * {@inheritDoc }
	 */
	@Override
	public void saveOrThrow() throws ValidationException {		
		if (isValid()) {
			save();
		} else {			
			throw new ValidationException(getErrors());
		}
	}
}
