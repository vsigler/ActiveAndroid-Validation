# ActiveAndroid-Validation

## About

An extension to [ActiveAndroid](http://github.com/pardom/ActiveAndroid) adding validation functionality to models, sticking to the simple-to-use way of the ActiveAndroid library.

I was looking for a simple ORM framework for Android and came across ActiveAndroid, an elegant active-record-like solution very easy to use. Ok, now I have easy to use persistence in my application, but I still have to implement validation on the model fields before saving. ActiveAndroid allows to define NOT NULL or UNIQUE constraints in the database, but it always saves model silently, even if the save failed due to constraint violation. Now I can execute all checks easily before trying to save the model - this covers not only simple field value checks, but also data-related constraints such as the *Unique* constraint.

## Example

To define validation rules, just annotate your model fields with constraints in a manner inspired by [bean validation (JSR 349)](http://beanvalidation.org/1.1). To add validation functionality to your model, subclass **ValidatingModel** instead of **Model**:

```java
public static class TestModel extends ValidatingModel {

	@Column(name = "foo")
	@NotNull(message = "This cannot be null!")
	private Object testField;

	public Object getTestField() {
		return testField;
	}

	public void setTestField(Object testField) {
		this.testField = testField;
	}
}
```

Now, to validate the model, simply call **isValid()**:

```java
TestModel model = new TestModel();
if (model.isValid()) {
	//yay, we're valid!
} else {
	//model is not valid, deal with it!
	List<ValidationError> errors = model.getErrors();
	//now you can process the errors, display to the user, etc.
}

//Or combined with the save method
try {
	model.saveOrThrow(); // this wraps validation around the save() method of the Model
} catch (ValidationException ve) {
	List<ValidationError> errors = ve.getErrors();
	//handle errors here
}
```

What if I already have my own customised/subclassed models? Just wrap a validation layer on top of your model:

``` java
IModelSupport<TestModel> support = ModelSupportFactory.wrapModel(model);

boolean valid = support.isValid(); // now you have all that jazz from Validating model
Long modelId = support.getId();
support.save();
support.saveOrThrow();
support.delete();
.
.
.

//with a little difference as it is a wrapper, not subclass:
Object myValue = support.getFieldValue("testField");
support.setFieldValue("testField", someObject);

//you can always get to your original model
MyCustomModel model = support.getModel();
```

## Documentation

I will be adding topics gradually, I just do not have them ready at the moment.

* ValidatingModel
* Wrapping existing model - ModelSupportFactory
* Built-in constraints
* Defining your own constraints
