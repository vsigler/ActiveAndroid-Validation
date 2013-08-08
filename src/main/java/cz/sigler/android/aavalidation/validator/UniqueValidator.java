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

import cz.sigler.android.aavalidation.api.IValueDescriptor;
import com.activeandroid.Model;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;
import cz.sigler.android.aavalidation.rule.Unique;

/**
 * Validator for the unique constraint. Checks whether there is not a row
 * in the database, different model instance, that has the same field value, but
 * different id than the currently validated model.
 */
public class UniqueValidator extends ValidatorBase<Object, Unique> {

	/**
	 * {@inheritDoc }
	 */
	@Override
	public boolean isValid(IValueDescriptor<Object> value) {
		Model model = value.getModel();

		From from = new Select().from(model.getClass());
		Model existing;

		if (value.getValue() == null) {
			existing = from.where(value.getColumnName() + " IS NULL").executeSingle();
		} else {
			existing = from.where(value.getColumnName() + " = ?", value.getValue()).executeSingle();
		}

		return existing == null || existing.getId().equals(value.getModel().getId());
	}

}
