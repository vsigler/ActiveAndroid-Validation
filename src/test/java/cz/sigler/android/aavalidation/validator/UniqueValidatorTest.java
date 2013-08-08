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
import static org.mockito.Mockito.*;

import android.content.ContentValues;
import com.activeandroid.annotation.Column;
import com.activeandroid.util.SQLiteUtils;
import cz.sigler.android.aavalidation.validator.UniqueValidatorTest.TestModel;
import cz.sigler.android.aavalidation.ValidatingModel;
import cz.sigler.android.aavalidation.rule.Unique;
import java.util.List;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

/**
 * Tests Unique constraint.
 */
@PrepareForTest({SQLiteUtils.class, TestModel.class, ContentValues.class})
public class UniqueValidatorTest extends ValidatorTestBase {

	@Override
	protected void onSetup() {
		PowerMockito.mockStatic(SQLiteUtils.class);

		TestModel model = PowerMockito.mock(TestModel.class);
		PowerMockito.when(model.getId()).thenReturn(ID);

		PowerMockito.when(SQLiteUtils.rawQuerySingle(eq(TestModel.class), anyString(), eq(new String[] {DUMMY}))).thenReturn(model);
		PowerMockito.when(SQLiteUtils.rawQuerySingle(eq(TestModel.class), anyString(), eq(new String[] {}))).thenReturn(model);
	}

	@Override
	protected ValidatingModel getModelNegative() {
		TestModel model = new TestModel();
		model.setTestField(DUMMY + 1);
		return model;
	}

	@Override
	protected ValidatingModel getModelPositive() {
		TestModel model = new TestModel();
		model.setTestField(DUMMY);
		return model;
	}

	/**
	 * Null is also considered for uniqueness.
	 * Tests case where the checked field is null and there already is an
	 * existing record with that field null.
	 */
	@Test
	public void testValidatePositive_NullMatch() {
		TestModel model = new TestModel();
		model.setTestField(null); //let's make this field more evident

		assertFalse(model.isValid());
		List<ValidationError> errors = model.getErrors();

		assertError(COLUMN_NAME, ERROR_MESSAGE, errors);
	}

	/**
	 * Case when we're validating update - the only record that is allowed to have
	 * this value is the one we're updating (with the same ID).
	 */
	@Test
	public void testValidatePositive_EntityExists() throws Exception {
		//make sqlite return the id we want to assign
		when(sqliteDb.insert(anyString(), anyString(), any(ContentValues.class))).thenReturn(ID);

		TestModel model = new TestModel();
		model.setTestField(DUMMY);
		model.save();

		assertTrue(model.isValid());
		List<ValidationError> errors = model.getErrors();

		assertTrue(errors.isEmpty());
	}

	public static class TestModel extends ValidatingModel {

		@Column(name = COLUMN_NAME)
		@Unique(message = ERROR_MESSAGE)
		private Object testField;

		public Object getTestField() {
			return testField;
		}

		public void setTestField(Object testField) {
			this.testField = testField;
		}
	}

}
