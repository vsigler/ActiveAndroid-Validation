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
import cz.sigler.android.aavalidation.rule.Pattern;

/**
 * TODO: add description
 */
public class PatternValidator implements IConstraintValidator<CharSequence, Pattern> {

	private java.util.regex.Pattern pattern;

	/**
	 * {@inheritDoc }
	 */
	@Override
	public void initialize(final Pattern annotation) {
		pattern = java.util.regex.Pattern.compile(annotation.pattern());
	}

	/**
	 *
	 * @param value
	 * @return
	 */
	@Override
	public boolean isValid(IValueDescriptor<CharSequence> value) {
		CharSequence toMatch = value.getValue();
		return toMatch == null || pattern.matcher(toMatch).matches();
	}
}
