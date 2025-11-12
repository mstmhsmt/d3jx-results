<<<<<<< commits-hd_100/junit-team/junit4/956f86fa4c406a923d5366b5906e3431e9d0717d-7d061f3af085d90a672f1618deb9bf6ed9979f7a/A.java
package org.junit.tests;
||||||| commits-hd_100/junit-team/junit4/956f86fa4c406a923d5366b5906e3431e9d0717d-7d061f3af085d90a672f1618deb9bf6ed9979f7a/O.java
package org.junit.runners;
=======
package org.junit.tests;

import static org.junit.Assert.assertEquals;
>>>>>>> commits-hd_100/junit-team/junit4/956f86fa4c406a923d5366b5906e3431e9d0717d-7d061f3af085d90a672f1618deb9bf6ed9979f7a/B.java

<<<<<<< commits-hd_100/junit-team/junit4/956f86fa4c406a923d5366b5906e3431e9d0717d-7d061f3af085d90a672f1618deb9bf6ed9979f7a/A.java
import static org.junit.Assert.assertEquals;
import org.junit.Test;
||||||| commits-hd_100/junit-team/junit4/956f86fa4c406a923d5366b5906e3431e9d0717d-7d061f3af085d90a672f1618deb9bf6ed9979f7a/O.java
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.internal.AssumptionViolatedException;
import org.junit.internal.runners.model.EachTestNotifier;
import org.junit.internal.runners.model.MultipleFailureException;
import org.junit.internal.runners.statements.RunAfters;
import org.junit.internal.runners.statements.RunBefores;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.Filterable;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runner.manipulation.Sortable;
import org.junit.runner.manipulation.Sorter;
=======
import java.util.Comparator;

import org.junit.Test;
import org.junit.internal.builders.AllDefaultPossibilitiesBuilder;
import org.junit.runner.Description;
import org.junit.runner.manipulation.Sorter;
>>>>>>> commits-hd_100/junit-team/junit4/956f86fa4c406a923d5366b5906e3431e9d0717d-7d061f3af085d90a672f1618deb9bf6ed9979f7a/B.java
import org.junit.runner.notification.RunNotifier;
<<<<<<< commits-hd_100/junit-team/junit4/956f86fa4c406a923d5366b5906e3431e9d0717d-7d061f3af085d90a672f1618deb9bf6ed9979f7a/A.java
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerInterceptor;
||||||| commits-hd_100/junit-team/junit4/956f86fa4c406a923d5366b5906e3431e9d0717d-7d061f3af085d90a672f1618deb9bf6ed9979f7a/O.java
import org.junit.runner.notification.StoppedByUserException;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

/**
 * Provides most of the functionality specific to a Runner that implements a
 * "parent node" in the test tree, with children defined by objects of some data
 * type {@code T}. (For {@link BlockJUnit4ClassRunner}, {@code T} is
 * {@link Method} . For {@link Suite}, {@code T} is {@link Class}.) Subclasses
 * must implement finding the children of the node, describing each child, and
 * running each child. ParentRunner will filter and sort children, handle
 * {@code @BeforeClass} and {@code @AfterClass} methods, create a composite
 * {@link Description}, and run children sequentially.
 */
public abstract class ParentRunner<T> extends Runner implements Filterable,
		Sortable {
	private final TestClass fTestClass;

	private Filter fFilter= null;

	private Sorter fSorter= Sorter.NULL;

	/**
	 * Constructs a new {@code ParentRunner} that will run {@code @TestClass}
	 * @throws InitializationError 
	 */
	protected ParentRunner(Class<?> testClass) throws InitializationError {
		fTestClass= new TestClass(testClass);
		validate();
	}

	//
	// Must be overridden
	//

	/**
	 * Returns a list of objects that define the children of this Runner.
	 */
	protected abstract List<T> getChildren();

	/**
	 * Returns a {@link Description} for {@code child}, which can be assumed to
	 * be an element of the list returned by {@link ParentRunner#getChildren()}
	 */
	protected abstract Description describeChild(T child);

	/**
	 * Runs the test corresponding to {@code child}, which can be assumed to be
	 * an element of the list returned by {@link ParentRunner#getChildren()}.
	 * Subclasses are responsible for making sure that relevant test events are
	 * reported through {@code notifier}
	 */
	protected abstract void runChild(T child, RunNotifier notifier);
	
	// TODO (May 4, 2009 4:34:28 PM): This can't be right, but it allows delegation without breaking subclasses
	/**
	 * Interim interface during 4.7 development.  _Very_ likely to go away.
	 */
	public List<T> internalGetChildren() {
		return getChildren();
	}

	/**
	 * Interim interface during 4.7 development.  _Very_ likely to go away.
	 */
	public Description internalDescribeChild(T child) {
		return describeChild(child);
	}

	/**
	 * Interim interface during 4.7 development.  _Very_ likely to go away.
	 */
	public void internalRunChild(T child, RunNotifier notifier) {
		runChild(child, notifier);
	}
	
	//
	// May be overridden
	//
	
	/**
	 * Adds to {@code errors} a throwable for each problem noted with the test class (available from {@link #getTestClass()}).
	 * Default implementation adds an error for each method annotated with
	 * {@code @BeforeClass} or {@code @AfterClass} that is not
	 * {@code public static void} with no arguments.
	 */
	protected void collectInitializationErrors(List<Throwable> errors) {
		validatePublicVoidNoArgMethods(BeforeClass.class, true, errors);
		validatePublicVoidNoArgMethods(AfterClass.class, true, errors);
	}

	/**
	 * Adds to {@code errors} if any method in this class is annotated with
	 * {@code annotation}, but:
	 * <ul>
	 * <li>is not public, or
	 * <li>takes parameters, or
	 * <li>returns something other than void, or
	 * <li>is static (given {@code isStatic is false}), or
	 * <li>is not static (given {@code isStatic is true}).
	 */
	protected void validatePublicVoidNoArgMethods(Class<? extends Annotation> annotation,
			boolean isStatic, List<Throwable> errors) {
		List<FrameworkMethod> methods= getTestClass().getAnnotatedMethods(annotation);

		for (FrameworkMethod eachTestMethod : methods)
			eachTestMethod.validatePublicVoidNoArg(isStatic, errors);
	}

	/** 
	 * Constructs a {@code Statement} to run all of the tests in the test class. Override to add pre-/post-processing. 
	 * Here is an outline of the implementation:
	 * <ul>
	 * <li>Call {@link #runChild(Object, RunNotifier)} on each object returned by {@link #getChildren()} (subject to any imposed filter and sort).</li>
	 * <li>ALWAYS run all non-overridden {@code @BeforeClass} methods on this class
	 * and superclasses before the previous step; if any throws an
	 * Exception, stop execution and pass the exception on.
	 * <li>ALWAYS run all non-overridden {@code @AfterClass} methods on this class
	 * and superclasses before any of the previous steps; all AfterClass methods are
	 * always executed: exceptions thrown by previous steps are combined, if
	 * necessary, with exceptions from AfterClass methods into a
	 * {@link MultipleFailureException}.
	 * </ul>
	 * @param notifier
	 * @return {@code Statement}
	 */
	protected Statement classBlock(final RunNotifier notifier) {
		Statement statement= childrenInvoker(notifier);
		statement= withBeforeClasses(statement);
		statement= withAfterClasses(statement);
		return statement;
	}

	/**
	 * Returns a {@link Statement}: run all non-overridden {@code @BeforeClass} methods on this class
	 * and superclasses before executing {@code statement}; if any throws an
	 * Exception, stop execution and pass the exception on.
	 */
	protected Statement withBeforeClasses(Statement statement) {
		List<FrameworkMethod> befores= fTestClass
				.getAnnotatedMethods(BeforeClass.class);
		statement= new RunBefores(statement, befores, null);
		return statement;
	}

	/**
	 * Returns a {@link Statement}: run all non-overridden {@code @AfterClass} methods on this class
	 * and superclasses before executing {@code statement}; all AfterClass methods are
	 * always executed: exceptions thrown by previous steps are combined, if
	 * necessary, with exceptions from AfterClass methods into a
	 * {@link MultipleFailureException}.
	 */
	protected Statement withAfterClasses(Statement statement) {
		List<FrameworkMethod> afters= fTestClass
				.getAnnotatedMethods(AfterClass.class);
		statement= new RunAfters(statement, afters, null);
		return statement;
	}

	/**
	 * Returns a {@link Statement}: Call {@link #runChild(Object, RunNotifier)}
	 * on each object returned by {@link #getChildren()} (subject to any imposed
	 * filter and sort)
	 */
	protected Statement childrenInvoker(final RunNotifier notifier) {
		return new Statement() {
			@Override
			public void evaluate() {
				runChildren(notifier);
			}
		};
	}
=======
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.FrameworkMethod;
>>>>>>> commits-hd_100/junit-team/junit4/956f86fa4c406a923d5366b5906e3431e9d0717d-7d061f3af085d90a672f1618deb9bf6ed9979f7a/B.java

<<<<<<< commits-hd_100/junit-team/junit4/956f86fa4c406a923d5366b5906e3431e9d0717d-7d061f3af085d90a672f1618deb9bf6ed9979f7a/A.java
public class ParentRunnerTest {
	public static String log= "";
||||||| commits-hd_100/junit-team/junit4/956f86fa4c406a923d5366b5906e3431e9d0717d-7d061f3af085d90a672f1618deb9bf6ed9979f7a/O.java
	private void runChildren(final RunNotifier notifier) {
		for (T each : getFilteredChildren())
			runChild(each, notifier);
	}
=======
// TODO (May 11, 2009 2:42:38 PM): move package
>>>>>>> commits-hd_100/junit-team/junit4/956f86fa4c406a923d5366b5906e3431e9d0717d-7d061f3af085d90a672f1618deb9bf6ed9979f7a/B.java

<<<<<<< commits-hd_100/junit-team/junit4/956f86fa4c406a923d5366b5906e3431e9d0717d-7d061f3af085d90a672f1618deb9bf6ed9979f7a/A.java
	public static class FruitTest {
		@Test
		public void apple() {
			log+= "apple ";
||||||| commits-hd_100/junit-team/junit4/956f86fa4c406a923d5366b5906e3431e9d0717d-7d061f3af085d90a672f1618deb9bf6ed9979f7a/O.java
	/**
	 * Returns a name used to describe this Runner
	 */
	protected String getName() {
		return fTestClass.getName();
	}

	//
	// Available for subclasses
	//

	/**
	 * Returns a {@link TestClass} object wrapping the class to be executed.
	 */
	public final TestClass getTestClass() {
		return fTestClass;
	}

	//
	// Implementation of Runner
	// 
	
	@Override
	public Description getDescription() {
		Description description= Description.createSuiteDescription(getName(),
				fTestClass.getAnnotations());
		for (T child : getFilteredChildren())
			description.addChild(describeChild(child));
		return description;
	}

	@Override
	public void run(final RunNotifier notifier) {
		EachTestNotifier testNotifier= new EachTestNotifier(notifier,
				getDescription());
		try {
			Statement statement= classBlock(notifier);
			statement.evaluate();
		} catch (AssumptionViolatedException e) {
			testNotifier.fireTestIgnored();
		} catch (StoppedByUserException e) {
			throw e;
		} catch (Throwable e) {
			testNotifier.addFailure(e);
=======
public class ParentRunnerTest {
	private static StringBuffer log= new StringBuffer();

	public static class FruitTests {
		@Test
		public void apple() {
			log.append("apple ");
>>>>>>> commits-hd_100/junit-team/junit4/956f86fa4c406a923d5366b5906e3431e9d0717d-7d061f3af085d90a672f1618deb9bf6ed9979f7a/B.java
		}
<<<<<<< commits-hd_100/junit-team/junit4/956f86fa4c406a923d5366b5906e3431e9d0717d-7d061f3af085d90a672f1618deb9bf6ed9979f7a/A.java

		@Test
		public void banana() {
			log+= "banana ";
		}
||||||| commits-hd_100/junit-team/junit4/956f86fa4c406a923d5366b5906e3431e9d0717d-7d061f3af085d90a672f1618deb9bf6ed9979f7a/O.java
	}
	
	//
	// Implementation of Filterable and Sortable
	//

	public void filter(Filter filter) throws NoTestsRemainException {
		fFilter= filter;

		for (T each : getChildren())
			if (shouldRun(each))
				return;
		throw new NoTestsRemainException();
	}

	public void sort(Sorter sorter) {
		fSorter= sorter;
	}
	
	//
	// Private implementation
	// 

	private void validate() throws InitializationError {
		List<Throwable> errors= new ArrayList<Throwable>();
		collectInitializationErrors(errors);
		if (!errors.isEmpty())
			throw new InitializationError(errors);
	}

	protected List<T> getFilteredChildren() {
		ArrayList<T> filtered= new ArrayList<T>();
		for (T each : getChildren())
			if (shouldRun(each))
				try {
					filterChild(each);
					sortChild(each);
					filtered.add(each);
				} catch (NoTestsRemainException e) {
					// don't add it
				}
		Collections.sort(filtered, comparator());
		return filtered;
	}

	private void sortChild(T child) {
		fSorter.apply(child);
	}

	private void filterChild(T child) throws NoTestsRemainException {
		if (fFilter != null)
			fFilter.apply(child);
=======

		@Test
		public void banana() {
			log.append("banana ");
		}

		@Test
		public void pear() {
			log.append("pear ");
		}
>>>>>>> commits-hd_100/junit-team/junit4/956f86fa4c406a923d5366b5906e3431e9d0717d-7d061f3af085d90a672f1618deb9bf6ed9979f7a/B.java
	}

<<<<<<< commits-hd_100/junit-team/junit4/956f86fa4c406a923d5366b5906e3431e9d0717d-7d061f3af085d90a672f1618deb9bf6ed9979f7a/A.java
	@Test
	public void useChildHarvester() throws InitializationError {
		log= "";
		ParentRunner<?> runner= new BlockJUnit4ClassRunner(FruitTest.class);
		runner.setRunnerInterceptor(new RunnerInterceptor() {
			public void runChild(Runnable childStatement) {
				log+= "before ";
				childStatement.run();
				log+= "after ";
			}
||||||| commits-hd_100/junit-team/junit4/956f86fa4c406a923d5366b5906e3431e9d0717d-7d061f3af085d90a672f1618deb9bf6ed9979f7a/O.java
	private boolean shouldRun(T each) {
		return fFilter == null || fFilter.shouldRun(describeChild(each));
	}
=======
	@Test
	public void installDecorator() throws Throwable {
		log.setLength(0);
		ParentRunner<FrameworkMethod> runner= (BlockJUnit4ClassRunner) new AllDefaultPossibilitiesBuilder(
				true).runnerForClass(FruitTests.class);
		// TODO (May 11, 2009 2:58:11 PM): DUP?
		runner.sort(new Sorter(new Comparator<Description>() {
			public int compare(Description o1, Description o2) {
				return o1.toString().compareTo(o2.toString());
			}
		}));
		runner.installDecorator(new ParentRunner.Decorator() {
			public void runChild(SafeStatement statement) {
				log.append("before ");
				statement.execute();
				log.append("after ");
			}
>>>>>>> commits-hd_100/junit-team/junit4/956f86fa4c406a923d5366b5906e3431e9d0717d-7d061f3af085d90a672f1618deb9bf6ed9979f7a/B.java

<<<<<<< commits-hd_100/junit-team/junit4/956f86fa4c406a923d5366b5906e3431e9d0717d-7d061f3af085d90a672f1618deb9bf6ed9979f7a/A.java
			public void finished() {
				log+= "afterAll ";
||||||| commits-hd_100/junit-team/junit4/956f86fa4c406a923d5366b5906e3431e9d0717d-7d061f3af085d90a672f1618deb9bf6ed9979f7a/O.java
	private Comparator<? super T> comparator() {
		return new Comparator<T>() {
			public int compare(T o1, T o2) {
				return fSorter.compare(describeChild(o1), describeChild(o2));
=======
			public void runAll(SafeStatement statement) {
				log.append("beforeAll ");
				statement.execute();
				log.append("afterAll ");
>>>>>>> commits-hd_100/junit-team/junit4/956f86fa4c406a923d5366b5906e3431e9d0717d-7d061f3af085d90a672f1618deb9bf6ed9979f7a/B.java
			}
<<<<<<< commits-hd_100/junit-team/junit4/956f86fa4c406a923d5366b5906e3431e9d0717d-7d061f3af085d90a672f1618deb9bf6ed9979f7a/A.java
		});

		runner.run(new RunNotifier());
		assertEquals("before apple after before banana after afterAll ", log);
||||||| commits-hd_100/junit-team/junit4/956f86fa4c406a923d5366b5906e3431e9d0717d-7d061f3af085d90a672f1618deb9bf6ed9979f7a/O.java
		};
=======
		});

		runner.run(new RunNotifier());
		assertEquals(
				"beforeAll before apple after before banana after before pear after afterAll ",
				log.toString());
>>>>>>> commits-hd_100/junit-team/junit4/956f86fa4c406a923d5366b5906e3431e9d0717d-7d061f3af085d90a672f1618deb9bf6ed9979f7a/B.java
	}
}
