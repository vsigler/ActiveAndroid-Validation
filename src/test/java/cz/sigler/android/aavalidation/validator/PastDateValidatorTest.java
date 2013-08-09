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
import cz.sigler.android.aavalidation.rule.Past;
import java.util.Date;

/**
 * Tests the Past constraint using Date type.
 */
public class PastDateValidatorTest extends ValidatorTestBase {

	/** Week in milliseconds. */
	private static final Long WEEK = Long.valueOf(60 * 60 * 24 * 7 * 1000);

	/**
	 * {@inheritDoc }
	 */
	@Override
	protected ValidatingModel getModelNegative() {
		TestModel model = new TestModel();
		Date future = new Date(System.currentTimeMillis() - WEEK);
		model.setTestField(future);
		return model;
	}

	/**
	 * {@inheritDoc }
	 */
	@Override
	protected ValidatingModel getModelPositive() {
		TestModel model = new TestModel();
		Date future = new Date(System.currentTimeMillis() + WEEK);
		model.setTestField(future);
		return model;
	}

	/**
	 * Testing model class.
	 */
	public static class TestModel extends ValidatingModel {
		
		@Column(name = COLUMN_NAME)
		@Past(message = ERROR_MESSAGE)
		private Date testField;

		public Date getTestField() {
			return testField;
		}

		public void setTestField(Date testField) {
			this.testField = testField;
		}
	}
}
