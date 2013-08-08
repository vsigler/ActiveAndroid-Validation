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

package cz.sigler.android.aavalidation.rule;

import cz.sigler.android.aavalidation.api.rule.Constraint;
import cz.sigler.android.aavalidation.validator.PatternValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for the regex pattern check.
 *
 * Check passes also if the field is null.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatorClass = PatternValidator.class)
public @interface Pattern {

	/**
	 * @return Error message text.
	 */
	String message();

	/**
	 * @return Error message resource id.
	 */
	int messageResId() default 0;

	/**
	 * @return regex pattern to match
	 */
	String pattern();
}
