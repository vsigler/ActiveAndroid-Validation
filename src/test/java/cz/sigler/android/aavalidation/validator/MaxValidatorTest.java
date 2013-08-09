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
import cz.sigler.android.aavalidation.rule.Max;
import java.math.BigDecimal;
import org.junit.Test;

/**
 * Test for the Max constraint.
 */
public class MaxValidatorTest extends ValidatorTestBase {

	private static final String COLUMN_NAME2 = COLUMN_NAME + 2;
	private static final String COLUMN_NAME3 = COLUMN_NAME + 3;
	private static final String ERROR_MESSAGE2 = ERROR_MESSAGE + 2;
	private static final String ERROR_MESSAGE3 = ERROR_MESSAGE + 3;

	private static final int BORDER = 10;
	private static final String BORDER_STR = "10.41";

	/**
	 * Tests comparing primitive type.
	 *
	 * @return model passing validation
	 */
	@Override
	protected ValidatingModel getModelNegative() {
		TestModel model = new TestModel();
		model.setField1(BORDER);
		return model;
	}

	/**
	 * Tests comparing primitive type.
	 *
	 * @return model failing validation
	 */
	@Override
	protected ValidatingModel getModelPositive() {
		TestModel model = new TestModel();
		model.setField1(BORDER + 10);
		return model;
	}

	/**
	 * Tests the "less only" (without equal) comparison.
	 *
	 * Also tests using non-primitive number type.
	 */
	@Test
	public void testLessNotEqual() {
		TestModel model = new TestModel();
		model.setField2(BORDER);

		assertFalse(model.isValid());
		assertError(COLUMN_NAME2, ERROR_MESSAGE2, model.getErrors());
	}

	/**
	 * Check decimal comparison - negative case.
	 */
	@Test
	public void testNegativeDecimalComparison() {
		TestModel model = new TestModel();
		model.setField3(new BigDecimal(19.45d));

		assertFalse(model.isValid());
		assertError(COLUMN_NAME3, ERROR_MESSAGE3, model.getErrors());
	}

	/**
	 * Check decimal comparison - positive case.
	 */
	@Test
	public void testPositiveDecimalComparison() {
		TestModel model = new TestModel();
		model.setField3(new BigDecimal(9.45d));

		assertTrue(model.isValid());
		assertTrue(model.getErrors().isEmpty());
	}

	/**
	 * Testing model.
	 */
	public static class TestModel extends ValidatingModel {

		@Column(name = COLUMN_NAME)
		@Max(value = BORDER, message = ERROR_MESSAGE)
		private int field1;

		@Column(name = COLUMN_NAME2)
		@Max(value = BORDER, equal = false, message = ERROR_MESSAGE2)
		private Integer field2;

		@Column(name = COLUMN_NAME3)
		@Max(valueStr = BORDER_STR, message = ERROR_MESSAGE3)
		private BigDecimal field3;

		public int getField1() {
			return field1;
		}

		public void setField1(int field1) {
			this.field1 = field1;
		}

		public Integer getField2() {
			return field2;
		}

		public void setField2(Integer field2) {
			this.field2 = field2;
		}

		public BigDecimal getField3() {
			return field3;
		}

		public void setField3(BigDecimal field3) {
			this.field3 = field3;
		}
	}
}
