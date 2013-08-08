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

import cz.sigler.android.aavalidation.api.error.ValidationError;
import cz.sigler.android.aavalidation.api.error.ValidationException;
import java.util.List;

/**
 * Interface to add validation extensions to a model.
 */
public interface IValidatingModel {

	/**
	 * @return true if all validations on the model pass
	 */
	boolean isValid();

	/**
	 * Performs validation and saves the model if all validations pass.
	 * Otherwise throws an exception containing all validation errors.
	 *
	 * @throws ValidationException
	 *					in case validation fails
	 */
	void saveOrThrow() throws ValidationException;

	/**
	 * Returns validation errors resulting from a previous call to isValid. Without
	 * it the method returns an empty collection.
	 *
	 * This method DOES NOT invoke validation.
	 *
	 * @return list of validation errors
	 */
	List<ValidationError> getErrors();

}
