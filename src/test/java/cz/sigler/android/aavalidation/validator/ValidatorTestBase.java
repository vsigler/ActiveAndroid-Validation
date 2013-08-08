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

package cz.sigler.android.aavalidation.validator;

import cz.sigler.android.aavalidation.api.error.ValidationError;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import cz.sigler.android.aavalidation.TestBase;
import cz.sigler.android.aavalidation.ValidatingModel;
import java.util.List;
import org.junit.Test;

/**
 * This is a base test case for validator tests. Implements
 * two basic tests - positive and negative cases.
 */
public abstract class ValidatorTestBase extends TestBase {

	/**
	 * Helper method for basic validation test.
	 *
	 * @return model that passes validation
	 */
	protected abstract ValidatingModel getModelNegative();

	/**
	 * Helper method for basic validation test.
	 *
	 * @return model that fails validation
	 */
	protected abstract ValidatingModel getModelPositive();

	/**
	 * Generic test - tests case when validation fails.
	 * Subclasses need to use proper constants for column name and error message.
	 */
	@Test
	public void testValidatePositive() {
		ValidatingModel model = getModelPositive();

		assertFalse(model.isValid());
		List<ValidationError> errors = model.getErrors();

		assertError(COLUMN_NAME, ERROR_MESSAGE, errors);
	}

	/**
	 * Generic test - tests case when validation passes.
	 */
	@Test
	public void testValidateNegative() {
		ValidatingModel model = getModelNegative();

		assertTrue(model.isValid());
		List<ValidationError> errors = model.getErrors();

		assertTrue(errors.isEmpty());
	}

}
