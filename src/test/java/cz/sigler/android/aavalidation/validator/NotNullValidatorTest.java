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

import com.activeandroid.annotation.Column;
import cz.sigler.android.aavalidation.ValidatingModel;
import cz.sigler.android.aavalidation.rule.NotNull;

/**
 * Tests NotNull constraint.
 */
public class NotNullValidatorTest extends ValidatorTestBase {

	@Override
	protected ValidatingModel getModelPositive() {
		TestModel model = new TestModel();
		model.setTestField(null);
		return model;
	}

	@Override
	protected ValidatingModel getModelNegative() {
		TestModel model = new TestModel();
		model.setTestField(DUMMY);
		return model;
	}
	
	public static class TestModel extends ValidatingModel {

		@Column(name = COLUMN_NAME)
		@NotNull(message = ERROR_MESSAGE)
		private Object testField;

		public Object getTestField() {
			return testField;
		}

		public void setTestField(Object testField) {
			this.testField = testField;
		}
	}
}
