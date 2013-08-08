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

package cz.sigler.android.aavalidation.internal;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import cz.sigler.android.aavalidation.ValidatingModel;
import cz.sigler.android.aavalidation.api.rule.Constraint;
import cz.sigler.android.aavalidation.api.IConstraintValidator;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Stores prepared information about validation setup for each model.
 *
 * Models are scanned at first attempt for validation.
 */
public class ValidationInfo {

	/** Method that all constraint annotations should have - message text. */
	private static final String METHOD_MESSAGE = "message";

	/** Method that all constraint annotations should have - message resource id. */
	private static final String METHOD_MESSAGE_RESOURCE = "messageResId";

	/** Prepared validation descriptors. */
	private static Map<Class<? extends Model>, List<ValidationDefinition>> preparedValidations =
					new HashMap<Class<? extends Model>, List<ValidationDefinition>>();

	/**
	 * Private constructor to prevent creation of instances of this class.
	 */
	private ValidationInfo() { }

	/**
	 * Returns a list of validation definitions for given model class.
	 *
	 * @param modelClass
	 *					model class to scan
	 * @return list of validation definitions
	 */
	public static synchronized List<ValidationDefinition> getValidationsForModel(final Class<? extends Model> modelClass) {
		List<ValidationDefinition> descriptors = preparedValidations.get(modelClass);

		//Prepare validators in a lazy-init way, otherwise we would have to scan classpath.
		//And that is something ActiveAndroid does, so doing it twice would really slow down app start.
		if (descriptors == null) {
			descriptors = prepareValidations(modelClass);
			preparedValidations.put(modelClass, descriptors);
		}
		return descriptors;
	}

	/**
	 * Scans given model class, instantiates and initializes validators for each field.
	 *
	 * @param clazz
	 *					class to scan
	 * @return list of prepared validation definitions
	 */
	private static List<ValidationDefinition> prepareValidations(final Class<? extends Model> clazz) {
		List<ValidationDefinition> result = new ArrayList<ValidationDefinition>();

		Field[] fields = clazz.getDeclaredFields();

		for (Field field : fields) {
			//we ignore any fields that do not define a column
			if (field.isAnnotationPresent(Column.class)) {
				Column column = field.getAnnotation(Column.class);

				//scan for constraint annotations
				for (Annotation annot : field.getAnnotations()) {
					if (annot.annotationType().isAnnotationPresent(Constraint.class)) {
						Constraint constraint = annot.annotationType().getAnnotation(Constraint.class);
						Class<? extends IConstraintValidator> validatorClass = constraint.validatorClass();
						IConstraintValidator validator;
						int messageResId = 0;
						String message = "";

						try {
							//crate and initialize validator for the specific field
							validator = validatorClass.newInstance();
							validator.initialize(annot);
						} catch (Exception ex) {
							throw new IllegalStateException("Failed to instantiate validator: " + validatorClass.getName(), ex);
						}

						try {
							Object invocResult = getAnnotationParam(METHOD_MESSAGE_RESOURCE, annot);
							if (invocResult instanceof Number) {
								messageResId = ((Number) invocResult).intValue();
							}

							invocResult = getAnnotationParam(METHOD_MESSAGE, annot);
							message = invocResult == null ? "" : invocResult.toString();

						} catch (Exception ex) {
							throw new IllegalStateException("Annotation must properly define message and messageRedId methods!", ex);
						}

						result.add(new ValidationDefinition(field.getName(), column.name(), validator, message, messageResId));
					}
				}
			}
		}

		return result;
	}

	/**
	 * Helper class to get annotation parameter (method value) using reflection.
	 *
	 * @param name
	 *					parameter/method name
	 * @param annotation
	 *					annotation instance
	 * @return retrieved value
	 * @throws Exception
	 *					in case something goes wrong - trowing generic Exception as otherwise we would have
	 *					to throw 3 different exceptions and we're not going to differentiate between them anyway.
	 */
	private static Object getAnnotationParam(final String name, final Annotation annotation) throws Exception {
		Class configAnnotationClass = annotation.getClass();
		Method messageResMethod = configAnnotationClass.getMethod(name);
		return messageResMethod.invoke(annotation);
	}
}
