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

package cz.sigler.android.aavalidation.api.error;

/**
 * Runtime exception to wrap various errors that can happen during processing.
 * When this is thrown, it is either a configuration problem or a bug. ;)
 */
public class ValidationProcessingException extends RuntimeException {

	public ValidationProcessingException(String message, Throwable cause) {
		super(message, cause);
	}

}
