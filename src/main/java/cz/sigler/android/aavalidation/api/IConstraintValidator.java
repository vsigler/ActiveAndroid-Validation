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

package cz.sigler.android.aavalidation.api;

import java.lang.annotation.Annotation;

/**
 * Interface for all constraint validators.
 */
public interface IConstraintValidator<U, T extends Annotation> {

	/**
	 * Initialize the validator with parameters defined in the annotation.
	 *
	 * @param annotation
	 *			constraint annotation
	 */
	void initialize(T annotation);

	/**
	 * Performs actual validation.
	 *
	 * @param value
	 *			value to validate
	 * @return true if value is valid
	 */
	boolean isValid(IValueDescriptor<U> value);	
}
