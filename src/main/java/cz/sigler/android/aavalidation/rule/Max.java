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
import cz.sigler.android.aavalidation.validator.MaxValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Rule defining that the target is less (or equal) to specified value.
 *
 * Parameters can be set as a long or a string representation of a
 * decimal value.
 *
 * When the long param is used, validation will compare against a long value.
 * Otherwise a decimal (Double) value is used for comparison.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(MaxValidator.class)
public @interface Max {
	/**
	 * @return Error message text.
	 */
	String message();

	/**
	 * @return Error message resource id.
	 */
	int messageResId() default 0;

	/**
	 * @return Upper boundary of the validated value.
	 */
	long value() default Long.MAX_VALUE;

	/**
	 * When defined, takes precedence over the value param.
	 *
	 * @return Upper boundary of the validated value - string representation.
	 */
	String valueStr() default "";

	/**
	 * @return true if this rule shoud be interpreted as "less or equal", false for "less"
	 */
	boolean equal() default true;
}
