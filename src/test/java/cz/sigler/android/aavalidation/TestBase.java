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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import com.activeandroid.Cache;
import com.activeandroid.TableInfo;
import com.activeandroid.content.ContentProvider;
import cz.sigler.android.aavalidation.api.error.ValidationError;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Base for tests that involve active android library calls and classes.
 * 
 * It makes heavy use of PowerMock
 * to overcome the static nature of ActiveAndroid library. This library,
 * while related, does not directly use any of the Android API, so it uses
 * standard jUnit approach.
 */
@PrepareForTest({ Cache.class, TableInfo.class, Context.class,
	ContentResolver.class, ContentProvider.class, ContentValues.class })
@RunWith(PowerMockRunner.class)
@SuppressStaticInitializationFor("com.activeandroid.content.ContentProvider")
public abstract class TestBase {

	/** Testing column name. */
	protected static final String COLUMN_NAME = "Column";

	/** Dummy string. */
	protected static final String DUMMY = "dummy";

	/** Dummy error message. */
	protected static final String ERROR_MESSAGE = "Error";

	/** Dummy id. */
	protected static final Long ID = 1234L;

	/** Dummy table name. */
	protected static final String TABLE = "TestTable";

	/** Mocked android context - to be able to inject stuff into ActiveAndroid internals. */
	protected Context context;

	/** Mocked sqlite database. */
	protected SQLiteDatabase sqliteDb;

	/** Mocked table info. */
	protected TableInfo tableInfo;

	/**
	 * Helper method to verify error messages.
	 *
	 * @param column
	 *					expected column name
	 * @param message
	 *					expected error message
	 * @param errors
	 *					list of reported errors
	 */
	protected void assertError(final String column, final String message, final List<ValidationError> errors) {
		assertFalse(errors.isEmpty());
		ValidationError error = errors.get(0);
		assertEquals(message, error.getMessage());
		assertEquals(column, error.getColumnName());
	}

	/**
	 * Override this to add further setup code in subclasses.
	 */
	protected void onSetup() {
	}

	/**
	 * Setups a number of mocks, parts of them static, to be able to push things
	 * into ActiveAndroid internals, making simple tests possible.
	 *
	 * @throws Exception
	 *					in case of an error - should not happen
	 */
	@Before
	public void setup() throws Exception {
		PowerMockito.mockStatic(Cache.class);
		PowerMockito.mockStatic(ContentProvider.class);
		tableInfo = PowerMockito.mock(TableInfo.class);
		context = PowerMockito.mock(Context.class);
		ContentResolver resolver = PowerMockito.mock(ContentResolver.class);
		sqliteDb = PowerMockito.mock(SQLiteDatabase.class);
		ContentValues vals = PowerMockito.mock(ContentValues.class);
		PowerMockito.whenNew(ContentValues.class).withNoArguments().thenReturn(vals);
		PowerMockito.when(Cache.openDatabase()).thenReturn(sqliteDb);
		when(context.getContentResolver()).thenReturn(resolver);
		doNothing().when(resolver).notifyChange(any(Uri.class), any(ContentObserver.class));
		when(tableInfo.getFields()).thenReturn(new ArrayList<Field>());
		when(tableInfo.getTableName()).thenReturn(TABLE);
		PowerMockito.when(Cache.getTableInfo(any(Class.class))).thenReturn(tableInfo);
		PowerMockito.when(Cache.getContext()).thenReturn(context);
		PowerMockito.when(ContentProvider.createUri((Class<ValidatingModel>) anyObject(), anyLong())).thenReturn(null);
		onSetup();
	}

}
