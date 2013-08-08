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

package cz.sigler.android.aavalidation.wrapper;

import android.content.ContentValues;
import android.database.Cursor;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.util.SQLiteUtils;
import cz.sigler.android.aavalidation.ModelSupportFactory;
import cz.sigler.android.aavalidation.TestBase;
import cz.sigler.android.aavalidation.api.error.ValidationError;
import cz.sigler.android.aavalidation.api.model.IModelSupport;
import cz.sigler.android.aavalidation.rule.NotBlank;
import cz.sigler.android.aavalidation.rule.NotNull;
import cz.sigler.android.aavalidation.validator.UniqueValidatorTest;
import cz.sigler.android.aavalidation.wrapper.ModelSupportWrapperTest.TestModel;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

/**
 * Tests the alternative way of using this library - wrapping an existing model using
 * ModelSupportFactory.
 */
@PrepareForTest({Model.class, SQLiteUtils.class, TestModel.class})
public class ModelSupportWrapperTest extends TestBase {
	
	private static final String COLUMN_NAME2 = COLUMN_NAME + 2;
	private static final String COLUMN_NAME3 = COLUMN_NAME + 3;
	private static final String ERROR_MESSAGE2 = ERROR_MESSAGE + 2;
	private static final String ERROR_MESSAGE3 = ERROR_MESSAGE + 3;

	/**
	 * Tests wrapping an existing model.
	 */
	@Test
	public void testWrap() {
		TestModel model = new TestModel();

		IModelSupport<TestModel> support = ModelSupportFactory.wrapModel(model);

		assertTrue(model == support.getModel()); //check references as equals will not work here now		
	}

	/**
	 * Tests creating a new model instance.
	 */
	@Test
	public void testCreate() {
		IModelSupport<TestModel> support = ModelSupportFactory.createModel(TestModel.class);
		assertTrue(support.getModel() instanceof TestModel);
	}

	/**
	 * Tests loading model from database.
	 */
	@Test	
	public void testLoad() {
		PowerMockito.mockStatic(SQLiteUtils.class);

		UniqueValidatorTest.TestModel model = PowerMockito.mock(UniqueValidatorTest.TestModel.class);
		PowerMockito.when(model.getId()).thenReturn(ID);

		PowerMockito.when(SQLiteUtils.rawQuerySingle(eq(TestModel.class), anyString(), any(String[].class))).thenReturn(model);

		IModelSupport<TestModel> support = ModelSupportFactory.loadModel(TestModel.class, ID);
		assertNotNull(support);
		assertEquals(ID, support.getId());
	}

	/**
	 * Tests validation functionality of the wrapper.
	 */
	@Test
	public void testWrapperValidation() {
		IModelSupport<TestModel> support = ModelSupportFactory.createModel(TestModel.class);
		assertFalse(support.isValid());

		List<ValidationError> errors = support.getErrors();
		assertEquals(3, errors.size());
		Map<String, String> errorMap = new HashMap<String, String>();
		for (ValidationError error : errors) {
			errorMap.put(error.getColumnName(), error.getMessage());
		}
		assertEquals(ERROR_MESSAGE, errorMap.get(COLUMN_NAME));
		assertEquals(ERROR_MESSAGE2, errorMap.get(COLUMN_NAME2));
		assertEquals(ERROR_MESSAGE3, errorMap.get(COLUMN_NAME3));
	}

	/**
	 * Tests delegating of the save call.
	 */
	@Test
	public void testWrapperSave() {
		IModelSupport<TestModel> support = ModelSupportFactory.createModel(TestModel.class);
		support.save();
		verify(sqliteDb).insert(anyString(), anyString(), any(ContentValues.class));
	}

	/**
	 * Tests getters and setters of the model.
	 */
	@Test
	public void testWrapperGettersSetters() {
		IModelSupport<TestModel> support = ModelSupportFactory.createModel(TestModel.class);
		support.setFieldValue("testField", DUMMY);
		assertEquals(DUMMY, support.getModel().getTestField());

		support.getModel().setYetAnotherField(DUMMY + 1);
		assertEquals(DUMMY + 1, support.getFieldValue("yetAnotherField"));

		when(sqliteDb.insert(anyString(), anyString(), any(ContentValues.class))).thenReturn(ID);
		support.save();
		assertEquals(ID, support.getId());
	}

	/**
	 * Test delegation of the delete call.
	 */
	@Test
	public void testWrapperDelete() {
		IModelSupport<TestModel> support = ModelSupportFactory.createModel(TestModel.class);
		when(sqliteDb.insert(anyString(), anyString(), any(ContentValues.class))).thenReturn(ID);
		support.save(); //we need to save first to get an Id to the model - otherwise there is a NPE later

		support.delete();
		verify(sqliteDb).delete(eq(TABLE), anyString(), eq(new String[] { ID.toString() }));
	}

	/**
	 * Tests delegation of loadFromCursor call.
	 */
	@Test
	public void testWrapperLoadFromCursor() throws NoSuchFieldException {
		IModelSupport<TestModel> support = ModelSupportFactory.createModel(TestModel.class);
		Field field = TestModel.class.getDeclaredField("testField");
		when(tableInfo.getFields()).thenReturn(Collections.singleton(field));

		Cursor cursor = mock(Cursor.class);
		when(cursor.getColumnIndex(anyString())).thenReturn(1);
		when(cursor.getString(1)).thenReturn(DUMMY);

		support.loadFromCursor(cursor);
		assertEquals(DUMMY, support.getFieldValue("testField"));
	}

	/**
	 * Testing model.
	 */
	public static class TestModel extends Model {

		@Column(name = COLUMN_NAME)
		@NotBlank(message = ERROR_MESSAGE)
		private String testField;

		@Column(name = COLUMN_NAME2)
		@NotNull(message = ERROR_MESSAGE2)
		private Long anotherField;

		@Column(name = COLUMN_NAME3)
		@NotNull(message = ERROR_MESSAGE3)
		private Object yetAnotherField;

		public String getTestField() {
			return testField;
		}

		public void setTestField(String testField) {
			this.testField = testField;
		}

		public Long getAnotherField() {
			return anotherField;
		}

		public void setAnotherField(Long anotherField) {
			this.anotherField = anotherField;
		}

		public Object getYetAnotherField() {
			return yetAnotherField;
		}

		public void setYetAnotherField(Object yetAnotherField) {
			this.yetAnotherField = yetAnotherField;
		}

	}
}
