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

import static org.junit.Assert.*;

import com.activeandroid.annotation.Column;
import cz.sigler.android.aavalidation.ValidatingModel;
import cz.sigler.android.aavalidation.rule.Pattern;
import org.junit.Test;

/**
 * Tests the pattern matching rule and validator.
 */
public class PatternValidatorTest extends ValidatorTestBase {

	@Override
	protected ValidatingModel getModelNegative() {
		TestModel model = new TestModel();
		model.setTestField("abcabxcaaa");
		return model;
	}

	@Override
	protected ValidatingModel getModelPositive() {
		TestModel model = new TestModel();
		model.setTestField("abcAAA");
		return model;
	}

	/**
	 * Tests that null passes as valid.
	 */
	@Test
	public void testNegative_Null() {
		TestModel model = new TestModel();
		model.setTestField(null);

		assertTrue(model.isValid());
		assertTrue(model.getErrors().isEmpty());
	}

	public static final class TestModel extends ValidatingModel {

		@Column(name = COLUMN_NAME)
		@Pattern(pattern = "[a-z]*", message = ERROR_MESSAGE)
		private String testField;

		public String getTestField() {
			return testField;
		}

		public void setTestField(String testField) {
			this.testField = testField;
		}
	}

}
