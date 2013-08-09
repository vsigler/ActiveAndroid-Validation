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

import cz.sigler.android.aavalidation.api.IConstraintValidator;
import cz.sigler.android.aavalidation.api.IValueDescriptor;
import cz.sigler.android.aavalidation.rule.Min;

/**
 * Validator for the Min constraint - validated value is greater (or equal) to the
 * specified boundary.
 */
public class MinValidator implements IConstraintValidator<Number, Min> {

	private Comparable param;

	private Boolean equal;
	
	/**
	 * {@inheritDoc }
	 */
	@Override
	public void initialize(final Min annotation) {

		if (annotation.valueStr().length() > 0) { //string representation is set
			param = Double.parseDouble(annotation.valueStr());
		} else {
			param = annotation.value();
		}

		equal = annotation.equal();
	}

	/**
	 * {@inheritDoc }
	 */
	@Override
	public boolean isValid(IValueDescriptor<Number> value) {
		if (value.getValue() == null) {
			return true;
		}

		//convert the compared value to the right type
		Number valueToCompare;
		if (param instanceof Double) {
			valueToCompare = value.getValue().doubleValue();
		} else {
			valueToCompare = value.getValue().longValue();
		}

		int	comparisonResult = param.compareTo(valueToCompare);

		return (comparisonResult < 0) || (equal && (comparisonResult == 0));
	}

}
