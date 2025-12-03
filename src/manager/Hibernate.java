package manager;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import model.Mark;
import model.MarkAverage;
import model.User;

public class Hibernate {
	private Session session;
	private Transaction tx;

	public void init() {
		initSession();

		// CRUD
		int id = insertUser();
		User user = getUser(id);
		updateUser(user);
		deleteUser(user);

		// Manual Queries
		ArrayList<User> users = getAllUsers();
		addMarkAverage(user); // oneToOneExample , check User "markAverage" attribute
		addMarks(user);       // oneToManyExample, check User "marks" ArrayList

		endSession();
	}

	private void endSession() {
		session.close();
	}

	private void initSession() {
		Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
		org.hibernate.SessionFactory sessionFactory = configuration.buildSessionFactory();
    	session = sessionFactory.openSession();		
	}

	private int insertUser() {
		// We create a user to save in the Database
		User user = new User("Maria", "Fernandez");

		try {
			// Start transaction (consecutive queries that need to be executed
			// together)
			tx = session.beginTransaction();

			// Insert a new student record in the database. Returns the id
			// generated
			int id = (Integer) session.save(user);

			tx.commit();
			System.out.println("Inserted Successfully.");

			return id;

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback(); // Roll back if any exception occurs.
			e.printStackTrace();
		}

		return 0;
	}

	private User getUser(int id) {
		try {
			tx = session.beginTransaction();
			
			// We get the user whith an specific id from database
			User user = session.get(User.class, id);

			// we print the information
			System.out.println(user.toString());

			tx.commit();
			System.out.println("Saved Successfully." + user.toString());

			return user;

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback(); // Roll back if any exception occurs.
			e.printStackTrace();
		}

		return null;
	}

	// Update a User value and save it to the database
	private void updateUser(User user) {
		// We change some info from the user
		user.setSurname("Gutierrez");

		try {
			tx = session.beginTransaction();
			session.save(user);
			tx.commit();
			System.out.println("Updated Successfully.");

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback(); // Roll back if any exception occurs.
			e.printStackTrace();
		}
	}

	// deletes a user from the Database
	private void deleteUser(User user) {
		try {
			tx = session.beginTransaction();
			session.remove(user);
			tx.commit();
			System.out.println("Deleted Successfully.");

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback(); // Roll back if any exception occurs.
			e.printStackTrace();
		}
	}

	private ArrayList<User> getAllUsers() {
		ArrayList<User> users = new ArrayList<>();

		try {
			tx = session.beginTransaction();

			// We create a manual query. Remember that "*" does not exist
			Query q = session.createQuery("select u from User u");

			// We get a List of Users
			List<User> usersList = q.list();

			// we add this users to our ArrayList
			users.addAll(usersList);

			for (User u : users) {
				System.out.println(u);
			}

			tx.commit();
			System.out.println("Get All Successfully.");

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback(); // Roll back if any exception occurs.
			e.printStackTrace();
		}

		return users;
	}

	private void addMarkAverage(User user) {
		// we generate a new Mark element to assign to the User markAverage field.
		MarkAverage markAverage = new MarkAverage(3, "I");

		// we add this element to the user
		user.setMarkAverage(markAverage);

		try {
			tx = session.beginTransaction();
			
			//we save the mark average object on database
			session.save(markAverage);

			// we update this information in the Database
			session.save(user);
			tx.commit();
			System.out.println("Added and Updated Successfully.");

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback(); // Roll back if any exception occurs.
			e.printStackTrace();
		}
	}

	private void addMarks(User user) {
		List<Mark> marks = user.getMarks();
		int[] numbers = {4, 7, 9};
		String[] letters = {"I","B","E"};

		try {
			tx = session.beginTransaction();

			//we generate and save all new Mark elements
			for (int i = 0; i < 3; i ++) {
				Mark mark = new Mark(numbers[i], letters[i]);
				session.save(mark);
				
				//we add the mark element to the arrayList of the user
				marks.add(mark);
			}
			
			// we update this information in the Database
			session.save(user);
			tx.commit();
			System.out.println("Entries added and Updated Successfully.");

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback(); // Roll back if any exception occurs.
			e.printStackTrace();
		}
	}

}
