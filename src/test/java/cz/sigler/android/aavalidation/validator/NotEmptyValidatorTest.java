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
import static org.junit.Assert.*;

import com.activeandroid.annotation.Column;
import cz.sigler.android.aavalidation.ValidatingModel;
import cz.sigler.android.aavalidation.rule.NotEmpty;
import java.util.List;
import org.junit.Test;

/**
 * Tests NotEmpty constraint.
 */
public class NotEmptyValidatorTest extends ValidatorTestBase {

	@Override
	protected ValidatingModel getModelPositive() {
		TestModel model = new TestModel();
		model.setTestField("");
		return model;
	}

	@Override
	protected ValidatingModel getModelNegative() {
		TestModel model = new TestModel();
		model.setTestField(DUMMY);
		return model;
	}

	/**
	 * Check null case - also should trigger error.
	 */
	@Test
	public void testValidatePositive_Null() {
		TestModel model = new TestModel();
		model.setTestField(null); //let's make this field more evident

		assertFalse(model.isValid());
		List<ValidationError> errors = model.getErrors();

		assertError(COLUMN_NAME, ERROR_MESSAGE, errors);
	}

	/**
	 * Check string with white spaces case - valid.
	 */
	@Test
	public void testValidateNegative_Whitespace() {
		TestModel model = new TestModel();
		model.setTestField("     "); //let's make this field more evident

		assertTrue(model.isValid());
		List<ValidationError> errors = model.getErrors();

		assertTrue(errors.isEmpty());
	}
	
	public static class TestModel extends ValidatingModel {

		@Column(name = COLUMN_NAME)
		@NotEmpty(message = ERROR_MESSAGE)
		private String testField;

		public String getTestField() {
			return testField;
		}

		public void setTestField(String testField) {
			this.testField = testField;
		}
	}
}
