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

package cz.sigler.android.aavalidation;

import com.activeandroid.Model;
import cz.sigler.android.aavalidation.api.error.ValidationProcessingException;
import cz.sigler.android.aavalidation.api.model.IModelSupport;
import cz.sigler.android.aavalidation.internal.ModelSupportImpl;

/**
 * Convenience factory to ease the use of this library in projects where
 * the models have been subclassed or the user does not want to use ValidatingModel
 * base class.
 */
public class ModelSupportFactory {

	/**
	 * Creates a new instance of the model and wraps the validating layer around it.
	 *
	 * @param <T>
	 *					subclass of Model
	 * @param modelClass
	 *					model class
	 * @return wrapped model instance
	 */
	public static <T extends Model> IModelSupport<T> createModel(final Class<T> modelClass) {
		try {
			T model = modelClass.newInstance();

			return wrapModel(model);
		} catch (Exception e) {
			throw new ValidationProcessingException("Failed creating new model instance.", e);
		}
	}

	/**
	 * Wraps an existing model with a validating layer. Null safe.
	 *
	 * @param <T>
	 *					subclass of Model
	 * @param model
	 *					model instance
	 * @return wrapped model
	 */
	public static <T extends Model> IModelSupport<T> wrapModel(final T model) {
		return model == null ? null : new ModelSupportImpl<T>(model);
	}

	/**
	 * Loads specified model from database and wraps the validating layer around it.
	 *
	 * @param <T>
	 *					subclass of Model
	 * @param modelClass
	 *					model class
	 * @param id
	 *					id of the model to fetch
	 * @return wrapped model
	 */
	public static <T extends Model> IModelSupport<T> loadModel(final Class<T> modelClass, final Long id) {
		T model = Model.load(modelClass, id);
		return model == null ? null : wrapModel(model);
	}
}
