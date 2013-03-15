import br.bodoque.Filter;
import br.bodoque.Find;
import helpers.Customer;
import helpers.Person;


public class teste {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Customer c = new Customer("e", 0);
		c.setAge(55);
		c.save();
		
		long countAdultPeople = Find.from(Customer.class).
				filter(new Filter<Customer>() {
					public boolean accept(final Customer p) {
						return p.getAge() >= 18;
					}
				}).count();
		
		System.out.println(countAdultPeople);

	}

}
